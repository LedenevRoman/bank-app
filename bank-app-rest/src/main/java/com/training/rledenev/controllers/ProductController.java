package com.training.rledenev.controllers;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ProductDto;
import com.training.rledenev.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all-active")
    public ResponseEntity<List<ProductDto>> getAllActiveProductDtos() {
        List<ProductDto> productDtos = productService.getAllActiveProductDtos();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/all-active/{type}")
    public ResponseEntity<List<ProductDto>> getAllActiveProductDtos(@PathVariable(name = "type") String productType) {
        List<ProductDto> productDtos = productService.getActiveProductsWithType(productType);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/suitable")
    public ProductDto getSuitableProduct(@RequestBody AgreementDto agreementDto) {
        return productService.getSuitableProduct(agreementDto);
    }
}
