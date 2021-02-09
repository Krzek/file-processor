package com.krzek.fileprocessor.services;

import com.krzek.fileprocessor.domain.Salesman;

public interface SalesmanService {
    Salesman processLine(String line, String separator);
}
