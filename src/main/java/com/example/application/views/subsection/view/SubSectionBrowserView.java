package com.example.application.views.subsection.view;

import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.Constants;
import com.example.application.views.MainLayout;
import com.example.application.views.base.AbstractBrowserView;
import com.example.application.views.base.EditorCloseListener;
import com.example.application.views.base.FactoryEditorView;
import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.subsection.SubSectionBrowser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@PageTitle(Constants.TITLE_APP_NAV.SUBSECTION)
@Route(value = "subsection/:subSectionId", layout = MainLayout.class)
public class SubSectionBrowserView
        extends AbstractBrowserView<SubSectionDto, SubSectionBrowser>
        implements BeforeEnterObserver, HasAddAction, HasEditAction, HasDeleteAction, EditorCloseListener {

    private final FactoryEditorView factoryEditorView;

    private String sectionId;

    private Grid<SubSectionDto> grid;

    @Autowired
    public SubSectionBrowserView(SubSectionBrowser subSectionBrowser, FactoryEditorView factoryEditorView) {
        setBrowser(subSectionBrowser);
        this.factoryEditorView = factoryEditorView;
    }

    @Override
    public void buildView() {
        grid = new Grid<>(SubSectionDto.class, false);
        grid.setWidthFull();
        grid.addColumn(SubSectionDto::getName).setHeader("Наименование");

        grid.addItemDoubleClickListener(e -> {
            SubSectionDto item = e.getItem();
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
        SectionDto section = getBrowser().getSection(sectionId);
        SubSectionEditorView subSectionEditorView = factoryEditorView.subSectionEditorView(section);
        subSectionEditorView.addEditorCloseListener(this);
        subSectionEditorView.open();
    }

    @Override
    public void delete() {
        Set<SubSectionDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }

        getBrowser().deleteSubSection(selectedItems.iterator().next().getId());
        refreshGrid();
    }

    @Override
    public void edit() {
        Set<SubSectionDto> selectedItems = grid.getSelectedItems();
        if (selectedItems == null) {
            return;
        }

        if (selectedItems.size() > 1) {
            return;
        }
        editInternal(selectedItems.iterator().next().getId());
    }

    protected void editInternal(String subSectionId) {
        SubSectionEditorView subSectionEditorView = factoryEditorView.subSectionEditorView(subSectionId);
        subSectionEditorView.addEditorCloseListener(this);
        subSectionEditorView.open();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> sectionIdOptional = event.getRouteParameters().get("subSectionId");
        sectionId = sectionIdOptional.orElse(null);

        if (sectionId != null) {
            init(Map.of("subSectionId", sectionId));
        }
    }

    private void refreshGrid() {
        getBrowser().refresh(Map.of("subSectionId", sectionId));
        grid.setItems(getItems());
        grid.getDataProvider().refreshAll();
    }
}
