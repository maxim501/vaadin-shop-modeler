package com.example.application.views.section;

import com.am.basketballshop.api.dto.SectionDto;
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
public class SectionBrowser extends AbstractBrowser<SectionDto> {

    @Autowired
    private ShopService shopService;

    @Override
    public List<SectionDto> loadList(Map<String, Object> params) {
        return shopService.getAllSection();
    }

    public void deleteSection(String sectionId){shopService.deleteSection(sectionId);}
}
