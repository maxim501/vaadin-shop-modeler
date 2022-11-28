package com.example.application.views.product.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.product.ProductBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.PRODUCT)
@Route(value = "product/:subSectionId", layout = MainLayout.class)
public class ProductBrowserView
        extends AbstractBrowserView<ProductDto, ProductBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction {

    private final ProductBrowser browser;
    private final FactoryEditorView factoryEditorView;

    private String subSectionId;

    private Grid<ProductDto> grid;

    @Autowired
    public ProductBrowserView(ProductBrowser productBrowser, FactoryEditorView factoryEditorView) {
        this.browser = productBrowser;
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> subSectionIdOptional = event.getRouteParameters().get("subSectionId");
        subSectionId = subSectionIdOptional.orElse(null);

        if (subSectionId != null) {
            init(browser, Map.of("subSectionId", subSectionId));
        }
    }

    @Override
    public void buildView() {
        grid = new Grid<>(ProductDto.class, false);
        grid.setWidthFull();
        grid.addColumn(ProductDto::getNameModel).setHeader("Наименование");
        grid.addColumn(ProductDto::getSumma).setHeader("Сумма (руб.)");

        grid.addColumn(productDto -> {
            if (productDto.getNovelty()) {
                return "Да";
            }
            return "Нет";
        }).setHeader("Новинка");

        grid.addColumn(e -> {
            CompanyDto companyDto = e.getCompany();
            if (companyDto != null) {
                return companyDto.getName();
            }
            return e.getNameModel();
        }).setHeader("Компания");

        grid.addItemDoubleClickListener(e -> {
            ProductDto item = e.getItem();
            factoryEditorView.productEditorView(item.getId()).open();
        });

        grid.setItems(browser.getListItems());

        add(grid);
    }

    @Override
    public void add() {
        SubSectionDto subSection = browser.getSubSection(subSectionId);
        factoryEditorView.productEditorViewNew(subSection).open();
    }

    @Override
    public void delete() {
        Set<ProductDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        browser.deleteProduct(selectedItems.iterator().next().getId());
    }

    @Override
    public void edit() {
        Set<ProductDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        factoryEditorView.productEditorView(selectedItems.iterator().next().getId()).open();
    }
}
