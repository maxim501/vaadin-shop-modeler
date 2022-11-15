package com.example.application.views.helloworld;

import com.example.application.service.ShopService;
import com.example.application.views.MainLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    @Autowired
    private ShopService shopService;

    public HelloWorldView() {
        name = new TextField("Product id");
        sayHello = new Button("Get product json");
        sayHello.addClickListener(e -> {
            try {
                Notification.show(shopService.getProductAsJson(name.getValue()));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);
    }

}
