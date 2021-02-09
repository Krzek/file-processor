package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Client;
import com.krzek.fileprocessor.repository.ClientRepository;
import com.krzek.fileprocessor.services.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ClientServiceImplTest {

    @Mock
    private ClientServiceImpl clientServiceImpl;

    @Mock
    private ClientRepository clientRepositoryMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientServiceImpl = new ClientServiceImpl(clientRepositoryMocked);
    }

    @Test
    public void convert_test() {
        String linha = "002;23567234000234;test;Testing";
        Client client = clientServiceImpl.convert(linha, ";");
        assertThat(client.getCnpj(), equalTo("23567234000234"));
    }

    @Test
    public void process_test() {
        String linha = "002ç1234569çaaaçBBBB";
        Client client = clientServiceImpl.processLine(linha, "ç");
        Client persisted = new Client("1234569");

        when(clientRepositoryMocked.save(client)).thenReturn(persisted);
        assertThat(persisted.getCnpj(), equalTo("1234569"));
    }
}