package Enos.projetoSpring.screenmatch.models;

import Enos.projetoSpring.screenmatch.enums.EmployeePosition;
import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "position",nullable = false)
    private EmployeePosition position;
    @ManyToOne
    private Title title;

    public Employee(){}

    public Employee(String name,EmployeePosition position){
        this.name = name;
        this.position = position;
    }

    protected void setTitle(Title title){this.title = title; }

    public Long getId() {
        return id;
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
