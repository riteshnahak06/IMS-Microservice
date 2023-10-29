package com.pralash.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="t_line_item")
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("ID")
    private Long id;
    @JsonProperty("SKU_Code")
    private String skuCode;
    @JsonProperty("Price")
    private BigDecimal price;
    @JsonProperty("Quantity")
    private Integer quantity;

}
