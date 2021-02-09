package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Salesman;
import com.krzek.fileprocessor.repository.SalesmanRepository;
import com.krzek.fileprocessor.services.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalesmanServiceImpl implements SalesmanService {
    private final SalesmanRepository salesmanRepository;

    @Autowired
    public SalesmanServiceImpl(SalesmanRepository salesmanRepository) {
        this.salesmanRepository = salesmanRepository;
    }

    public Salesman processLine(String line, String separator) {
        Salesman salesman = convert(line, separator);
        return salesmanRepository.save(salesman);
    }

    protected Salesman convert(String line, String separator) {
        String[] split = line.split(separator);
        return new Salesman(split[1]);
    }
}
