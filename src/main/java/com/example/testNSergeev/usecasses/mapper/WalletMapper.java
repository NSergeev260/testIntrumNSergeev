package com.example.testNSergeev.usecasses.mapper;

import com.example.testNSergeev.persistence.model.WalletEntity;
import com.example.testNSergeev.persistence.model.WalletInfoEntity;
import com.example.testNSergeev.usecasses.dto.WalletInfoResponseDto;
import com.example.testNSergeev.usecasses.dto.WalletRequestDto;
import com.example.testNSergeev.usecasses.dto.WalletResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface WalletMapper {

    WalletEntity fromDtoToEntity(WalletRequestDto walletRequestDto);

    WalletResponseDto fromEntityToDto(WalletEntity walletEntity);

    WalletInfoResponseDto fromEntityToInfoDto(WalletInfoEntity walletInfoEntity);
}
