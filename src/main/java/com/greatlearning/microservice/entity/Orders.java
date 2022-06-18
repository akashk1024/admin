package com.greatlearning.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
//@Table
@AllArgsConstructor
@Builder
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String userName;

    @OneToMany(mappedBy = "orders")
    private List<Items> items;
    private double amount;

    private Date orderDate = new Date();

}
