package model.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Seller implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private LocalDate birthday;
    private Double baseSalary;

    private Department department;

    public Seller() {

    }
    public Seller(Integer id, String name, String email, LocalDate birthday, Department department, Double baseSalary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.department = department;
        this.baseSalary = baseSalary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id+", "+name+", "+email+", "+birthday+", "+ baseSalary+", "+department;
    }

}