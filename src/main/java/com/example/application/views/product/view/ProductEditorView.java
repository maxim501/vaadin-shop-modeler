package com.example.application.views.product.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.example.application.views.base.AbstractEditorView;
import com.example.application.views.product.ProductEditor;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;

public class ProductEditorView extends AbstractEditorView<ProductDto, ProductEditor> {

    public ProductEditorView(ProductEditor productEditor) {
        init(productEditor);
    }

    @Override
    public void buildView() {
        ProductDto item = getEditor().getItem();

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
        add(formLayout);
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

    /*protected VerticalLayout createProductModelLayout() {
        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        plusButton.getElement().setAttribute("aria-label", "Добавить модель");
        plusButton.addClickListener(v -> {

        })
    }*/



    /*protected void clickEventMenuItem(String subSectionId) {
        List<ProductDto> productsBySubSection = shopService.getProductsBySubSection(subSectionId);
        splitLayout = new SplitLayout();
        splitLayout.setWidthFull();
        splitLayout.addToPrimary(createGridProductList(productsBySubSection));
        //splitLayout.addToSecondary(createGridProductList(productsBySubSection));
        add(splitLayout);
    }*/


    /*protected Grid<ProductDto> createGridProductList(List<ProductDto> products) {
        Grid<ProductDto> grid = new Grid<>(ProductDto.class, false);
        grid.setWidthFull();
        grid.addColumn(e -> {
            CompanyDto companyDto = e.getCompany();
            if (companyDto != null) {
                return companyDto.getName() + " " + e.getNameModel();
            }
            return e.getNameModel();
        }).setHeader("Наименование");
        grid.addColumn(ProductDto::getSumma).setHeader("Сумма");
        grid.addColumn(ProductDto::getNovelty).setHeader("Новинка");

        grid.addItemClickListener(e -> {
            ProductDto productDto = e.getItem();
            List<ProductModelDto> productModels = productDto.getProductModels();

            currentProductModelForms = productModels.stream().collect(Collectors.toMap(Function.identity(), model -> {
                FormLayout formLayout = new FormLayout();

                TextField codeField = new TextField("Код");
                codeField.setValue(model.getCode());

                TextField nameField = new TextField("Код");
                nameField.setValue(model.getName());

                formLayout.add(codeField, nameField);

                formLayout.setResponsiveSteps(
                        new FormLayout.ResponsiveStep("0", 1),
                        new FormLayout.ResponsiveStep("500px", 2));
                return formLayout;
            }));
        });

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setItems(products);

        return grid;
    }*/

}
