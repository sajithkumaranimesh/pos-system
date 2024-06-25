package edu.icet.clothingcrm.bo.custom;

import edu.icet.clothingcrm.bo.SuperBo;
import edu.icet.clothingcrm.dto.Category;

public interface CategoryBo extends SuperBo {
    boolean saveCategory(Category dto);
    boolean deleteCategoryById(String categoryId);
    Category searchCategoryById(String categoryId);
}
