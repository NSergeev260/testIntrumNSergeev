package com.example.testNSergeev.usecasses.impl;

import com.example.testNSergeev.api.exeption.WalletBadRequestException;
import com.example.testNSergeev.api.exeption.WalletNotFoundException;
import com.example.testNSergeev.persistence.model.WalletEntity;
import com.example.testNSergeev.persistence.repository.WalletInfoRepository;
import com.example.testNSergeev.persistence.repository.WalletRepository;
import com.example.testNSergeev.usecasses.WalletService;
import com.example.testNSergeev.usecasses.dto.OperationType;
import com.example.testNSergeev.usecasses.dto.WalletInfoResponseDto;
import com.example.testNSergeev.usecasses.dto.WalletRequestDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;
import com.example.testNSergeev.usecasses.mapper.WalletMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletServiceImp implements WalletService {

    private static final BigDecimal START_BALANCE = BigDecimal.ZERO;
    private final WalletMapper walletMapper;
    private final WalletRepository walletRepo;
    private final WalletInfoRepository walletInfoRepo;


    @Override
    @Transactional
    public WalletResponseDto insertWallet() {
        UUID newWalletId = UUID.randomUUID();
        WalletRequestDto newWallet = WalletRequestDto.builder()
                .withWalletId(newWalletId)
                .withBalance(START_BALANCE)
                .withLastOperation(OperationType.CREATED)
                .withAmountOfOperation(START_BALANCE)
                .build();

        if (walletRepo.findById(newWalletId).isPresent()) {
            log.info("Wallet with id {} already exists. FAIL, Time: {}", newWalletId, LocalDateTime.now());
            throw new WalletBadRequestException("Wallet already exists. FAIL");
        }

        WalletEntity walletEntity = walletMapper.fromDtoToEntity(newWallet);
        walletRepo.save(walletEntity);
        log.info("New wallet with id {} was INSERT, Time: {}", walletEntity.getWalletId(), LocalDateTime.now());

        return walletMapper.fromEntityToDto(walletEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletInfoResponseDto getWalletInfo(UUID walletId) {
        return walletInfoRepo
                .findById(walletId)
                .map(walletMapper::fromEntityToInfoDto)
                .orElseThrow(() -> new WalletNotFoundException(
                        "Data with id %s not found".formatted(walletId)));
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponseDto getWallet(UUID walletId) {
        return walletRepo
                .findById(walletId)
                .map(walletMapper::fromEntityToDto)
                .orElseThrow(() -> new WalletNotFoundException(
                        "Data with id %s not found".formatted(walletId)));
    }

    @Override
    @Transactional
    public WalletResponseDto updateWalletDeposit(UUID walletId, BigDecimal amountOfOperation) {
        Optional<WalletEntity> walletEntityRepo = walletRepo.findById(walletId);
        WalletEntity walletUpdateEntity;

        if (walletEntityRepo.isPresent()) {
            walletUpdateEntity = walletEntityRepo.get();
            walletUpdateEntity = WalletEntity.builder()
                    .withWalletId(walletId)
                    .withBalance((walletUpdateEntity.getBalance()).add(amountOfOperation))
                    .withLastOperation(OperationType.DEPOSIT)
                    .withAmountOfOperation(amountOfOperation)
                    .build();
            log.info("Success DEPOSIT. Wallet id is {}, Date: {}", walletId, LocalDateTime.now());
        } else {
            throw new WalletBadRequestException("Data with id %s not found".formatted(walletId));
        }

        walletRepo.save(walletUpdateEntity);
        log.info("Success UPDATE operation. Wallet id is {}, Date: {}", walletId, LocalDateTime.now());
        return walletMapper.fromEntityToDto(walletUpdateEntity);
    }

    @Override
    @Transactional
    public WalletResponseDto updateWalletWithdraw(UUID walletId, BigDecimal amountOfOperation) {
        Optional<WalletEntity> walletEntityRepo = walletRepo.findById(walletId);
        WalletEntity walletUpdateEntity;

        if (walletEntityRepo.isPresent()) {
            walletUpdateEntity = walletEntityRepo.get();

            if (walletUpdateEntity.getBalance().compareTo(amountOfOperation) < 0) {
                log.info("Fail WITHDRAW. Wallet id is {}, Date: {}", walletId, LocalDateTime.now());
                throw new WalletBadRequestException("Not enough funds. Id is %s".formatted(walletId));
            } else {
                walletUpdateEntity = WalletEntity.builder()
                        .withWalletId(walletId)
                        .withBalance((walletUpdateEntity.getBalance()).subtract(amountOfOperation))
                        .withLastOperation(OperationType.WITHDRAW)
                        .withAmountOfOperation(amountOfOperation)
                        .build();
                log.info("Success WITHDRAW. Wallet id is {}, Date: {}", walletId, LocalDateTime.now());
            }
        } else {
            throw new WalletBadRequestException("Data with id %s not found".formatted(walletId));
        }

        walletRepo.save(walletUpdateEntity);
        log.info("Success UPDATE operation. Wallet id is {}, Date: {}", walletId, LocalDateTime.now());
        return walletMapper.fromEntityToDto(walletUpdateEntity);
    }

    @Override
    @Transactional
    public void deleteWallet(UUID walletId) {
        Optional<WalletEntity> walletEntityRepo = walletRepo.findById(walletId);
        WalletEntity walletDelete;

        if (walletEntityRepo.isPresent()) {
            walletDelete = walletEntityRepo.get();
            walletRepo.deleteById(walletDelete.getWalletId());
            log.info("DELETE wallet by id {}, Date: {}", walletId, LocalDateTime.now());
        }
    }
}
