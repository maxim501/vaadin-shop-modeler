package com.example.application.views.subsection;

import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
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
public class SubSectionEditor extends AbstractEditor<SubSectionDto> {

    private ShopService shopService;

    private String subSectionId;

    private SectionDto currentSection;

    @Autowired
    public SubSectionEditor(ShopService shopService) {
        this.shopService = shopService;
    }

    public void setSubSectionId(String subSectionId) {
        this.subSectionId = subSectionId;
    }

    public void setSection(SectionDto Section) {
        currentSection = Section;
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createSubSection(getItem());
        } else {
            shopService.updateSubSection(subSectionId, getItem());
        }
    }

    @Override
    public SubSectionDto loadItem() {
        if (subSectionId == null) {
            setNew(true);
            SubSectionDto subSectionDto = new SubSectionDto();
            subSectionDto.setSection(currentSection);
            return subSectionDto;
        }
        return shopService.getSubSection(subSectionId);
    }
}
