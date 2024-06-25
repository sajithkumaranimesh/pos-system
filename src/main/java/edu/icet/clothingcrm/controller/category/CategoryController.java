package edu.icet.clothingcrm.controller.category;

import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.dto.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController implements CategoryService{


    private static CategoryController instance;

    private CategoryController(){}

    public static CategoryController getInstance(){
        if (instance == null){
            return instance = new CategoryController();
        }
        return instance;
    }

    private List<Category> categoryList;

    public List<Category> loadCategory() {
        categoryList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM category");
            while (resultSet.next()){
                Category category = new Category(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );
                System.out.println(category);
                categoryList.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

    public Category searchCategory(String categoryId){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM category where id='" + categoryId + "'");
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

    public boolean addCategory(Category category){
        System.out.println(category);

        try {
            String sql = "INSERT INTO category VALUES (?,?)";
            CrudUtil.execute(
                    sql,
                    category.getId(),
                    category.getName()
                    );


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteCategory(String categoryId){
        try {
            String query = "DELETE FROM category where id= ? ";
            return CrudUtil.execute(query,categoryId);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
