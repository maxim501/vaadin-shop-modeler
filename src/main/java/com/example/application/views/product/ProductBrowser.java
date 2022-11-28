package com.example.application.views.product;

import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.service.ShopService;
import com.example.application.views.base.logicScreens.AbstractBrowser;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@SpringComponent
public class ProductBrowser extends AbstractBrowser<ProductDto> {

    @Autowired
    private ShopService shopService;

    @Override
    public List<ProductDto> loadList(Map<String, Object> params) {
        return shopService.getProductsBySubSection((String) params.get("subSectionId"));
    }

    public void deleteProduct(String productId) {
        shopService.deleteProduct(productId);
    }

    public SubSectionDto getSubSection(String productId) {
        return shopService.getSubSection(productId);
    }
}
