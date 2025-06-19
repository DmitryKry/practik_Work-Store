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
@since   2025/06/19 12:17:03
*/
@Entity (name="ru.inversion.catalog.PStore")
@NamedNativeQuery (name="ru.inversion.catalog.PStore", query="select s.id_store, s.address, s.name_store, s.date_open_store, s.time_open_store, s.time_close_store, o.first_name_owner, o.second_name_owner, o.last_name_owner, o.phone, o.mail\n"
	+"from store_new_dim s\n"
	+"join owner_store_dim o on\n"
	+"o.id_owner=s.owner_store_id")
public class PStore implements Serializable
{
    private static final long serialVersionUID = 19_06_2025_12_17_03l;

    private Long ID_STORE;
    private String ADDRESS;
    private String NAME_STORE;
    private LocalDate DATE_OPEN_STORE;
    private LocalDateTime TIME_OPEN_STORE;
    private LocalDateTime TIME_CLOSE_STORE;
    private String FIRST_NAME_OWNER;
    private String SECOND_NAME_OWNER;
    private String LAST_NAME_OWNER;
    private Long PHONE;
    private String MAIL;

    public PStore(){}

    @Id 
    @Column(name="ID_STRE",nullable = false,length = 0)
    public Long getID_STORE() {
        return ID_STORE;
    }
    public void setID_STORE(Long val) {
        ID_STORE = val; 
    }
    @Column(name="ADDRESS",length = 100)
    public String getADDRESS() {
        return ADDRESS;
    }
    public void setADDRESS(String val) {
        ADDRESS = val; 
    }
    @Column(name="NAME_STORE",length = 100)
    public String getNAME_STORE() {
        return NAME_STORE;
    }
    public void setNAME_STORE(String val) {
        NAME_STORE = val; 
    }
    @Column(name="DATE_OPEN_STORE")
    public LocalDate getDATE_OPEN_STORE() {
        return DATE_OPEN_STORE;
    }
    public void setDATE_OPEN_STORE(LocalDate val) {
        DATE_OPEN_STORE = val; 
    }
    @Column(name="TIME_OPEN_STORE")
    public LocalDateTime getTIME_OPEN_STORE() {
        return TIME_OPEN_STORE;
    }
    public void setTIME_OPEN_STORE(LocalDateTime val) {
        TIME_OPEN_STORE = val; 
    }
    @Column(name="TIME_CLOSE_STORE")
    public LocalDateTime getTIME_CLOSE_STORE() {
        return TIME_CLOSE_STORE;
    }
    public void setTIME_CLOSE_STORE(LocalDateTime val) {
        TIME_CLOSE_STORE = val; 
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