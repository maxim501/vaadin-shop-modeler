package com.example.application.views.size.view;

import com.am.basketballshop.api.dto.SizeDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.size.SizeEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class SizeEditorView extends AbstractEditorView<SizeDto, SizeEditor> {
    public SizeEditorView(SizeEditor sizeEditor) {
        init(sizeEditor);
    }

    @Override
    public void buildView() {
        SizeDto item = getEditor().getItem();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        TextField sizeCodeFiled = textField("Код размера", item.getCode(), (code, value) -> {
            if (value.isBlank()) {
                code.setCode(null);
            } else {
                code.setCode(value);
            }
        });

        TextField sizeNameFiled = textField("Номер размера", item.getName(), (size, value) -> {
            if (value.isBlank()) {
                size.setName(null);
            } else {
                size.setName(value);
            }
        });

        formLayout.add(sizeCodeFiled, sizeNameFiled);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        verticalLayout.add(formLayout);

        add(verticalLayout);
    }

    @Override
    public String caption() {
        SizeDto item = getEditor().getItem();
        if (item == null || StringUtils.isBlank(item.getName())) {
            return "Новый размер";
        }
        String nameSize = item.getName();
        if (item.getName() != null) {
            return item.getName();
        }
        return nameSize;
    }
}
