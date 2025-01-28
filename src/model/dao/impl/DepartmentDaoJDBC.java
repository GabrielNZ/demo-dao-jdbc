package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.dao.Instantiate;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                System.out.println("Rows affected: " + rowsAffected);
                while (rs.next()) {
                    System.out.println("Generated key: " + rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Department department) {
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("UPDATE Department SET Name = ? WHERE Id = ?");
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());

            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbIntegrityException("Failed to UPDATE! Rolling Back... Reason: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbIntegrityException("Failed to Rollback... Reason: " + e1.getMessage());
            } finally {
                DB.closeStatement(ps);
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM Department WHERE Id = ?");
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Department WHERE Id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Instantiate.instantiateDepartment(rs);
            }

            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        try {
            List<Department> departments = new ArrayList<Department>();
            ps = conn.prepareStatement("SELECT * FROM Department");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departments.add(Instantiate.instantiateDepartment(rs));
            }
            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }
}
