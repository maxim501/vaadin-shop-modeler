package com.example.application.views.subsection;

import com.am.basketballshop.api.dto.SectionDto;
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
public class SubSectionBrowser extends AbstractBrowser<SubSectionDto> {

    @Autowired
    private ShopService shopService;

    @Override
    public List<SubSectionDto> loadList(Map<String, Object> params) {
        return shopService.getAllSubSectionBySection((String) params.get("subSectionId"));
    }

    public void deleteSubSection(String subSectionId) {
        shopService.deleteSubSection(subSectionId);
    }

    public SectionDto getSection(String sectionId) {
        return shopService.getSection(sectionId);
    }
}
