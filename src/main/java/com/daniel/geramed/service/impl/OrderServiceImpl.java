package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.BookPrice;
import com.daniel.geramed.entity.Customer;
import com.daniel.geramed.entity.Order;
import com.daniel.geramed.entity.OrderDetail;
import com.daniel.geramed.model.request.OrderRequest;
import com.daniel.geramed.model.response.OrderDetailResponse;
import com.daniel.geramed.model.response.OrderResponse;
import com.daniel.geramed.repository.OrderRepository;
import com.daniel.geramed.service.BookPriceService;
import com.daniel.geramed.service.OrderDetailService;
import com.daniel.geramed.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final CustomerServiceImpl customerService;
    private final BookPriceService bookPriceService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse create(OrderRequest request) {
        Customer customer = customerService.findById(request.getCustomerId());
        String invoice = invoice();

        Order order = orderRepository.save(Order.builder()
                .customer(customer)
                .invoice(invoice)
                .build());

        List<OrderDetailResponse> orderDetailResponses = request.getOrderDetails().stream().map(orderDetailRequest -> {
            BookPrice bookPrice = bookPriceService.getById(orderDetailRequest.getBookPriceId());

            if (orderDetailRequest.getQuantity() > bookPrice.getStock()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Stock is not available");
            }

            bookPrice.setStock(bookPrice.getStock() - orderDetailRequest.getQuantity());

            OrderDetail orderDetail = orderDetailService.create(OrderDetail.builder()
                    .order(order)
                    .bookPrice(bookPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build());

            Long subTotal = bookPrice.getPrice() * orderDetailRequest.getQuantity();
            return OrderDetailResponse.builder()
                    .title(bookPrice.getBook().getTitle())
                    .store(bookPrice.getStore().getName())
                    .quantity(orderDetail.getQuantity())
                    .price(bookPrice.getPrice())
                    .subTotal(subTotal)
                    .build();
        }).collect(Collectors.toList());

        Long grandTotal = orderDetailResponses.stream().mapToLong(OrderDetailResponse::getSubTotal).sum();

        return OrderResponse.builder()
                .invoice(order.getInvoice())
                .orderDate(order.getOrderDate())
                .orderDetails(orderDetailResponses)
                .grandTotal(grandTotal)
                .build();
    }

    private Integer getOrderDay() {
        Specification<Order> specification = (root, query, criteriaBuilder) -> {
            LocalDate date = LocalDate.now();
            List<Predicate> predicates = List.of(criteriaBuilder.equal(criteriaBuilder
                    .function("DATE", LocalDate.class, root.get("orderDate")), date));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return orderRepository.findAll(specification).size();
    }

    private String invoice() {
        String prefix = "GRMD";
        Date currentDate = new Date();
        String formattedDate = new SimpleDateFormat("yyyyMMdd").format(currentDate);
        Integer orderByDay = getOrderDay();
        return String.format("%s%s%04d", prefix, formattedDate, orderByDay);
    }
}
