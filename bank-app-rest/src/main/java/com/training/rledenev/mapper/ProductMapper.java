package com.training.rledenev.mapper;

import com.training.rledenev.dto.ProductDto;
import com.training.rledenev.entity.Product;
import com.training.rledenev.entity.enums.ProductType;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends MapperDefault {

    @Named("toProductDto")
    @Mapping(source = "type", target = "type", qualifiedByName = "enumGetName")
    ProductDto mapToDto(Product product);

    @IterableMapping(qualifiedByName = "toProductDto")
    List<ProductDto> mapToListDto(List<Product> products);

    @Named("enumGetName")
    default String enumGetName(ProductType productType) {
        return productType.getName();
    }
}
