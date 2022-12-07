package com.example.application.views.product.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
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
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {

    private final FactoryEditorView factoryEditorView;

    private String subSectionId;

    private Grid<ProductDto> grid;

    @Autowired
    public ProductBrowserView(ProductBrowser productBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(productBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> subSectionIdOptional = event.getRouteParameters().get("subSectionId");
        subSectionId = subSectionIdOptional.orElse(null);

        if (subSectionId != null) {
            init(Map.of("subSectionId", subSectionId));
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
            editInternal(item.getId());
        });

        grid.setItems(getItems());

        add(grid);
    }

    @Override
    public void add() {
        SubSectionDto subSection = getBrowser().getSubSection(subSectionId);
        ProductEditorView productEditorView = factoryEditorView.productEditorView(subSection);
        productEditorView.addEditorCloseListener(this);
        productEditorView.open();
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

        getBrowser().deleteProduct(selectedItems.iterator().next().getId());
        refreshGrid();
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
        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String productId) {
        ProductEditorView productEditorView = factoryEditorView.productEditorView(productId);
        productEditorView.addEditorCloseListener(this);
        productEditorView.open();
    }

    @Override
    public void afterClose() {
        refreshGrid();
    }

    private void refreshGrid() {
        getBrowser().refresh(Map.of("subSectionId", subSectionId));
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }
}
