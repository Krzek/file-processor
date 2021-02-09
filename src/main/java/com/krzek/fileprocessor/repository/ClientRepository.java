package com.krzek.fileprocessor.repository;

import com.krzek.fileprocessor.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
