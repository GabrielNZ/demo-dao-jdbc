package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.util.List;

public interface SellerDao {

    public void insert(Seller seller);

    public void update(Seller seller);

    public void deleteById(int id);

    public Seller findById(Integer id);

    public List<Seller> findAll();

    public List<Seller> findByDepartment(Department dep) throws SQLException;
}
