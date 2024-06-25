package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.bo.custom.ProductBo;
import edu.icet.clothingcrm.dao.custom.ProductDao;
import edu.icet.clothingcrm.dto.Product;
import edu.icet.clothingcrm.entity.ProductEntity;
import edu.icet.clothingcrm.util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDaoImpl implements ProductDao {

    @Override
    public boolean save(ProductEntity entity) {
//        Session session = HibernateUtil.getSession();
//        session.getTransaction().begin();
//        session.persist(entity);
//        session.getTransaction().commit();
//        session.close();
//        return true;
        try {
            String sql = "INSERT INTO product VALUES (?,?,?,?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    entity.getId(),
                    entity.getName(),
                    entity.getSize(),
                    entity.getPrice(),
                    entity.getQuantityOnHand(),
                    entity.getCategoryId(),
                    entity.getSupplierId()
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public Product search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM product where id='" + id + "'");
            while (resultSet.next()){
                return new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    @Override
    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM product where id= ? ";
            return CrudUtil.execute(sql,id);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
