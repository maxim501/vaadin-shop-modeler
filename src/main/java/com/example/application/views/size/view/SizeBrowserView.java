package com.example.application.views.size.view;

import com.am.basketballshop.api.dto.SizeDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.size.SizeBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.SIZE)
@Route(value = "size/", layout = MainLayout.class)
public class SizeBrowserView
        extends AbstractBrowserView<SizeDto, SizeBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {
    private final FactoryEditorView factoryEditorView;
    private Grid<SizeDto> grid;

    @Autowired
    public SizeBrowserView(SizeBrowser sizeBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(sizeBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        init(new HashMap<>());
    }

    @Override
    public void buildView() {
        grid = new Grid<>(SizeDto.class, false);
        grid.setWidthFull();
        grid.addColumn(SizeDto::getCode).setHeader("Код размера");
        grid.addColumn(SizeDto::getName).setHeader("Номер размера");

        grid.addItemDoubleClickListener(e -> {
            SizeDto item = e.getItem();
            editInternal(item.getId());
        });

        grid.setItems(getItems());

        add(grid);
    }

    @Override
    public void add() {
        SizeEditorView sizeEditorView = factoryEditorView.sizeEditorView();
        sizeEditorView.addEditorCloseListener(this);
        sizeEditorView.open();
    }

    @Override
    public void delete() {
        Set<SizeDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        getBrowser().deleteSize(selectedItems.iterator().next().getId());
        refreshGrid();
    }

    @Override
    public void edit() {
        Set<SizeDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String sizeId) {
        SizeEditorView sizeEditorView = factoryEditorView.sizeEditorView(sizeId);
        sizeEditorView.addEditorCloseListener(this);
        sizeEditorView.open();
    }

    @Override
    public void afterClose() {
        refreshGrid();
    }

    private void refreshGrid() {
        getBrowser().refresh(new HashMap<>());
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }
}
