/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.catalog;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.codec.language.bm.Rule;

/**
 *
 * @author admin
 */
@Entity (name="ru.inversion.catalog.POwner_Store")
@Table (name="owner_store_dim")
public class POwner_Store {
    private long ID_OWNER;
    private String ADRES_TO_HOME;
    private LocalDate DATE_OF_BORN;
    private String FIRST_NAME_OWNER;
    private String SECOND_NAME_OWNER;
    private String LAST_NAME_OWNER;
    private long PHONE;
    private String MAIL; 

    public void setID_OWNER(long ID_OWNER) {
        this.ID_OWNER = ID_OWNER;
    }

    public void setADRES_TO_HOME(String ADRES_TO_HOME) {
        this.ADRES_TO_HOME = ADRES_TO_HOME;
    }

    public void setDATE_OF_BORN(LocalDate DATE_OF_BORN) {
        this.DATE_OF_BORN = DATE_OF_BORN;
    }

    public void setFIRST_NAME_OWNER(String FIRST_NAME_OWNER) {
        this.FIRST_NAME_OWNER = FIRST_NAME_OWNER;
    }

    public void setSECOND_NAME_OWNER(String SECOND_NAME_OWNER) {
        this.SECOND_NAME_OWNER = SECOND_NAME_OWNER;
    }

    public void setLAST_NAME_OWNER(String LAST_NAME_OWNER) {
        this.LAST_NAME_OWNER = LAST_NAME_OWNER;
    }

    public void setPHONE(long PHONE) {
        this.PHONE = PHONE;
    }

    public void setMAIL(String MAIL) {
        this.MAIL = MAIL;
    }
    
    @Id 
    @Column(name="ID_OWNER",nullable = false,length = 0)
    public long getID_OWNER() {
        return ID_OWNER;
    }
    @Column(name="ADRES_TO_HOME",length = 100)
    public String getADRES_TO_HOME() {
        return ADRES_TO_HOME;
    }
    
    @Column(name="DATE_OF_BORN")
    public LocalDate getDATE_OF_BORN() {
        return DATE_OF_BORN;
    }

    @Column(name="FIRST_NAME_OWNER",length = 25)
    public String getFIRST_NAME_OWNER() {
        return FIRST_NAME_OWNER;
    }
    
    @Column(name="SECOND_NAME_OWNER",length = 25)
    public String getSECOND_NAME_OWNER() {
        return SECOND_NAME_OWNER;
    }
    
    @Column(name="LAST_NAME_OWNER",length = 25)
    public String getLAST_NAME_OWNER() {
        return LAST_NAME_OWNER;
    }

    @Column(name="PHONE",length = 0)
    public long getPHONE() {
        return PHONE;
    }

    @Column(name="MAIL",length = 35)
    public String getMAIL() {
        return MAIL;
    }
   
}
