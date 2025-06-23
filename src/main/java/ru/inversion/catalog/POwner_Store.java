/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.catalog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity (name="ru.inversion.catalog.POwner_Store")
@Table (name="owner_store_dim")
public class POwner_Store {
    private long ID_OWNER;
    private String ADRES_TO_HOME;
    
    @Id 
    @Column(name="ID_OWNER",nullable = false,length = 0)
    public long getID_OWNER() {
        return ID_OWNER;
    }

    public String getADRES_TO_HOME() {
        return ADRES_TO_HOME;
    }
    
   
}
