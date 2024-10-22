package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;

public class Employee {
    private String name;
    private EmployeePosition position;

    public Employee(String name, EmployeePosition position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}
