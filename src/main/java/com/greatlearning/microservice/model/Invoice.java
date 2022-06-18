package com.greatlearning.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    private Long orderId;
    private String orderDetails;
    private Double totalAmount;
}
