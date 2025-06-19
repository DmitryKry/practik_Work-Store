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
@since   2025/06/17 10:19:45
*/
@Entity (name="ru.inversion.catalog.PSuppliersDim")
@Table (name="SUPPLIERS_DIM")
public class PSuppliersDim implements Serializable
{
    private static final long serialVersionUID = 17_06_2025_10_19_45l;

    private Long ID;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String PATRONYMIC;
    private String MAIL;
    private Long PHONE;

    public PSuppliersDim(){}

    @Id 
    @Column(name="ID",nullable = false,length = 0)
    public Long getID() {
        return ID;
    }
    public void setID(Long val) {
        ID = val; 
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
    @Column(name="PATRONYMIC",length = 25)
    public String getPATRONYMIC() {
        return PATRONYMIC;
    }
    public void setPATRONYMIC(String val) {
        PATRONYMIC = val; 
    }
    @Column(name="MAIL",length = 25)
    public String getMAIL() {
        return MAIL;
    }
    public void setMAIL(String val) {
        MAIL = val; 
    }
    @Column(name="PHONE",length = 0)
    public Long getPHONE() {
        return PHONE;
    }
    public void setPHONE(Long val) {
        PHONE = val; 
    }
}