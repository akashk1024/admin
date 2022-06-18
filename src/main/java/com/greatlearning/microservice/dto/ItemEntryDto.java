package com.greatlearning.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntryDto implements Serializable {

    private Integer itemId;
    private Integer quantity;

}
