package com.example.application.views.base.logicScreens;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;

import java.util.function.BiConsumer;

/**
 * Абстрактный класс для редактирования сущности
 *
 * @param <T> - сущность
 */
public abstract class AbstractEditor<T> {

    //Сущность
    private T item;
    //Флаг, были ли измнения в сущности
    private boolean isModified;

    private Boolean isNew = false;

    public void commitInternal(){
        if (!isModified()) {
            return;
        }
        commit();
    }

    /**
     * Коммит сущности
     */
    public abstract void commit();

    /**
     * Загрузка сущности
     */
    public abstract T loadItem();

    public <C extends Component, V> void callListener(BiConsumer<T, V> consumer,
                                                      AbstractField.ComponentValueChangeEvent<C, V> event) {
        if (item == null) {
            return;
        }
        consumer.accept(item, event.getValue());
        setModified(true);
    }

    /**
     * Первичная инициализация
     */
    public AbstractEditor<T> init() {
        T item = loadItem();

        setItem(item);
        setModified(false);

        return this;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
