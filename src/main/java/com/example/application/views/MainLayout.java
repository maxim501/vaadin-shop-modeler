package com.example.application.views;


import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.Constants;
import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.service.ShopService;
import com.example.application.views.about.AboutView;
import com.example.application.views.product.view.ProductBrowserView;
import com.example.application.views.product.view.ProductEditorView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
@Route(value = "")
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private ShopService shopService;

    @Autowired
    public MainLayout(ShopService shopService) {
        this.shopService = shopService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1(Constants.TITLE);
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();

        nav.addItem(createProductNav());
        nav.addItem(new AppNavItem(Constants.TITLE_APP_NAV.ABOUT, AboutView.class, "la la-file"));

        return nav;
    }

    private AppNavItem createProductNav() {
        AppNavItem productItem = new AppNavItem(Constants.TITLE_APP_NAV.PRODUCT);

        List<SectionDto> allSection = shopService.getAllSection();
        allSection.forEach(sectionDto -> {
            AppNavItem sectionItem = new AppNavItem(sectionDto.getName());

            List<SubSectionDto> allSubSectionBySection = shopService.getAllSubSectionBySection(sectionDto.getId());
            allSubSectionBySection.forEach(subSectionDto -> {
                AppNavItem subSectionItem = new AppNavItem(subSectionDto.getName(), ProductBrowserView.class, subSectionDto.getId(), "la la-globe");
                sectionItem.addItem(subSectionItem);
            });

            productItem.addItem(sectionItem);
        });

        return productItem;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
