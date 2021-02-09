package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Client;
import com.krzek.fileprocessor.repository.ClientRepository;
import com.krzek.fileprocessor.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client processLine(String line, String separator) {
        Client client = convert(line, separator);
        return clientRepository.save(client);
    }

    protected Client convert(String line, String separator) {
        String[] split = line.split(separator);
        return new Client(split[1]);
    }
}
