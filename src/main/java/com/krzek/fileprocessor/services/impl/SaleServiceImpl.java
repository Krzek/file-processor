package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Sale;
import com.krzek.fileprocessor.repository.SaleRepository;
import com.krzek.fileprocessor.services.SaleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class SaleServiceImpl implements SaleService {
    @Value("${file.item.separator.in}")
    private String itemSeparator;

    @Value("${file.item-inf.separator.in}")
    private String itemInfSeparator;

    private SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale processLine(String line, String separator) {
        Sale sale = convert(line, separator);
        return saleRepository.save(sale);
    }

    protected Sale convert(String line, String separator) {
        String[] split = line.split(separator);
        String idSales = split[1];
        String salesmanName = split[3];
        return convertItens(idSales, salesmanName, split[2]);

    }

    protected Sale convertItens(String idSales, String salesmanName, String itens) {
        String[] split = itens.substring(1, itens.length() - 1).split(itemSeparator);
        BigDecimal total = BigDecimal.ZERO;

        for (String s : split) {
            String[] infs = s.split(itemInfSeparator);
            BigDecimal price = new BigDecimal(infs[2]);
            total = total.add(price);
        }

        return new Sale(idSales, total, salesmanName);

    }
}
