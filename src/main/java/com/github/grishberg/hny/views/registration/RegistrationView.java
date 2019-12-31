package com.github.grishberg.hny.views.registration;

import com.github.grishberg.hny.MainView;
import com.github.grishberg.hny.backend.BackendService;
import com.github.grishberg.hny.backend.Employee;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "registration", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Готовь подарки к новому 2020 году!")
@CssImport("styles/views/registration/registration-view.css")
public class RegistrationView extends Div implements AfterNavigationObserver {
    @Autowired
    private BackendService service;
    private Grid<Employee> employees;
    private TextField name = new TextField();
    private PasswordField passwd = new PasswordField();
    private Button showNumber = new Button("Show");
    private Binder<Employee> binder;

    public RegistrationView() {
        setId("registration-view");
        // Configure Grid
        employees = new Grid<>();
        employees.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        employees.setHeightFull();
        employees.addColumn(Employee::getName).setHeader("Имя Санты");

        //when a row is selected or deselected, populate form
        employees.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        // Configure Form
        binder = new Binder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        // note that password field isn't bound since that property doesn't exist in
        // Employee

        showNumber.addClickListener(e -> {
            onShowNumberClicked();
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorDiv = new Div();
        editorDiv.setId("editor-layout");
        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, name, "Имя");
        addFormItem(editorDiv, formLayout, passwd, "Пароль");
        createButtonLayout(editorDiv);
        splitLayout.addToSecondary(editorDiv);
    }

    private void createButtonLayout(Div editorDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        showNumber.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(showNumber);
        editorDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        wrapper.setHeightFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(employees);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout,
                             AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user
        employees.setItems(service.getEmployees());
    }

    private void populateForm(Employee value) {
        // Value can be null as well, that clears the form
        binder.readBean(value);

        // The password field isn't bound through the binder, so handle that
        passwd.setValue("");
    }

    private void onShowNumberClicked() {
        String userName = name.getValue();
        Employee user = service.getUserByName(userName);
        if (user == null) {
            return;
        }
        if (!user.getPassword().equals(passwd.getValue())) {
            Notification.show("Неверный пароль");
            return;
        }

        Notification.show("Наклей на свой подарок номер " + user.getGiftNumber());
    }
}
