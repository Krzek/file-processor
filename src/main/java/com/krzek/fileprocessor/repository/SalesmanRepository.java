package com.krzek.fileprocessor.repository;

import com.krzek.fileprocessor.domain.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman, String> {
}
