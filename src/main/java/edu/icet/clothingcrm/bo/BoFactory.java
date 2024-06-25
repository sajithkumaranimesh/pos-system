package edu.icet.clothingcrm.bo;

import edu.icet.clothingcrm.bo.custom.impl.*;
import edu.icet.clothingcrm.util.BoType;

public class BoFactory {
    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        if (instance == null){
            return instance = new BoFactory();
        }
        return instance;
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CATEGORY:return (T) new CategoryBoImpl();
            case PRODUCT:return (T) new ProductBoImpl();
            case EMPLOYEE:return (T) new EmployeeBoImpl();
            case SUPPLIER:return (T) new SupplierBoImpl();
            case ORDER:return (T) new OrderBoImpl();
            case USER:return (T) new UserBoImpl();
        }
        return null;
    }
}
