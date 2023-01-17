package com.example.application.views.base;

import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.views.base.logicScreens.AbstractEditor;
import com.example.application.views.company.CompanyEditor;
import com.example.application.views.company.view.CompanyEditorView;
import com.example.application.views.product.ProductEditor;
import com.example.application.views.product.view.ProductEditorView;
import com.example.application.views.remainder.RemainderEditor;
import com.example.application.views.remainder.view.RemainderEditorView;
import com.example.application.views.section.SectionEditor;
import com.example.application.views.section.view.SectionEditorView;
import com.example.application.views.size.SizeEditor;
import com.example.application.views.size.view.SizeEditorView;
import com.example.application.views.subsection.SubSectionEditor;
import com.example.application.views.subsection.view.SubSectionEditorView;
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

    public CompanyEditorView companyEditorView() {
        CompanyEditor editor = createEditor(CompanyEditor.class);

        return new CompanyEditorView(editor);
    }

    public CompanyEditorView companyEditorView(String companyId) {
        CompanyEditor editor = createEditor(CompanyEditor.class);
        editor.setCompanyId(companyId);

        return new CompanyEditorView(editor);
    }

    public RemainderEditorView remainderEditorView(ProductModelDto productModelDto) {
        RemainderEditor editor = createEditor(RemainderEditor.class);
        editor.setProductModel(productModelDto);

        return new RemainderEditorView(editor);
    }

    public RemainderEditorView remainderEditorView(String remainderId) {
        RemainderEditor editor = createEditor(RemainderEditor.class);
        editor.setRemainderId(remainderId);

        return new RemainderEditorView(editor);
    }

    public SizeEditorView sizeEditorView() {
        SizeEditor editor = createEditor(SizeEditor.class);

        return new SizeEditorView(editor);
    }

    public SizeEditorView sizeEditorView(String sizeId) {
        SizeEditor editor = createEditor(SizeEditor.class);
        editor.setSizeId(sizeId);

        return new SizeEditorView(editor);
    }

    public SectionEditorView sectionEditorView() {
        SectionEditor editor = createEditor(SectionEditor.class);

        return new SectionEditorView(editor);
    }

    public SectionEditorView sectionEditorView(String sectionId) {
        SectionEditor editor = createEditor(SectionEditor.class);
        editor.setSectionId(sectionId);

        return new SectionEditorView(editor);
    }

    public SubSectionEditorView subSectionEditorView(String id) {
        SubSectionEditor editor = createEditor(SubSectionEditor.class);
        editor.setSubSectionId(id);

        return new SubSectionEditorView(editor);
    }

    public SubSectionEditorView subSectionEditorView(SectionDto sectionDto) {
        SubSectionEditor editor = createEditor(SubSectionEditor.class);
        editor.setSection(sectionDto);

        return new SubSectionEditorView(editor);
    }
//    subSectionEditorView

    public <T, E extends AbstractEditor<T>> E createEditor(Class<E> eClass) {
        return beanFactory.getBean(eClass);
    }
}
