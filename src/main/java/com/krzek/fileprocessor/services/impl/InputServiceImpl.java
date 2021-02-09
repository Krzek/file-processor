package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.enums.TypeEnum;
import com.krzek.fileprocessor.helper.FileHelper;
import com.krzek.fileprocessor.services.ClientService;
import com.krzek.fileprocessor.services.InputService;
import com.krzek.fileprocessor.services.SaleService;
import com.krzek.fileprocessor.services.SalesmanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InputServiceImpl implements InputService {
    @Value("${file.separator.in :ç}")
    private String separator;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private SaleService salesItemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InputServiceImpl.class);


    public void process(String line) {
        if (FileHelper.isValidLine(line, separator)) {
            TypeEnum type = TypeEnum.getValue(line.substring(0, 3));
            switch (type) {
                case CLIENT:
                    clientService.processLine(line, separator);
                    break;
                case SALESMAN:
                    salesmanService.processLine(line, separator);
                    break;
                case SALE:
                    salesItemService.processLine(line, separator);
                    break;
                default:
                    break;
            }
        } else {
            LOGGER.error("Invalid Line: " + line);
        }
    }
}
