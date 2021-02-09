package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.builder.BuilderReport;
import com.krzek.fileprocessor.helper.FileHelper;
import com.krzek.fileprocessor.repository.ClientRepository;
import com.krzek.fileprocessor.repository.SaleRepository;
import com.krzek.fileprocessor.repository.SalesmanRepository;
import com.krzek.fileprocessor.services.OutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OutputServiceImpl implements OutputService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputServiceImpl.class);

    @Value("${app.out}")
    private String out;

    @Value("${file.name.out}")
    private String fileName;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;


    public void generateReport() {
        LOGGER.info("REPORT ");

        String file = reportProcess();

        try {
            FileHelper.write(out, fileName, file);
        } catch (IOException e) {
            LOGGER.error("Error OutputServiceImpl.generateReport()");
            throw new RuntimeException(e);
        }
    }

    public String reportProcess() {

        return BuilderReport.getInstance()
                .withAmountClients(clientRepository.count())
                .withAmountSalesMan(salesmanRepository.count())
                .withSalesMoreExpensive(saleRepository.getSalesMoreExpensive())
                .withWorstSeller(saleRepository.getSalesByNameSalesMan())
                .build();
    }
}
