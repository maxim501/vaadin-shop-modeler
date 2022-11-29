package com.example.application.views.product.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.product.ProductEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductEditorView extends AbstractEditorView<ProductDto, ProductEditor> {

    private Map<FormLayout, ProductModelDto> modelsMap = new HashMap<>();

    public ProductEditorView(ProductEditor productEditor) {
        init(productEditor);
    }

    @Override
    public void buildView() {
        ProductDto item = getEditor().getItem();
        /*Scroller scroller = new Scroller();
        scroller.setWidthFull();*/

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();

        FormLayout formLayout = new FormLayout();

        TextField modelNameField = textField("Наименование", item.getNameModel(), (product, value) -> {
            if (value.isBlank()) {
                product.setNameModel(null);
            } else {
                product.setNameModel(value);
            }
        });

        Checkbox noveltyField = checkBox("Новинка", item.getNovelty(), ProductDto::setNovelty);

        TextArea descriptionField = textArea("Описание", item.getDescription(), 255, (product, value) -> {
            if (value.isBlank()) {
                product.setDescription(null);
            } else {
                product.setDescription(value);
            }
        });

        IntegerField summaField = integerField("Сумма", item.getSumma(), ProductDto::setSumma);
        Div rubleSuffix = new Div();
        rubleSuffix.setText("₽");
        summaField.setSuffixComponent(rubleSuffix);

        Select<CompanyDto> companyField = select(
                "Компания",
                item.getCompany(),
                getEditor().getCompanies(),
                CompanyDto::getName,
                ProductDto::setCompany);

        formLayout.add(companyField, modelNameField, descriptionField, summaField, noveltyField);

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        formLayout.setColspan(descriptionField, 2);
        verticalLayout.add(formLayout);

        verticalLayout.add(getModelsFrame(item.getProductModels()));

        add(verticalLayout);
    }

    private VerticalLayout getModelsFrame(List<ProductModelDto> models) {
        VerticalLayout verticalLayout = new VerticalLayout();

        Button addBtn = new Button(new Icon(VaadinIcon.PLUS));
        addBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        addBtn.addClickListener(e -> {
            ProductModelDto model = new ProductModelDto();
            FormLayout modelForm = createModelForm(model, verticalLayout);

            getEditor().getItem().getProductModels().add(model);
            modelsMap.put(modelForm, model);
            verticalLayout.add(modelForm);
        });
        verticalLayout.add(addBtn);

        if (!models.isEmpty()) {
            modelsMap = models.stream()
                    .collect(Collectors.toMap(model -> {
                        FormLayout modelForm = createModelForm(model, verticalLayout);
                        verticalLayout.add(modelForm);
                        return modelForm;
                    }, key -> key));
        }

        return verticalLayout;
    }

    private FormLayout createModelForm(ProductModelDto model, VerticalLayout verticalLayout) {
        FormLayout formLayout = new FormLayout();
        modelsMap.put(formLayout, model);

        TextField nameField = textField("Наименование", model.getName(), (product, value) -> {
            model.setName(value);
        });
        TextField codeField = textField("Код", model.getCode(), (product, value) -> {
            model.setCode(value);
        });

        Button deleteBtn = new Button(new Icon(VaadinIcon.MINUS));
        deleteBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        deleteBtn.addClickListener(e -> {
            ProductModelDto productModelDto = modelsMap.get(formLayout);
            getEditor().getItem().getProductModels().removeIf(item -> item == productModelDto);
            getEditor().setModified(true);
            modelsMap.remove(formLayout);
            verticalLayout.remove(formLayout);
        });
        deleteBtn.setMaxWidth("42px");
        //todo здесь будет еще загрузка картинок

        formLayout.add(nameField, codeField, deleteBtn);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));

        formLayout.getStyle().set("padding", "10px");
        formLayout.getStyle().set("border", "1px solid grey");

        return formLayout;
    }

    @Override
    public String caption() {
        ProductDto item = getEditor().getItem();
        if (item == null || StringUtils.isBlank(item.getNameModel())) {
            return "Новый товар";
        }
        String nameModel = item.getNameModel();
        if (item.getCompany() != null) {
            return item.getCompany().getName() + " " + nameModel;
        }
        return nameModel;
    }
}
