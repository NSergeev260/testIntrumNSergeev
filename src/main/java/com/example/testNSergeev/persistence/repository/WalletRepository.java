package com.example.testNSergeev.persistence.repository;

import java.util.UUID;

import com.example.testNSergeev.persistence.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {
}
