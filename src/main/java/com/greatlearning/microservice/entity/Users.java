package com.greatlearning.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
//@Table
@AllArgsConstructor
@Builder
public class Users implements Serializable {
    @Id
    private String username;
    private String password;
    private boolean enabled;

}
