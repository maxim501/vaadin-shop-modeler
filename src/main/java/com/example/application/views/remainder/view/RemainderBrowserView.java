package com.example.application.views.remainder.view;

import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.SizeDto;
import com.am.basketballshop.api.dto.remainderProduct.RemainderProductDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.remainder.RemainderBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.REMAINDER)
@Route(value = "remainder/:subSectionId", layout = MainLayout.class)
public class RemainderBrowserView
        extends AbstractBrowserView<RemainderProductDto, RemainderBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {

    private final FactoryEditorView factoryEditorView;
    private String productModelId;
    private Grid<RemainderProductDto> grid;

    @Autowired
    public RemainderBrowserView(RemainderBrowser remainderBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(remainderBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> productModelIdOptional = event.getRouteParameters().get("subSectionId");
        productModelId = productModelIdOptional.orElse(null);

        if (productModelId != null) {
            init(Map.of("subSectionId", productModelId));
        }
    }

    @Override
    public void buildView() {
        grid = new Grid<>(RemainderProductDto.class, false);
        grid.setWidthFull();

        grid.addColumn(e -> {
            SizeDto sizeDto = e.getSizeId();
            if (sizeDto != null) {
                return sizeDto.getName();
            }
            return e.getProductModelId();
        }).setHeader("Размер");

        grid.addColumn(RemainderProductDto::getRemainder).setHeader("Остаток товара");

        grid.addItemDoubleClickListener(e -> {
            RemainderProductDto item = e.getItem();
            editInternal(item.getId());
        });

        grid.setItems(getItems());

        add(grid);
    }

    @Override
    public void add() {
        ProductModelDto productModel = getBrowser().getProductModel(productModelId);
        RemainderEditorView remainderEditorView = factoryEditorView.remainderEditorView(productModel);
        remainderEditorView.addEditorCloseListener(this);
        remainderEditorView.open();
    }

    @Override
    public void delete() {
        Set<RemainderProductDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        getBrowser().deleteRemainder(selectedItems.iterator().next().getId());
        refreshGrid();
    }

    @Override
    public void edit() {
        Set<RemainderProductDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String remainderId) {
        RemainderEditorView remainderEditorView = factoryEditorView.remainderEditorView(remainderId);
        remainderEditorView.addEditorCloseListener(this);
        remainderEditorView.open();
    }

    @Override
    public void afterClose() {
        refreshGrid();
    }

    private void refreshGrid() {
        getBrowser().refresh(Map.of("subSectionId", productModelId));
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }
}
