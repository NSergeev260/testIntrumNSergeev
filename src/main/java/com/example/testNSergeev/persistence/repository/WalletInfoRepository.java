package com.example.testNSergeev.persistence.repository;

import com.example.testNSergeev.persistence.model.WalletInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletInfoRepository extends JpaRepository<WalletInfoEntity, UUID> {
}
