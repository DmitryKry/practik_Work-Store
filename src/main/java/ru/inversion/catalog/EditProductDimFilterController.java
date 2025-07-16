/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.inversion.catalog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.db.expr.SQLExpressionException;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.JInvComboBox;
import ru.inversion.fx.form.controls.JInvTextField;

/**
 *
 * @author admin
 */
public class EditProductDimFilterController extends JInvFXFormController <PProductDim>{
    @FXML private JInvComboBox productCategoryComboBox;
    @FXML private JInvComboBox productSuppliersComboBox;
    private boolean cheakBox;
    private List<PSuppliersDim> supplierses;
    private List<PCategoryDim> categores;
//
// Initializes the controller class.
//
     private final XXIDataSet<PSuppliersDim> dsSupplierSet = new XXIDataSet<> ();    
     private final XXIDataSet<PCategoryDim> dsPCategorySet = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsSupplierSet.setTaskContext (getTaskContext ());
        dsSupplierSet.setRowClass (PSuppliersDim.class);
        
        dsPCategorySet.setTaskContext (getTaskContext ());
        dsPCategorySet.setRowClass (PCategoryDim.class);
    }
    
    @Override
    public boolean onOK() {
        cheakBox = true;
        PSuppliersDim SupplersOnly = supplierses.stream()
                        .filter(s -> (s.getFIRST_NAME() + " " + s.getLAST_NAME())
                                .equals(productSuppliersComboBox.getSelectionModel().getSelectedItem()))
                        .findFirst().orElse(null);

        PCategoryDim categoryOnly = categores.stream()
                        .filter(c -> (c.getCATEGORY_NAME())
                                .equals(productCategoryComboBox.getSelectionModel().getSelectedItem()))
                        .findFirst().orElse(null);   
        dataObject.setCATEGORY_NAME(categoryOnly.getCATEGORY_NAME());
        dataObject.setFIRST_NAME(SupplersOnly.getFIRST_NAME());
        dataObject.setLAST_NAME(SupplersOnly.getLAST_NAME());
        this.getFXEntity().commit();
        return true;
    }
    
    private void SupplersBooksComboBox() {
        Set<String> uniqueSuppliers = new LinkedHashSet<>();
        List<PSuppliersDim> findOfUsers = new ArrayList<>();// LinkedHashSet сохраняет порядок
        for (PSuppliersDim supplier : dsSupplierSet.getRows()) 
            findOfUsers.add(supplier);
        productSuppliersComboBox.setEditable(true);
        productSuppliersComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!productSuppliersComboBox.getItems().contains(newVal)) {
                if (!uniqueSuppliers.isEmpty())
                    uniqueSuppliers.clear();
                int count = 0;
                for (PSuppliersDim suppliers : dsSupplierSet.getRows()) {
                    for (char item : newVal.toCharArray()) {
                        if (Character.toLowerCase(item) == Character.toLowerCase((suppliers.getFIRST_NAME() + " " + suppliers.getLAST_NAME()).charAt(count))) {
                            if (count < newVal.length()) {
                                count++;
                                if (count == (newVal.length())) {
                                    uniqueSuppliers.add(suppliers.getFIRST_NAME() + " " + suppliers.getLAST_NAME());
                                    count = 0;
                                }
                            }
                        }
                        else {
                            count = 0;
                            break;
                        }
                    }
                }       
                productSuppliersComboBox.getItems().clear();
                productSuppliersComboBox.getItems().setAll(uniqueSuppliers);
                productSuppliersComboBox.show();

                // Показываем выпадающий список при вводе
                if (newVal.isEmpty()) {
                    productSuppliersComboBox.getItems().clear();
                    uniqueSuppliers.clear();
                    for (PSuppliersDim supplierItem : findOfUsers){
                        uniqueSuppliers.add(supplierItem.getFIRST_NAME() + " " + supplierItem.getLAST_NAME());
                    }
                    productSuppliersComboBox.getItems().addAll(uniqueSuppliers);                                
                    productSuppliersComboBox.show();
                }
            }
        });
        // Устанавливаем первое значение по умолчанию, если список не пуст
        if (!productSuppliersComboBox.getItems().isEmpty()) {
            productSuppliersComboBox.getSelectionModel().selectFirst();
        }
    } 


    private void CategoryBooksComboBox() {
        Set<String> uniqueSuppliers = new LinkedHashSet<>();
        List<PCategoryDim> findOfUsers = new ArrayList<>();// LinkedHashSet сохраняет порядок
        for (PCategoryDim category : dsPCategorySet.getRows()) 
            findOfUsers.add(category);
        productCategoryComboBox.setEditable(true);
        productCategoryComboBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!productCategoryComboBox.getItems().contains(newVal)) {
                if (!uniqueSuppliers.isEmpty())
                    uniqueSuppliers.clear();
                int count = 0;
                for (PCategoryDim category : dsPCategorySet.getRows()) {
                    for (char item : newVal.toCharArray()) {
                        if (Character.toLowerCase(item) == Character.toLowerCase((category.getCATEGORY_NAME().charAt(count)))) {
                            if (count < newVal.length()) {
                                count++;
                                if (count == (newVal.length())) {
                                    uniqueSuppliers.add(category.getCATEGORY_NAME());
                                    count = 0;
                                }
                            }
                        }
                        else {
                            count = 0;
                            break;
                        }
                    }
                }       
                productCategoryComboBox.getItems().clear();
                productCategoryComboBox.getItems().setAll(uniqueSuppliers);
                productCategoryComboBox.show();

                // Показываем выпадающий список при вводе
                if (newVal.isEmpty()) {
                    productCategoryComboBox.getItems().clear();
                    uniqueSuppliers.clear();
                    for (PCategoryDim category : findOfUsers){
                        uniqueSuppliers.add(category.getCATEGORY_NAME());
                    }
                    productCategoryComboBox.getItems().addAll(uniqueSuppliers);                                
                    productCategoryComboBox.show();
                }
            }
        });

        // Устанавливаем первое значение по умолчанию, если список не пуст
        if (!productSuppliersComboBox.getItems().isEmpty()) {
            productSuppliersComboBox.getSelectionModel().selectFirst();
        }
    } 
    
    @Override
    protected void init () throws Exception 
    {
        initDataSet();
        dsSupplierSet.executeQuery();
        dsPCategorySet.executeQuery();
        productComboBox();
        categoryComboBox();
        SupplersBooksComboBox();
        CategoryBooksComboBox();
        productCategoryComboBox.setDisable(false);
        productSuppliersComboBox.setDisable(false);
        Platform.runLater(() -> {
            productCategoryComboBox.requestFocus();
        });
        super.init (); 
    }
    
    private void productComboBox(){
        productSuppliersComboBox.getItems().clear();
        Set<String> unigTetles = new LinkedHashSet<>();
        supplierses = new ArrayList<>();
        for (PSuppliersDim item : dsSupplierSet.getRows()){
            unigTetles.add(item.getFIRST_NAME() + " " + item.getLAST_NAME());
            supplierses.add(item);
        }
        productSuppliersComboBox.getItems().addAll(unigTetles);
        if (!productSuppliersComboBox.getItems().isEmpty())
            productSuppliersComboBox.getSelectionModel().selectFirst();
    }
    
    private void categoryComboBox(){
        productCategoryComboBox.getItems().clear();
        Set<String> unigTetles = new LinkedHashSet<>();
        categores = new ArrayList<>();
        for (PCategoryDim item : dsPCategorySet.getRows()){
            unigTetles.add(item.getCATEGORY_NAME());
            categores.add(item);
        }
        productCategoryComboBox.getItems().addAll(unigTetles);
        if (!productCategoryComboBox.getItems().isEmpty())
            productCategoryComboBox.getSelectionModel().selectFirst();
    }
}
