package com.example.application.views.size;

import com.am.basketballshop.api.dto.SizeDto;
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
public class SizeBrowser extends AbstractBrowser<SizeDto> {
    @Autowired
    private ShopService shopService;

    @Override
    public List<SizeDto> loadList(Map<String, Object> params) {
        return shopService.getAllSizes();
    }

    public void deleteSize(String sizeId) {
        shopService.deleteSize(sizeId);
    }
}
