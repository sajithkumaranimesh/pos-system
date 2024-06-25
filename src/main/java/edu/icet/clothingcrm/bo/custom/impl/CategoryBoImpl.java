package edu.icet.clothingcrm.bo.custom.impl;

import edu.icet.clothingcrm.bo.custom.CategoryBo;
import edu.icet.clothingcrm.dao.DaoFactory;
import edu.icet.clothingcrm.dao.custom.CategoryDao;
import edu.icet.clothingcrm.dto.Category;
import edu.icet.clothingcrm.entity.CategoryEntity;
import edu.icet.clothingcrm.util.DaoType;
import org.modelmapper.ModelMapper;

public class CategoryBoImpl implements CategoryBo {

    private CategoryDao categoryDao = DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public boolean saveCategory(Category dto) {
        return categoryDao.save(new ModelMapper().map(dto, CategoryEntity.class));
    }

    @Override
    public boolean deleteCategoryById(String categoryId) {
        return categoryDao.delete(categoryId);
    }

    @Override
    public Category searchCategoryById(String categoryId) {
        return categoryDao.search(categoryId);
    }
}
