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
@since   2025/06/16 14:56:17
*/
@Entity (name="ru.inversion.catalog.PEmployeesDimIs122")
@Table (name="EMPLOYEES_DIM_IS122")
public class PEmployeesDimIs122 implements Serializable
{
    private static final long serialVersionUID = 16_06_2025_14_56_17l;

    private Long EMPLOYEES_ID;
    private String FULL_NAME;
    private String POSITION_ID;
    private LocalDate HIRE_DATE;
    private BigDecimal SALARY;

    public PEmployeesDimIs122(){}

    @Id 
    @Column(name="EMPLOYEES_ID",nullable = false,length = 0)
    public Long getEMPLOYEES_ID() {
        return EMPLOYEES_ID;
    }
    public void setEMPLOYEES_ID(Long val) {
        EMPLOYEES_ID = val; 
    }
    @Column(name="FULL_NAME",nullable = false,length = 100)
    public String getFULL_NAME() {
        return FULL_NAME;
    }
    public void setFULL_NAME(String val) {
        FULL_NAME = val; 
    }
    @Column(name="POSITION_ID",nullable = false,length = 100)
    public String getPOSITION_ID() {
        return POSITION_ID;
    }
    public void setPOSITION_ID(String val) {
        POSITION_ID = val; 
    }
    @Column(name="HIRE_DATE")
    public LocalDate getHIRE_DATE() {
        return HIRE_DATE;
    }
    public void setHIRE_DATE(LocalDate val) {
        HIRE_DATE = val; 
    }
    @Column(name="SALARY",nullable = false,length = 10)
    public BigDecimal getSALARY() {
        return SALARY;
    }
    public void setSALARY(BigDecimal val) {
        SALARY = val; 
    }
}