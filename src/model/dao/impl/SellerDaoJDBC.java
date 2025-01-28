package model.dao.impl;

import db.DB;
import db.DbException;
import jdk.jshell.spi.SPIResolutionException;
import model.dao.Instantiate;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO seller (Name,Email,BirthDate,BaseSalary,DepartmentId) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, java.sql.Date.valueOf(seller.getBirthday()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                System.out.println("Rows affected: " + rowsAffected);
                while (rs.next()) {
                    System.out.println("Generated key: " + rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }else{
                throw new DbException("Insert failed! No rows affected.");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE seller SET Name = ?, Email = ?, BirthDate = ?,BaseSalary = ?,DepartmentId = ? WHERE Id = ?");
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, java.sql.Date.valueOf(seller.getBirthday()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Seller findById(Integer id) {
        Connection conn = DB.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*, department.Name FROM seller JOIN department on seller.DepartmentId = department.Id where seller.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                Department dep = Instantiate.instantiateDepartment(rs);
                Seller seller = Instantiate.instantiateSeller(rs, dep);

                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Seller> sellers = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM seller JOIN department ON department.Id = seller.DepartmentId ORDER BY seller.Name");
            rs = ps.executeQuery();

            while (rs.next()) {
                Seller seller = Instantiate.instantiateSeller(rs, Instantiate.instantiateDepartment(rs));
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Seller> sellers = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM seller JOIN department ON department.Id = seller.DepartmentId WHERE department.id = ? ORDER BY seller.Name");
            ps.setInt(1, dep.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                sellers.add(Instantiate.instantiateSeller(rs, dep));
            }
            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }
}
