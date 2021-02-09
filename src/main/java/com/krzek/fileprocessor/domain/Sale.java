package com.krzek.fileprocessor.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sale {

    @Id
    @EqualsAndHashCode.Include
    private String idSale;
    private BigDecimal total;
    private String salesmanName;
}