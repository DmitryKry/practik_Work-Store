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
@since   2025/06/16 14:31:20
*/
@Entity (name="ru.inversion.catalog.PPositionsDim")
@Table (name="POSITIONS_DIM")
public class PPositionsDim implements Serializable
{
    private static final long serialVersionUID = 16_06_2025_14_31_20l;

    private Long POSITION_ID;
    private String POSITION_NAME;

    public PPositionsDim(){}

    @Id 
    @Column(name="POSITION_ID",nullable = false,length = 0)
    public Long getPOSITION_ID() {
        return POSITION_ID;
    }
    public void setPOSITION_ID(Long val) {
        POSITION_ID = val; 
    }
    @Column(name="POSITION_NAME",nullable = false,length = 50)
    public String getPOSITION_NAME() {
        return POSITION_NAME;
    }
    public void setPOSITION_NAME(String val) {
        POSITION_NAME = val; 
    }
}