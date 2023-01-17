package com.example.application.views.base;

import com.example.application.views.base.actions.HasAddAction;
import com.example.application.views.base.actions.HasDeleteAction;
import com.example.application.views.base.actions.HasEditAction;
import com.example.application.views.base.logicScreens.AbstractBrowser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractBrowserView<T, K extends AbstractBrowser<T>> extends VerticalLayout {

    private K browser;

    public void init(Map<String, Object> params) {
        Objects.requireNonNull(browser);
        browser.init(params);

        buildViewInternal();
    }

    public void setBrowser(K browser) {
        this.browser = browser;
    }

    public K getBrowser() {
        return browser;
    }

    protected List<T> getItems() {
        return getBrowser().getListItems();
    }

    public void buildViewInternal() {
        removeAll();

        if (hasButtonsFrame()) {
            createButtonsFrame();
        }

        buildView();
    }

    private boolean hasButtonsFrame() {
        return this instanceof HasAddAction ||
                this instanceof HasEditAction ||
                this instanceof HasDeleteAction;
    }

    private void createButtonsFrame() {
        HorizontalLayout buttonsFrame = new HorizontalLayout();
        buttonsFrame.setWidthFull();

        if (this instanceof HasAddAction) {
            Button addButton = new Button("Добавить");
            addButton.addClickListener(e -> ((HasAddAction) this).add());
            buttonsFrame.add(addButton);
        }

        if (this instanceof HasEditAction) {
            Button editButton = new Button("Изменить");
            editButton.addClickListener(e -> ((HasEditAction) this).edit());
            buttonsFrame.add(editButton);
        }

        if (this instanceof HasDeleteAction) {
            Button deleteButton = new Button("Удалить");
            deleteButton.addClickListener(e -> ((HasDeleteAction) this).delete());
            buttonsFrame.add(deleteButton);
        }

        add(buttonsFrame);
    }

    public abstract void buildView();
}
