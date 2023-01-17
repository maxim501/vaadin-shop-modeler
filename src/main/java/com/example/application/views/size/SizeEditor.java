package com.example.application.views.size;

import com.am.basketballshop.api.dto.SizeDto;
import com.example.application.service.ShopService;
import com.example.application.views.base.logicScreens.AbstractEditor;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@SpringComponent
public class SizeEditor extends AbstractEditor<SizeDto> {
    private ShopService shopService;

    private String sizeId;

    @Autowired
    public SizeEditor(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createSize(getItem());
        } else {
            shopService.updateSize(sizeId, getItem());
        }
    }

    @Override
    public SizeDto loadItem() {
        if (sizeId == null) {
            setNew(true);
            SizeDto sizeDto = new SizeDto();
            return sizeDto;
        }
        return shopService.getSize(sizeId);
    }
}
