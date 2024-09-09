package com.unimater.dao;

import com.unimater.model.Product;
import com.unimater.model.SaleItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleItemDAO extends GenericDAOImpl<SaleItem> implements GenericDAO<SaleItem> {

    private Connection connection;
    private final String TABLE_NAME = "sale_item";

    private final List<String> COLUMNS = List.of("product_id", "quantity", "percentual_discount", "sale_id");

    private ProductDAO productDAO;

    public SaleItemDAO(Connection connection) {
        super(SaleItem::new, connection);
        super.tableName = TABLE_NAME;
        super.columns = COLUMNS;
        this.connection = connection;
    }

    public SaleItem feedProduct(SaleItem saleItem){
        productDAO = new ProductDAO(connection);
        saleItem.setProduct(productDAO.getById(saleItem.getProduct().getId()));
        return saleItem;
    }

    public List<SaleItem> findBySaleId(int saleId){

            List<SaleItem> saleItems = new ArrayList<>();
            try {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE sale_id = ?");
                stmt.setInt(1, saleId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    SaleItem saleItem = new SaleItem(rs);
                    saleItems.add(saleItem);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return saleItems;
    }

}
