package com.thilshan.dream_shops.service.order;

import com.thilshan.dream_shops.model.Order;

public interface IOrderService {
    Order placeOrder( Long userId);
    Order getOrderDetails(Long orderId);

}
