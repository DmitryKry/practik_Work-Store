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
@since   2025/06/16 14:31:43
*/
@Entity (name="ru.inversion.catalog.PEmployeesDim")
@Table (name="EMPLOYEES_DIM")
public class PEmployeesDim implements Serializable
{
    private static final long serialVersionUID = 16_06_2025_14_31_43l;

    private Long EMPLOYEE_ID;
    private String FULL_NAME;
    private LocalDate HIRE_DATE;
    private BigDecimal SALARY;
    private Long POSITION_ID_DIM;
    private Long STORE_ID_DIM;

    public PEmployeesDim(){}

    @Id 
    @Column(name="EMPLOYEE_ID",nullable = false,length = 0)
    public Long getEMPLOYEE_ID() {
        return EMPLOYEE_ID;
    }
    public void setEMPLOYEE_ID(Long val) {
        EMPLOYEE_ID = val; 
    }
    @Column(name="FULL_NAME",nullable = false,length = 100)
    public String getFULL_NAME() {
        return FULL_NAME;
    }
    public void setFULL_NAME(String val) {
        FULL_NAME = val; 
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
    @Column(name="POSITION_ID_DIM",nullable = false,length = 0)
    public Long getPOSITION_ID_DIM() {
        return POSITION_ID_DIM;
    }
    public void setPOSITION_ID_DIM(Long val) {
        POSITION_ID_DIM = val; 
    }
    @Column(name="STORE_ID_DIM",nullable = false,length = 0)
    public Long getSTORE_ID_DIM() {
        return STORE_ID_DIM;
    }
    public void setSTORE_ID_DIM(Long val) {
        STORE_ID_DIM = val; 
    }
}