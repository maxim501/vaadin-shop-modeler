package com.example.application.views.company.view;

import com.am.basketballshop.api.dto.CompanyDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.company.CompanyBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.COMPANY)
@Route(value = "company/", layout = MainLayout.class)
public class CompanyBrowserView
        extends AbstractBrowserView<CompanyDto, CompanyBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {

    private final FactoryEditorView factoryEditorView;
    private Grid<CompanyDto> grid;

    @Autowired
    public CompanyBrowserView(CompanyBrowser companyBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(companyBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void buildView() {
        grid = new Grid<>(CompanyDto.class, false);
        grid.setWidthFull();
        grid.addColumn(CompanyDto::getName).setHeader("Наименование");

        grid.addItemDoubleClickListener(e -> {
            CompanyDto item = e.getItem();
            editInternal(item.getId());
        });

        grid.setItems(getItems());

        add(grid);
    }

    @Override
    public void afterClose() {
        refreshGrid();
    }

    @Override
    public void add() {
        CompanyEditorView companyEditorView = factoryEditorView.companyEditorView();
        companyEditorView.addEditorCloseListener(this);
        companyEditorView.open();
    }

    @Override
    public void delete() {
        Set<CompanyDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        getBrowser().deleteCompany(selectedItems.iterator().next().getId());
        refreshGrid();
    }

    @Override
    public void edit() {
        Set<CompanyDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String companyId) {
        CompanyEditorView companyEditorView = factoryEditorView.companyEditorView(companyId);
        companyEditorView.addEditorCloseListener(this);
        companyEditorView.open();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        init(new HashMap<>());
    }

    private void refreshGrid() {
        getBrowser().refresh(new HashMap<>());
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }
}
