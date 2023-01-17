package com.example.application.views.company.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.company.CompanyEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class CompanyEditorView extends AbstractEditorView<CompanyDto, CompanyEditor> {

    public CompanyEditorView(CompanyEditor companyEditor){init(companyEditor);}

    @Override
    public void buildView() {
        CompanyDto item = getEditor().getItem();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        TextField companyNameFiled = textField("Наименование", item.getName(),(company, value) -> {
            if (value.isBlank()){
                company.setName(null);
            }else {
                company.setName(value);
            }
        });

        formLayout.add(companyNameFiled);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("500px",2));
        verticalLayout.add(formLayout);

        add(verticalLayout);
    }

    @Override
    public String caption() {
        CompanyDto item = getEditor().getItem();
        if (item==null|| StringUtils.isBlank(item.getName())){
            return "Новая компания";
        }
        String nameCompany = item.getName();
        if (item.getName() != null) {
            return item.getName();
        }
        return nameCompany;
    }
}
