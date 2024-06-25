package edu.icet.clothingcrm.dao.custom;

import edu.icet.clothingcrm.dao.CrudDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.entity.CategoryEntity;

public interface CategoryDao extends CrudDao<CategoryEntity> {
    Category search(String id);
}
