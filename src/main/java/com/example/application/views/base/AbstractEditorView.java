package com.example.application.views.base;

import com.example.application.views.base.logicScreens.AbstractEditor;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public abstract class AbstractEditorView<T, K extends AbstractEditor<T>> extends Dialog {

    private K editor;
    private EditorCloseListener editorCloseListener;

    public void init(K editor) {
        this.editor = editor;
        Objects.requireNonNull(editor);
        editor.init();

        buildViewInternal();
    }

    private void buildViewInternal() {
        setHeaderTitle(caption());
        setResizable(true);
        setDraggable(true);

        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> closeInternal());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        getHeader().add(closeButton);

        buildView();

        Button cancelButton = new Button("Отмена", this::closeDialog);
        getFooter().add(cancelButton);
        getFooter().add(createSaveBtn());
    }

    public K getEditor() {
        return editor;
    }

    public abstract void buildView();

    public abstract String caption();

    public void closeDialog(ClickEvent<Button> e) {
        closeInternal();
    }

    public void saveDialog(ClickEvent<Button> e) {
        editor.commitInternal();
        closeInternal();
    }

    public Button createSaveBtn() {
        return new Button("Сохранить", this::saveDialog);
    }

    private void closeInternal() {
        //todo добавить проверку на isModified если есть изменения спрашивать точно ли хотите закрыть окно
        close();

        if (editorCloseListener != null) {
            editorCloseListener.afterClose();
        }
    }

    //===========Создание различных полей================
    public TextField textField(String name, String value, BiConsumer<T, String> listener) {
        TextField textField = new TextField();
        textField.setLabel(name);

        value = value != null ? value : "";
        textField.setValue(value);

        if (listener != null) {
            textField.addValueChangeListener(event -> callListener(listener, event));
        }
        return textField;
    }

    public TextField textField(String name, String value) {
        return textField(name, value, null);
    }

    public TextArea textArea(String name, String value, Integer limit, BiConsumer<T, String> listener) {
        TextArea textArea = new TextArea();
        textArea.setLabel(name);
        value = value != null ? value : "";
        textArea.setValue(value);
        if (listener != null) {
            textArea.addValueChangeListener(event -> callListener(listener, event));
        }
        textArea.setMaxLength(limit);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        textArea.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + limit);
        });
        return textArea;
    }

    public <E> Select<E> select(String name,
                                E value,
                                List<E> items,
                                ItemLabelGenerator<E> labelGenerator,
                                BiConsumer<T, E> listener) {
        Select<E> select = new Select<>();
        select.setItems(items);
        select.setLabel(name);
        select.setItemLabelGenerator(labelGenerator);
        select.setValue(value);
        if (listener != null) {
            select.addValueChangeListener(event -> callListener(listener, event));
        }
        return select;
    }

    public Checkbox checkBox(String name, Boolean value, BiConsumer<T, Boolean> listener) {
        Checkbox checkbox = new Checkbox();
        checkbox.setLabel(name);
        checkbox.setValue(value);
        if (listener != null) {
            checkbox.addValueChangeListener(event -> callListener(listener, event));
        }
        return checkbox;
    }

    public IntegerField integerField(String name, Integer value, BiConsumer<T, Integer> listener) {
        IntegerField integerField = new IntegerField();
        integerField.setLabel(name);
        integerField.setValue(value);
        if (listener != null) {
            integerField.addValueChangeListener(event -> callListener(listener, event));
        }
        return integerField;
    }
    //================================================


    private <C extends Component, V> void callListener(BiConsumer<T, V> listener,
                                                       AbstractField.ComponentValueChangeEvent<C, V> event) {
        Objects.requireNonNull(editor);
        editor.callListener(listener, event);
    }

    public void addEditorCloseListener(EditorCloseListener listener) {
        if (editorCloseListener == listener) {
            return;
        }
        editorCloseListener = listener;
    }
}
