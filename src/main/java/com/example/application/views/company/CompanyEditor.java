package com.example.application.views.company;

import com.am.basketballshop.api.dto.CompanyDto;
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
public class CompanyEditor extends AbstractEditor<CompanyDto> {

    private ShopService shopService;

    private String companyId;

    @Autowired
    public CompanyEditor(ShopService shopService) {
        this.shopService = shopService;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public void commit() {
        if (Boolean.TRUE.equals(getNew())) {
            shopService.createCompany(getItem());
        } else {
            shopService.updateCompany(companyId, getItem());
        }
    }

    @Override
    public CompanyDto loadItem() {
        if (companyId == null) {
            setNew(true);
            CompanyDto companyDto = new CompanyDto();
            return companyDto;
        }
        return shopService.getCompany(companyId);
    }
}
