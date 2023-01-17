package com.example.application.views.subsection.view;

import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.subsection.SubSectionEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class SubSectionEditorView extends AbstractEditorView<SubSectionDto, SubSectionEditor> {

    public SubSectionEditorView(SubSectionEditor subSectionEditor) {
        init(subSectionEditor);
    }

    @Override
    public void buildView() {
        SubSectionDto item = getEditor().getItem();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        TextField subSectionField = textField("Наименование", item.getName(), (subSection, value) -> {
            if (value.isBlank()) {
                subSection.setName(null);
            } else {
                subSection.setName(value);
            }
        });

        formLayout.add(subSectionField);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        verticalLayout.add(formLayout);

        add(verticalLayout);
    }

    @Override
    public String caption() {
        SubSectionDto item = getEditor().getItem();
        if (item == null || StringUtils.isBlank(item.getName())) {
            return "Новый подраздел";
        }
        String nameSubSection = item.getName();
//        if (item.getCompany() != null) {
//            return item.getCompany().getName() + " " + nameModel;
//        }
        return nameSubSection;
    }
}
