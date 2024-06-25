package edu.icet.clothingcrm.dao;


public interface CrudDao <T> extends SuperDao{
    boolean save(T dto);
    boolean delete(String id);
}
