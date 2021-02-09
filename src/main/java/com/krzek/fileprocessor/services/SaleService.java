package com.krzek.fileprocessor.services;

import com.krzek.fileprocessor.domain.Sale;

public interface SaleService {
    Sale processLine(String line, String separator);
}
