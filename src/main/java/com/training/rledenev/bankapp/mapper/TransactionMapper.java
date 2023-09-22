package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Transaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, AccountMapper.class})
public interface TransactionMapper {

    @Named("toTransactionDto")
    @Mapping(source = "debitAccount", target = "debitAccountNumber", qualifiedByName = "getNumberFromAccount")
    @Mapping(source = "creditAccount", target = "creditAccountNumber", qualifiedByName = "getNumberFromAccount")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "bigDecimalToDouble")
    @Mapping(source = "debitBalanceDifference", target = "debitBalanceDifference",
            qualifiedByName = "bigDecimalToDouble")
    @Mapping(source = "creditBalanceDifference", target = "creditBalanceDifference",
            qualifiedByName = "bigDecimalToDouble")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapToDate")
    TransactionDto mapToDto(Transaction transaction);

    @Mapping(source = "type", target = "type", qualifiedByName = "stringToEnumName")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "doubleToBigDecimal")
    @Mapping(source = "currencyCode", target = "currencyCode", qualifiedByName = "stringToEnumName")
    Transaction mapToEntity(TransactionDto transactionDto);

    @IterableMapping(qualifiedByName = "toTransactionDto")
    List<TransactionDto> mapToListDto(List<Transaction> transactions);

    @Named("getNumberFromAccount")
    default String getNumberFromAccount(Account account) {
        return account.getNumber();
    }

    @Named("mapToDate")
    default Date mapToDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
