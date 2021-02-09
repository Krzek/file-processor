package com.krzek.fileprocessor.repository;

import com.krzek.fileprocessor.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value = "select new com.krzek.fileprocessor.domain.Sale(s.idSale, s.total, s.salesmanName) from Sale s where rownum < 2 order by s.total desc")
    Optional<Sale> getSalesMoreExpensive();

    @Query(value = "select new com.krzek.fileprocessor.domain.Sale('', sum(s.total), s.salesmanName) from Sale s group by s.salesmanName order by s.total asc ")
    Optional<List<Sale>> getSalesByNameSalesMan();
}
