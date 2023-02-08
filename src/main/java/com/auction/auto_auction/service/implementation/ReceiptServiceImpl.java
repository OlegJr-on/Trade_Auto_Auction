package com.auction.auto_auction.service.implementation;

import com.auction.auto_auction.dto.ordering.ReceiptDTO;
import com.auction.auto_auction.entity.*;
import com.auction.auto_auction.entity.enums.LotStatus;
import com.auction.auto_auction.entity.enums.OrderStatus;
import com.auction.auto_auction.exception.OutOfMoneyException;
import com.auction.auto_auction.exception.ResourceNotFoundException;
import com.auction.auto_auction.mapper.OrderDetailsMapper;
import com.auction.auto_auction.repository.uow.UnitOfWork;
import com.auction.auto_auction.service.ReceiptService;
import com.auction.auto_auction.utils.ApplicationConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ReceiptServiceImpl implements ReceiptService{
    private final UnitOfWork unitOfWork;
    private final OrderDetailsMapper orderDetailsMapper;

    @Override
    @Transactional
    public ReceiptDTO getOrdersByCustomerId(int customerId) {

        List<OrdersDetails> ordersByCustomer = this.unitOfWork.getOrdersDetailsRepository()
                .findAllOrdersByCustomerId(customerId)
                    .orElseThrow(() ->
                        new ResourceNotFoundException("Customer","id",String.valueOf(customerId)));

        return ReceiptDTO.builder()
                         .orders(ordersByCustomer.stream()
                                 .map(this.orderDetailsMapper::mapToDTO)
                                 .toList())
                         .total(ordersByCustomer.stream()
                                 .filter(od -> od.getOrderStatus() == OrderStatus.NOT_PAID)
                                 .map(OrdersDetails::getTotalPrice)
                                 .reduce(BigDecimal.ZERO,BigDecimal::add)
                                 .setScale(3, RoundingMode.CEILING))
                         .build();
    }

    @Override
    public void placeAnOrder(int customerId) {

    }

    @Override
    public void payOrder(int customerId, int orderId) {

    }

    @Override
    public void payAllCustomerOrders(int customerId) {

    }

    @Override
    public void cancelOrder(int customerId, int orderId) {

    }
}
