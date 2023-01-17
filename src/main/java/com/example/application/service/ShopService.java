package com.example.application.service;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.SizeDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.remainderProduct.RemainderProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.client.*;
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
    private final SizeClient sizeClient;

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

    //============PRODUCT_MODEL=====================
    public ProductModelDto getProductModel(String productModelId) {
        return productClient.getProductModel(productModelId);
    }
    //==============================================

    //============SIZE==============================
    public List<SizeDto> getAllSizes() {
        return sizeClient.getAllSizes();
    }

    public SizeDto getSize(String sizeId) {
        return sizeClient.getSize(sizeId);
    }

    public SizeDto createSize(SizeDto sizeDto) {
        return sizeClient.createSize(sizeDto);
    }

    public SizeDto updateSize(String sizeId, SizeDto sizeDto) {
        return sizeClient.updateSize(sizeId, sizeDto);
    }

    public void deleteSize(String sizeId) {
        sizeClient.deleteSize(sizeId);
    }
    //==============================================

    //============SECTION===========================
    public List<SectionDto> getAllSection() {
        return sectionClient.getAllSections();
    }

    public SectionDto getSection(String sectionId) {
        return sectionClient.getSection(sectionId);
    }

    public SectionDto createSection(SectionDto sectionDto) {
        return sectionClient.createSection(sectionDto);
    }

    public SectionDto updateSection(String sectionId, SectionDto sectionDto) {
        return sectionClient.updateSection(sectionId, sectionDto);
    }

    public void deleteSection(String sectionId) {
        sectionClient.deleteSection(sectionId);
    }
    //==============================================


    //============SUB_SECTION=======================
    public List<SubSectionDto> getAllSubSectionBySection(String sectionId) {
        return sectionClient.getAllSubSectionsBySection(sectionId);
    }

    public SubSectionDto getSubSection(String subSectionId) {
        return sectionClient.getSubSection(subSectionId);
    }

    public void deleteSubSection(String subSectionId) {
        sectionClient.deleteSubSection(subSectionId);
    }

    public SubSectionDto createSubSection(SubSectionDto subSectionDto) {
        return sectionClient.createSubSection(subSectionDto);
    }

    public SubSectionDto updateSubSection(String subSectionId, SubSectionDto subSectionDto) {
        return sectionClient.updateSubSection(subSectionId, subSectionDto);
    }


    //===============================================

    //============REMAINDER==========================
    public List<RemainderProductDto> getAllRemainderByModel(String modelId) {
        return remainderClient.getRemainderProduct(modelId);
    }

    public RemainderProductDto getRemainder(String remainderId) {
        return remainderClient.getRemainder(remainderId);
    }

    public RemainderProductDto createRemainder(RemainderProductDto RemainderProductDto) {
        return remainderClient.createRemainderProduct(RemainderProductDto);
    }

    public RemainderProductDto updateRemainder(String remainderId, RemainderProductDto remainderProductDto) {
        return remainderClient.updateRemainderProduct(remainderId, remainderProductDto);
    }

    public void deleteRemainder(String remainderId) {
        remainderClient.deleteRemainderProduct(remainderId);
    }

    //==============================================

    //============COMPANY===========================
    public List<CompanyDto> getAllCompanies() {
        return companyClient.getAllCompanies();
    }

    public CompanyDto getCompany(String companyId) {
        return companyClient.getCompany(companyId);
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        return companyClient.createCompany(companyDto);
    }

    public CompanyDto updateCompany(String companyId, CompanyDto companyDto) {
        return companyClient.updateCompany(companyId, companyDto);
    }

    public void deleteCompany(String companyId) {
        companyClient.deleteCompany(companyId);
    }
    //==============================================
}
