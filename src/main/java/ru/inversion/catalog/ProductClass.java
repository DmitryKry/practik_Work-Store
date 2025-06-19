package ru.inversion.catalog;

import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.io.Serializable;
import javax.persistence.*;
import ru.inversion.dataset.mark.*;
import ru.inversion.db.entity.ProxyFor;

/**
@author  admin
@since   2025/06/18 14:43:34
*/
@Entity (name="ru.inversion.catalog.ProductClass")
@NamedNativeQuery (name="ru.inversion.catalog.ProductClass", query="select p.product_id, p.product_name, p.price, p.stock_quantity, c.category_name, s.first_name, s.last_name, s.phone, s.mail from product_dim p\n"
	+"join category_dim c on p.category=c.category_dim_id\n"
	+"join suppliers_dim s on p.supplier=s.id")
public class ProductClass implements Serializable
{
    private static final long serialVersionUID = 18_06_2025_14_43_34l;

    private Long PRODUCT_ID;
    private String PRODUCT_NAME;
    private BigDecimal PRICE;
    private Long STOCK_QUANTITY;
    private String CATEGORY_NAME;
    private String FIRST_NAME;
    private String LAST_NAME;
    private Long PHONE;
    private String MAIL;

    public ProductClass(){}

    @Id 
    @Column(name="PRODUCT_ID",nullable = false,length = 0)
    public Long getPRODUCT_ID() {
        return PRODUCT_ID;
    }
    public void setPRODUCT_ID(Long val) {
        PRODUCT_ID = val; 
    }
    @Column(name="PRODUCT_NAME",nullable = false,length = 100)
    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }
    public void setPRODUCT_NAME(String val) {
        PRODUCT_NAME = val; 
    }
    @Column(name="PRICE",nullable = false,length = 10)
    public BigDecimal getPRICE() {
        return PRICE;
    }
    public void setPRICE(BigDecimal val) {
        PRICE = val; 
    }
    @Column(name="STOCK_QUANTITY",length = 0)
    public Long getSTOCK_QUANTITY() {
        return STOCK_QUANTITY;
    }
    public void setSTOCK_QUANTITY(Long val) {
        STOCK_QUANTITY = val; 
    }
    @Column(name="CATEGORY_NAME",nullable = false,length = 100)
    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }
    public void setCATEGORY_NAME(String val) {
        CATEGORY_NAME = val; 
    }
    @Column(name="FIRST_NAME",length = 25)
    public String getFIRST_NAME() {
        return FIRST_NAME;
    }
    public void setFIRST_NAME(String val) {
        FIRST_NAME = val; 
    }
    @Column(name="LAST_NAME",length = 25)
    public String getLAST_NAME() {
        return LAST_NAME;
    }
    public void setLAST_NAME(String val) {
        LAST_NAME = val; 
    }
    @Column(name="PHONE",length = 0)
    public Long getPHONE() {
        return PHONE;
    }
    public void setPHONE(Long val) {
        PHONE = val; 
    }
    @Column(name="MAIL",length = 25)
    public String getMAIL() {
        return MAIL;
    }
    public void setMAIL(String val) {
        MAIL = val; 
    }
}