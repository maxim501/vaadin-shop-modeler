package com.example.application.views.remainder;

import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.remainderProduct.RemainderProductDto;
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
public class RemainderBrowser extends AbstractBrowser<RemainderProductDto> {
    @Autowired
    private ShopService shopService;

    @Override
    public List<RemainderProductDto> loadList(Map<String, Object> params) {
        return shopService.getAllRemainderByModel((String) params.get("subSectionId"));
    }

    public void deleteRemainder(String remainderId) {
        shopService.deleteRemainder(remainderId);
    }

    public ProductModelDto getProductModel(String productModelId) {
        return shopService.getProductModel(productModelId);
    }
}
