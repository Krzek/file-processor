package com.krzek.fileprocessor.services.impl;

import com.krzek.fileprocessor.domain.Sale;
import com.krzek.fileprocessor.repository.SaleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SaleServiceImplTest {

    @Mock
    private SaleServiceImpl saleServiceImpl;

    @Mock
    private SaleRepository saleRepositoryMocked;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        saleServiceImpl = new SaleServiceImpl(saleRepositoryMocked);
        saleServiceImpl.setItemSeparator(",");
        saleServiceImpl.setItemInfSeparator("-");
    }

    @Test
    public void convert_test() {
        String linha = "003;10;[1-10-100,2-30-2.50,3-40-3.10];Pedro";
        Sale sale = saleServiceImpl.convert(linha, ";");
        assertThat(sale.getTotal(), equalTo(new BigDecimal("105.60")));
    }

    @Test
    public void convert_itens_test() {
        String itens = "[1-10-100,2-30-2.50,3-40-3.10]";
        Sale sale = saleServiceImpl.convertItens("10", "Pedro", itens);

        assertThat(sale.getTotal(), equalTo(new BigDecimal("105.60")));
    }

    @Test
    public void process_test() {
        String linha = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
        Sale sale = saleServiceImpl.processLine(linha, "ç");
        Sale persisted = new Sale("10", new BigDecimal("105.60"), "Pedro");

        when(saleRepositoryMocked.save(sale)).thenReturn(persisted);
        assertThat(persisted.getTotal(), equalTo(new BigDecimal("105.60")));
    }
}