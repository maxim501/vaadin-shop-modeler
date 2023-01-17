package com.example.application.views.remainder.view;

import com.am.basketballshop.api.dto.SizeDto;
import com.am.basketballshop.api.dto.remainderProduct.RemainderProductDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.remainder.RemainderEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class RemainderEditorView extends AbstractEditorView<RemainderProductDto, RemainderEditor> {

    public RemainderEditorView(RemainderEditor remainderEditor) {
        init(remainderEditor);
    }

    @Override
    public void buildView() {
        RemainderProductDto item = getEditor().getItem();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        Select<SizeDto> sizeField = select(
                "Размер",
                item.getSizeId(),
                getEditor().getSizes(),
                SizeDto::getName,
                RemainderProductDto::setSizeId);

        TextField remainderFiled = textField("Остатки", (item.getRemainder() != null ? item.getRemainder().toString() : null), (remainder, value) -> {
            if (value.isBlank()) {
                remainder.setRemainder(null);
            } else {
                remainder.setRemainder(Integer.parseInt(value));
            }
        });

        formLayout.add(sizeField, remainderFiled);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        verticalLayout.add(formLayout);

        add(verticalLayout);
    }

    @Override
    public String caption() {
        RemainderProductDto item = getEditor().getItem();
        if (item == null || StringUtils.isBlank(item.getProductModelId())) {
            return "Новые остатки";
        }
        String nameModel = item.getProductModelId();
//        if (item.getProductModelId().getName() != null) {
//            return item.getProductModelId().getName();
//        }
        return nameModel;
    }
}
