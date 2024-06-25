package edu.icet.clothingcrm.controller.supplier;

import edu.icet.clothingcrm.util.CrudUtil;
import edu.icet.clothingcrm.dto.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController implements SupplierService{

    private static SupplierController instance;

    private SupplierController(){}

    public static SupplierController getInstance(){
        if (instance == null){
            return instance = new SupplierController();
        }
        return instance;
    }

    private List<Supplier> supplierList;

    public List<Supplier> loadSupplier() {
        supplierList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");
            while (resultSet.next()){
                Supplier supplier = new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                System.out.println(supplier);
                supplierList.add(supplier);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return supplierList;
    }

    public Supplier searchSupplier(String supplierId){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier where id='" + supplierId + "'");
            while (resultSet.next()){
                return new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(3)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean addSupplier(Supplier supplier){

        try {
            String sql = "INSERT INTO supplier VALUES (?,?,?,?)";
            CrudUtil.execute(
                    sql,
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getEmail(),
                    supplier.getCompany()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteSupplier(String supplierId){
        try {
            String sql = "DELETE FROM supplier where id= ?";
            return CrudUtil.execute(sql,supplierId);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
