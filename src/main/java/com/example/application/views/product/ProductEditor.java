package com.example.application.views.product;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.service.ShopService;
import com.example.application.views.base.logicScreens.AbstractEditor;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@SpringComponent
public class ProductEditor extends AbstractEditor<ProductDto> {

    private ShopService shopService;
    private String productId;

    private SubSectionDto currentSubSection;

    @Autowired
    public ProductEditor(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSubSection(SubSectionDto subSection) {
        currentSubSection = subSection;
    }

    public List<CompanyDto> getCompanies() {
        return shopService.getAllCompanies();
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createProduct(getItem());
        } else {
            shopService.updateProduct(productId, getItem());
        }
    }

    @Override
    public ProductDto loadItem() {
        if (productId == null) {
            setNew(true);
            ProductDto productDto = new ProductDto();
            productDto.setSubSection(currentSubSection);
            productDto.setProductModels(new ArrayList<>());
            return productDto;
        }
        return shopService.getProduct(productId);
    }

}
