package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Salesman;
import com.krzek.fileprocessor.repository.SalesmanRepository;
import com.krzek.fileprocessor.services.SalesmanService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SalesmanServiceImplTest {

    @Mock
    private SalesmanServiceImpl salesmanServiceImpl;

    @Mock
    private SalesmanRepository salesmanRepositoryMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        salesmanServiceImpl = new SalesmanServiceImpl(salesmanRepositoryMocked);
    }

    @Test
    public void convert_test() {
        String linha = "001;1234567890;Paulo;50000";
        Salesman salesMan = salesmanServiceImpl.convert(linha, ";");
        assertThat(salesMan.getCpf(), equalTo("1234567890"));
    }

    @Test
    public void process_test() {
        String linha = "001ç1234567890çPedroç50000";
        Salesman salesman = salesmanServiceImpl.processLine(linha, "ç");
        Salesman persisted = new Salesman("1234567890");

        when(salesmanRepositoryMocked.save(salesman)).thenReturn(persisted);
        assertThat(persisted.getCpf(), equalTo("1234567890"));
    }
}