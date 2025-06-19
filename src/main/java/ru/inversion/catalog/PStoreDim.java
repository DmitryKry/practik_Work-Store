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
@since   2025/06/16 14:30:45
*/
@Entity (name="ru.inversion.catalog.PStoreDim")
@Table (name="STORE_DIM")
public class PStoreDim implements Serializable
{
    private static final long serialVersionUID = 16_06_2025_14_30_45l;

    private Long STORE_ID;
    private String NAME;
    private String ADDRESS;

    public PStoreDim(){}

    @Id 
    @Column(name="STORE_ID",nullable = false,length = 0)
    public Long getSTORE_ID() {
        return STORE_ID;
    }
    public void setSTORE_ID(Long val) {
        STORE_ID = val; 
    }
    @Column(name="NAME",length = 100)
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String val) {
        NAME = val; 
    }
    @Column(name="ADDRESS",length = 200)
    public String getADDRESS() {
        return ADDRESS;
    }
    public void setADDRESS(String val) {
        ADDRESS = val; 
    }
}