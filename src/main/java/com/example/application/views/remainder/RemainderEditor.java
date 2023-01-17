package com.example.application.views.remainder;

import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.SizeDto;
import com.am.basketballshop.api.dto.remainderProduct.RemainderProductDto;
import com.example.application.service.ShopService;
import com.example.application.views.base.logicScreens.AbstractEditor;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@SpringComponent
public class RemainderEditor extends AbstractEditor<RemainderProductDto> {
    private ShopService shopService;

    private String remainderId;

    private ProductModelDto currentProductModel;

    @Autowired
    public RemainderEditor(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setRemainderId(String remainderId) {
        this.remainderId = remainderId;
    }

    public void setProductModel(ProductModelDto productModel) {
        currentProductModel = productModel;
    }

    public List<SizeDto> getSizes() {
        return shopService.getAllSizes();
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createRemainder(getItem());
        } else {
            shopService.updateRemainder(remainderId, getItem());
        }
    }

    @Override
    public RemainderProductDto loadItem() {
        if (remainderId == null) {
            setNew(true);
            RemainderProductDto remainderProductDto = new RemainderProductDto();
            remainderProductDto.setProductModelId(currentProductModel.getId());
            return remainderProductDto;
        }
        return shopService.getRemainder(remainderId);
    }
}
