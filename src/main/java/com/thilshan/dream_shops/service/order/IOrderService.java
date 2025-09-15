package com.thilshan.dream_shops.service.order;

import com.thilshan.dream_shops.dto.OrderDto;
import com.thilshan.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder( Long userId);
    OrderDto getOrderDetails(Long orderId);
    List<OrderDto>  getUsersOrders(Long userId);
}
