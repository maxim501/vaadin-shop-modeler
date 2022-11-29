package com.example.application.views.base;

import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.views.base.logicScreens.AbstractEditor;
import com.example.application.views.product.ProductEditor;
import com.example.application.views.product.view.ProductEditorView;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SpringComponent
public class FactoryEditorView {

    private final BeanFactory beanFactory;

    public ProductEditorView productEditorView(String id) {
        ProductEditor editor = createEditor(ProductEditor.class);
        editor.setProductId(id);

        return new ProductEditorView(editor);
    }

    public ProductEditorView productEditorView(SubSectionDto subSectionDto) {
        ProductEditor editor = createEditor(ProductEditor.class);
        editor.setSubSection(subSectionDto);

        return new ProductEditorView(editor);
    }

    public <T, E extends AbstractEditor<T>> E createEditor(Class<E> eClass) {
        return beanFactory.getBean(eClass);
    }
}
