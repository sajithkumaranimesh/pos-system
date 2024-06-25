package edu.icet.clothingcrm.dao.custom.impl;

import edu.icet.clothingcrm.dao.custom.CategoryDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.entity.CategoryEntity;
import edu.icet.clothingcrm.util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public boolean save(CategoryEntity entity) {
//        Session session = HibernateUtil.getSession();
//        session.getTransaction().begin();
//        session.persist(entity);
//        session.getTransaction().commit();
//        session.close();
//        return true;
        try {
            String sql = "INSERT INTO category VALUES (?,?)";
            CrudUtil.execute(
                    sql,
                    entity.getId(),
                    entity.getName()
            );


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            String query = "DELETE FROM category where id= ? ";
            return CrudUtil.execute(query, id);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM category where id='" + id + "'");
            while (resultSet.next()){
                return new Category(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
