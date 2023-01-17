package com.example.application.views.section.view;

import com.am.basketballshop.api.dto.SectionDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.section.SectionEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class SectionEditorView extends AbstractEditorView<SectionDto, SectionEditor> {

    public SectionEditorView(SectionEditor sectionEditor) {
        init(sectionEditor);
    }

    @Override
    public void buildView() {
        SectionDto item = getEditor().getItem();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        TextField companyNameFiled = textField("Наименование", item.getName(), (section, value) -> {
            if (value.isBlank()) {
                section.setName(null);
            } else {
                section.setName(value);
            }
        });

        formLayout.add(companyNameFiled);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        verticalLayout.add(formLayout);

        add(verticalLayout);
    }

    @Override
    public String caption() {
        SectionDto item = getEditor().getItem();
        if (item == null || StringUtils.isBlank(item.getName())) {
            return "Новый раздел";
        }
        String nameSection = item.getName();
        if (item.getName() != null) {
            return item.getName();
        }
        return nameSection;
    }
}
