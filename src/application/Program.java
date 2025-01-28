package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Program {
    public static void main(String[] args) throws SQLException {

        SellerDao sellerDao = new DaoFactory().createSellerDao();

        System.out.println("=======Find By Id=======");

        System.out.println(sellerDao.findById(3));

        System.out.println("=======Find By Department=======");

        Department department = new Department(2, "Eletronic");

        List<Seller> sellers = sellerDao.findByDepartment(department);

        for (Seller seller : sellers) {
            System.out.println(seller);
        }
        System.out.println("=======Find All=======");

        List<Seller> seller = sellerDao.findAll();

        for (Seller seller1 : seller) {
            System.out.println(seller1);
        }

        System.out.println("=======Insert Into=======");

        sellerDao.insert(new Seller(null, "Greg", "greg@gmail.com", LocalDate.parse("1998-04-27"), department, 3000.0));

        System.out.println("=======Update=======");

        Seller s = sellerDao.findById(8);
        s.setName("Carl");
        s.setEmail("carl@gmail.com");
        s.setBaseSalary(2700.0);
        s.setBirthday(LocalDate.parse("1989-07-13"));

        sellerDao.update(s);

        sellerDao.deleteById(10);
    }
}
