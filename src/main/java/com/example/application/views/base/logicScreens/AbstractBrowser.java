package com.example.application.views.base.logicScreens;

import java.util.List;
import java.util.Map;

public abstract class AbstractBrowser<T> {

    private List<T> listItems;

    public abstract List<T> loadList(Map<String, Object> params);

    public void init(Map<String, Object> params) {
        listItems = loadList(params);
    }

    public void refresh(Map<String, Object> params) {
        listItems = loadList(params);
    }

    public List<T> getListItems() {
        return listItems;
    }
}
