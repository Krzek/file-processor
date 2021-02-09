package com.krzek.fileprocessor.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Salesman {

    @Id
    @EqualsAndHashCode.Include
    private String cpf;
}
