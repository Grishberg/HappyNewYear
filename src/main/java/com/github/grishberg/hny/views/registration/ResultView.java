package com.github.grishberg.hny.views.registration;

import com.github.grishberg.hny.MainView;
import com.github.grishberg.hny.backend.BackendService;
import com.github.grishberg.hny.backend.Employee;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "result", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
@PageTitle("С Новым 2020 годом! Разбирай подарки!")
@CssImport("styles/views/registration/registration-view.css")
public class ResultView extends Div implements AfterNavigationObserver {
    @Autowired
    private BackendService service;
    private Grid<Employee> employees;

    public ResultView() {
        setId("registration-view");
        // Configure Grid
        employees = new Grid<>();
        employees.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        employees.setHeightFull();
        employees.addColumn(Employee::getName).setHeader("Имя");
        employees.addColumn(Employee::getGiftNumber).setHeader("Номер твоего подарка");

        createGridLayout();
    }

    private void createGridLayout() {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        wrapper.setHeightFull();
        wrapper.add(employees);
        add(wrapper);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user
        employees.setItems(service.getEmployees());
    }
}
