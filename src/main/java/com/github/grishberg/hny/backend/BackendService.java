package com.github.grishberg.hny.backend;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BackendService {
    private List<Employee> employees;
    private final Generator generator = new Generator();

    {
        // Init dummy data

        employees = new ArrayList<>();

        employees.add(new Employee("Гриша", Group.G1, "4548"));
        employees.add(new Employee("Маша", Group.G1, "4325"));
        employees.add(new Employee("Саша", Group.G2, "543"));
        employees.add(new Employee("Дениза", Group.G2, "6643"));
        employees.add(new Employee("Андрей", Group.G3, "956743"));
        employees.add(new Employee("Ника", Group.G3, "0834"));
        employees.add(new Employee("Ангелина", Group.G4, "i974"));
        employees.add(new Employee("Леша", Group.G4, "543973"));

        Collections.shuffle(employees);

        ArrayList<Integer> giftNumbers = new ArrayList<>();
        for (int i = 1; i <= employees.size(); i++) {
            giftNumbers.add(i);
        }
        Collections.shuffle(giftNumbers);

        for (int i = 0; i < employees.size(); i++) {
            employees.get(i).setGiftNumber(giftNumbers.get(i));
            generator.addUser(employees.get(i));
        }
        generator.randomize();

        for (int i = 0; i < employees.size(); i++) {
            Employee user = employees.get(i);
            user.setMyGift(generator.giftForUser(user));
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @Nullable
    public Employee getUserByName(String userName) {
        for (Employee user : employees) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
