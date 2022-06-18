package com.greatlearning.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AuditLog {
    @Id
    private Long orderId;

    private String orderDetails;

    private Double totalAmount;

    private String userName;

    @CreatedDate
    private Date orderDate = new Date();
}
