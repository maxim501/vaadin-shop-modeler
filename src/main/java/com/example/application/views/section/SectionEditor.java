package com.example.application.views.section;

import com.am.basketballshop.api.dto.SectionDto;
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
public class SectionEditor extends AbstractEditor<SectionDto> {

    private ShopService shopService;

    private String sectionId;

    @Autowired
    public SectionEditor(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createSection(getItem());
        } else {
            shopService.updateSection(sectionId, getItem());
        }
    }

    @Override
    public SectionDto loadItem() {
        if (sectionId == null) {
            setNew(true);
            SectionDto sectionDto = new SectionDto();
            return sectionDto;
        }
        return shopService.getSection(sectionId);
    }
}
