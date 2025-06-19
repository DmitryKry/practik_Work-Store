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
@since   2025/06/16 14:30:25
*/
@Entity (name="ru.inversion.catalog.PCategoryDim")
@Table (name="CATEGORY_DIM")
public class PCategoryDim implements Serializable
{
    private static final long serialVersionUID = 16_06_2025_14_30_25l;

    private Long CATEGORY_DIM_ID;
    private String CATEGORY_NAME;

    public PCategoryDim(){}

    @Id 
    @Column(name="CATEGORY_DIM_ID",nullable = false,length = 0)
    public Long getCATEGORY_DIM_ID() {
        return CATEGORY_DIM_ID;
    }
    public void setCATEGORY_DIM_ID(Long val) {
        CATEGORY_DIM_ID = val; 
    }
    @Column(name="CATEGORY_NAME",nullable = false,length = 100)
    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }
    public void setCATEGORY_NAME(String val) {
        CATEGORY_NAME = val; 
    }
}