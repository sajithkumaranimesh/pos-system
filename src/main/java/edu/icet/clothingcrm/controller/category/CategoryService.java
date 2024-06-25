package edu.icet.clothingcrm.controller.category;

import edu.icet.clothingcrm.dto.Category;

import java.util.List;

public interface CategoryService {

    List<Category> loadCategory();

    Category searchCategory(String categoryId);

    boolean addCategory(Category category);

    boolean deleteCategory(String categoryId);
}
