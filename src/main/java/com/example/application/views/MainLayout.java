package com.example.application.views;


import com.am.basketballshop.api.dto.ProductModelDto;
import com.am.basketballshop.api.dto.SectionDto;
import com.am.basketballshop.api.dto.product.ProductDto;
import com.am.basketballshop.api.dto.subSection.SubSectionDto;
import com.example.application.Constants;
import com.example.application.components.appnav.AppNav;
import com.example.application.components.appnav.AppNavItem;
import com.example.application.service.ShopService;
import com.example.application.views.about.AboutView;
import com.example.application.views.company.view.CompanyBrowserView;
import com.example.application.views.product.view.ProductBrowserView;
import com.example.application.views.remainder.view.RemainderBrowserView;
import com.example.application.views.section.view.SectionBrowserView;
import com.example.application.views.size.view.SizeBrowserView;
import com.example.application.views.subsection.view.SubSectionBrowserView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

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
        nav.addItem(createRemainders());
        nav.addItem(createDirectories());
        nav.addItem(new AppNavItem(Constants.TITLE_APP_NAV.ABOUT, AboutView.class, "la la-file"));

        return nav;
    }

    private AppNavItem createProductNav() {
        AppNavItem productItem = new AppNavItem(Constants.TITLE_APP_NAV.PRODUCT);

        List<SectionDto> allSection = shopService.getAllSection();
        allSection.forEach(sectionDto -> {
            AppNavItem sectionItem = new AppNavItem(sectionDto.getName());

            List<SubSectionDto> allSubSectionBySection = shopService.getAllSubSectionBySection(sectionDto.getId());
            if (allSubSectionBySection.size() != 0) {
                allSubSectionBySection.forEach(subSectionDto -> {
                    AppNavItem subSectionItem = new AppNavItem(subSectionDto.getName(), ProductBrowserView.class, subSectionDto.getId(), "la la-globe");
                    sectionItem.addItem(subSectionItem);
                });

            }
            productItem.addItem(sectionItem);
        });

        return productItem;
    }

    private AppNavItem createCompanyNav() {
        AppNavItem companyItem = new AppNavItem(Constants.TITLE_APP_NAV.COMPANY, CompanyBrowserView.class, "la la-globe");

        return companyItem;
    }

    private AppNavItem createSectionNav() {
        AppNavItem sectionItem = new AppNavItem(Constants.TITLE_APP_NAV.SECTION, SectionBrowserView.class, "la la-globe");

        return sectionItem;
    }

    private AppNavItem createSubSectionNav() {
        AppNavItem subSectionItem = new AppNavItem(Constants.TITLE_APP_NAV.SUBSECTION);

        List<SectionDto> allSection = shopService.getAllSection();
        allSection.forEach(sectionDto -> {
            AppNavItem sectionItem = new AppNavItem(sectionDto.getName(), SubSectionBrowserView.class, sectionDto.getId(), "la la-globe");

            subSectionItem.addItem(sectionItem);
        });

        return subSectionItem;
    }

    private AppNavItem createSizeNav() {
        AppNavItem sizeItem = new AppNavItem(Constants.TITLE_APP_NAV.SIZE, SizeBrowserView.class, "la la-globe");

        return sizeItem;
    }

    private AppNavItem createDirectories() {
        AppNavItem directoriesItem = new AppNavItem(Constants.TITLE_APP_NAV.DIRECTORIES);
        directoriesItem.addItem(createCompanyNav(), createSectionNav(), createSubSectionNav(), createSizeNav());

        return directoriesItem;
    }

    private AppNavItem createRemainders() {
        AppNavItem remaindersItem = new AppNavItem(Constants.TITLE_APP_NAV.REMAINDER);

        List<SectionDto> allSection = shopService.getAllSection();
        allSection.forEach(sectionDto -> {
            AppNavItem sectionItem = new AppNavItem(sectionDto.getName());

            List<SubSectionDto> allSubSectionBySection = shopService.getAllSubSectionBySection(sectionDto.getId());
            if (allSubSectionBySection.size() != 0) {
                allSubSectionBySection.forEach(subSectionDto -> {
                    AppNavItem subSectionItem = new AppNavItem(subSectionDto.getName());

                    List<ProductDto> allProductBySubSection = shopService.getProductsBySubSection(subSectionDto.getId());
                    allProductBySubSection.forEach(productDto -> {
                        AppNavItem productItem = new AppNavItem(productDto.getNameModel());

                        List<ProductModelDto> allProductModelByProduct = productDto.getProductModels();
                        allProductModelByProduct.forEach(productModelDto -> {
                            AppNavItem productModelItem = new AppNavItem(productModelDto.getName(), RemainderBrowserView.class, productModelDto.getId(), "la la-globe");
                            productItem.addItem(productModelItem);
                        });

                        subSectionItem.addItem(productItem);
                    });

                    sectionItem.addItem(subSectionItem);
                });
            }
            remaindersItem.addItem(sectionItem);
        });

        return remaindersItem;
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
