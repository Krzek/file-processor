package com.krzek.fileprocessor.services;

import com.krzek.fileprocessor.domain.Client;

public interface ClientService {
    Client processLine(String line, String separator);
}
