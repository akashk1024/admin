package com.greatlearning.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
//@Table
@AllArgsConstructor
@Builder
public class Items implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sequenceId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;
    private int itemId;

    private int quantity;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
