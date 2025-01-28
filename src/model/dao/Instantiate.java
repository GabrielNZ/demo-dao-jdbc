package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Instantiate {
    public static Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthday(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setDepartment(dep);
        return seller;
    }

    public static Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("department.Name"));
        return dep;
    }
}
