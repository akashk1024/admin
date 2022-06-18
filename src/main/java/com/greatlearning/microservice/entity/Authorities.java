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
public class Authorities implements Serializable {
//    @Id
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private Users users;
    @Id
    private String username;
    private String authority;
}
