package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.dao.custom.SupplierDao;
import edu.icet.clothingcrm.dto.Supplier;
import edu.icet.clothingcrm.entity.SupplierEntity;
import edu.icet.clothingcrm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDaoImpl implements SupplierDao {

    @Override
    public boolean save(SupplierEntity entity) {
//        Session session = HibernateUtil.getSession();
//        session.getTransaction().begin();
//        session.persist(entity);
//        session.getTransaction().commit();
//        session.close();
//        return true;
        try {
            String sql = "INSERT INTO supplier VALUES (?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    entity.getId(),
                    entity.getName(),
                    entity.getEmail(),
                    entity.getCompany()
            );

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM supplier where id= ?";
            return CrudUtil.execute(sql,id);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier where id='" + id + "'");
            while (resultSet.next()){
                return new Supplier(
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
