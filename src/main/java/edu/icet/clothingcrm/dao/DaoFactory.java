package edu.icet.clothingcrm.dao;

import edu.icet.clothingcrm.dao.custom.impl.*;
import edu.icet.clothingcrm.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        if (instance == null){
            return instance = new DaoFactory();
        }
        return instance;
    }

    public<T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case CATEGORY:return (T) new CategoryDaoImpl();
            case PRODUCT:return (T) new ProductDaoImpl();
            case EMPLOYEE:return (T) new EmployeeDaoImpl();
            case SUPPLIER:return (T) new SupplierDaoImpl();
            case ORDER:return (T) new OrderDaoImpl();
            case USER:return (T) new UserDaoImpl();
        }
        return null;
    }
}
