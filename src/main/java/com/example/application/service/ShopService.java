package com.example.application.service;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.remainderProduct.ResponseRemainderProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.client.CompanyClient;
import com.example.application.client.ProductClient;
import com.example.application.client.RemainderClient;
import com.example.application.client.SectionClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@SpringComponent
public class ShopService {

    private final ProductClient productClient;
    private final SectionClient sectionClient;
    private final RemainderClient remainderClient;
    private final CompanyClient companyClient;

    private ObjectMapper mapper = new ObjectMapper();

    //============PRODUCT===========================
    public ProductDto getProduct(String productId) {
        return productClient.getProduct(productId);
    }

    public List<ProductDto> getProductsBySubSection(String subSectionId) {
        return productClient.getBySubSection(subSectionId);
    }

    public ProductDto createProduct(ProductDto productDto) {
        return productClient.createProduct(productDto);
    }

    public ProductDto updateProduct(String productId, ProductDto productDto) {
        return productClient.updateProduct(productId, productDto);
    }

    public void deleteProduct(String productId) {
        productClient.deleteProduct(productId);
    }
    //==============================================


    //============SECTION===========================
    public List<SectionDto> getAllSection() {
        return sectionClient.getAllSections();
    }
    //==============================================


    //============SUB_SECTION=======================
    public List<SubSectionDto> getAllSubSectionBySection(String sectionId) {
        return sectionClient.getAllSubSectionsBySection(sectionId);
    }

    public SubSectionDto getSubSection(String subSectionId) {
        return sectionClient.getSubSection(subSectionId);
    }
    //===============================================

    //============REMAINDER==========================
    public List<ResponseRemainderProductDto> getAllRemainderByModel(String modelId) {
        return remainderClient.getRemainderProduct(modelId);
    }
    //==============================================

    //============COMPANY===========================
    public List<CompanyDto> getAllCompanies() {
        return companyClient.getAllCompanies();
    }
    //==============================================
}
