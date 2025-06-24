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
@since   2025/06/24 11:59:35
*/
@Entity (name="ru.inversion.catalog.POwnerStore")
@Table (name="OWNER_STORE_DIM")
public class POwnerStore implements Serializable
{
    private static final long serialVersionUID = 24_06_2025_11_59_35l;

    private Long ID_OWNER;
    private String ADDRESS_TO_HOME;
    private LocalDate DATE_OF_BORN;
    private String FIRST_NAME_OWNER;
    private String SECOND_NAME_OWNER;
    private String LAST_NAME_OWNER;
    private Long PHONE;
    private String MAIL;

    public POwnerStore(){}

    @Id 
    @Column(name="ID_OWNER",nullable = false,length = 0)
    public Long getID_OWNER() {
        return ID_OWNER;
    }
    public void setID_OWNER(Long val) {
        ID_OWNER = val; 
    }
    @Column(name="ADDRESS_TO_HOME",length = 100)
    public String getADDRESS_TO_HOME() {
        return ADDRESS_TO_HOME;
    }
    public void setADDRESS_TO_HOME(String val) {
        ADDRESS_TO_HOME = val; 
    }
    @Column(name="DATE_OF_BORN")
    public LocalDate getDATE_OF_BORN() {
        return DATE_OF_BORN;
    }
    public void setDATE_OF_BORN(LocalDate val) {
        DATE_OF_BORN = val; 
    }
    @Column(name="FIRST_NAME_OWNER",length = 25)
    public String getFIRST_NAME_OWNER() {
        return FIRST_NAME_OWNER;
    }
    public void setFIRST_NAME_OWNER(String val) {
        FIRST_NAME_OWNER = val; 
    }
    @Column(name="SECOND_NAME_OWNER",length = 25)
    public String getSECOND_NAME_OWNER() {
        return SECOND_NAME_OWNER;
    }
    public void setSECOND_NAME_OWNER(String val) {
        SECOND_NAME_OWNER = val; 
    }
    @Column(name="LAST_NAME_OWNER",length = 25)
    public String getLAST_NAME_OWNER() {
        return LAST_NAME_OWNER;
    }
    public void setLAST_NAME_OWNER(String val) {
        LAST_NAME_OWNER = val; 
    }
    @Column(name="PHONE",length = 11)
    public Long getPHONE() {
        return PHONE;
    }
    public void setPHONE(Long val) {
        PHONE = val; 
    }
    @Column(name="MAIL",length = 35)
    public String getMAIL() {
        return MAIL;
    }
    public void setMAIL(String val) {
        MAIL = val; 
    }
}