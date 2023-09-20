package com.training.rledenev.bankapp.mapper;

import com.training.rledenev.bankapp.dto.ProductDto;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named("toProductDto")
    @Mapping(source = "interestRate", target = "interestRate", qualifiedByName = "bigDecimalToDouble",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "type", target = "type", qualifiedByName = "enumGetName")
    ProductDto mapToDto(Product product);

    @IterableMapping(qualifiedByName = "toProductDto")
    List<ProductDto> mapToListDto(List<Product> products);

    @Named("bigDecimalToDouble")
    default Double bigDecimalToDouble(BigDecimal value) {
        return value.doubleValue();
    }

    @Named("enumGetName")
    default String enumGetName(ProductType productType) {
        return productType.getName();
    }
}
