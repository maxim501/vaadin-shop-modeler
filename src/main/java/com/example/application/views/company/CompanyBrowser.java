package com.example.application.views.company;

import com.am.basketballshop.api.dto.CompanyDto;
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
public class CompanyBrowser extends AbstractBrowser<CompanyDto> {

    @Autowired
    private ShopService shopService;

    @Override
    public List<CompanyDto> loadList(Map<String, Object> params) {
        return shopService.getAllCompanies();
    }

    public void deleteCompany(String companyId){
        shopService.deleteCompany(companyId);
    }

}
