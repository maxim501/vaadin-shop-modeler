package com.example.application.views.section.view;

import com.am.basketballshop.api.dto.SectionDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.section.SectionBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.SECTION)
@Route(value = "section/", layout = MainLayout.class)
public class SectionBrowserView
        extends AbstractBrowserView<SectionDto, SectionBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {

    private final FactoryEditorView factoryEditorView;
    private Grid<SectionDto> grid;

    @Autowired
    public SectionBrowserView(SectionBrowser sectionBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(sectionBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        init(new HashMap<>());
    }

    @Override
    public void buildView() {
        grid = new Grid<>(SectionDto.class, false);
        grid.setWidthFull();
        grid.addColumn(SectionDto::getName).setHeader("Наименование");

        grid.addItemDoubleClickListener(e -> {
            SectionDto item = e.getItem();
            editInternal(item.getId());
        });

        grid.setItems(getItems());

        add(grid);
    }

    @Override
    public void add() {
        SectionEditorView sectionEditorView = factoryEditorView.sectionEditorView();
        sectionEditorView.addEditorCloseListener(this);
        sectionEditorView.open();
    }

    @Override
    public void delete() {
        Set<SectionDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        getBrowser().deleteSection(selectedItems.iterator().next().getId());
        refreshGrid();
    }

    @Override
    public void edit() {
        Set<SectionDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String sectionId) {
        SectionEditorView sectionEditorView = factoryEditorView.sectionEditorView(sectionId);
        sectionEditorView.addEditorCloseListener(this);
        sectionEditorView.open();
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
