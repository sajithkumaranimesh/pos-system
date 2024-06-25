package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.dao.custom.EmployeeDao;
import edu.icet.clothingcrm.dto.Employee;
import edu.icet.clothingcrm.entity.EmployeeEntity;
import edu.icet.clothingcrm.util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public boolean save(EmployeeEntity entity) {
//        Session session = HibernateUtil.getSession();
//        session.getTransaction().begin();
//        session.persist(entity);
//        session.getTransaction().commit();
//        session.close();
//        return true;
        try {
            String sql = "INSERT INTO employee VALUES (?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getAddress()
            );

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String query = "DELETE FROM employee WHERE id = ?";
            return CrudUtil.execute(query, id);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee where id='" + id + "'");
            while (resultSet.next()) {
                return new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
