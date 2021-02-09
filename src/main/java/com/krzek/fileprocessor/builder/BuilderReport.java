package com.krzek.fileprocessor.builder;

import com.krzek.fileprocessor.domain.Sale;

import java.util.List;
import java.util.Optional;

public class BuilderReport {

    StringBuilder buffer = new StringBuilder();

    String lineBreak = System.getProperty("line.separator");

    public static BuilderReport getInstance() {
        return new BuilderReport();
    }

    public BuilderReport withAmountClients(Long amount) {
        if (amount > 0) {
            buffer.append("Quantidade de clientes no arquivo de entrada: ").append(amount).append(lineBreak);
        }
        return this;
    }

    public BuilderReport withAmountSalesMan(Long amount) {
        if (amount > 0) {
            buffer.append("Quantidade de vendedor no arquivo de entrada: ").append(amount).append(lineBreak);
        }
        return this;
    }

    public BuilderReport withSalesMoreExpensive(Optional<Sale> sale) {
        sale.ifPresent(value -> buffer.append("ID da venda mais cara: ").append(value.getIdSale()).append(lineBreak));
        return this;
    }

    public BuilderReport withWorstSeller(Optional<List<Sale>> sales) {
        sales.ifPresent(saleList -> buffer.append("O pior vendedor: ")
                .append(saleList.iterator().next().getSalesmanName()));

        return this;
    }

    public String build() {
        return buffer.toString();
    }
}