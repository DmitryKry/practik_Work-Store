package ru.inversion.catalog;

import javafx.event.ActionEvent;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import ru.inversion.dataset.XXIDataSet;
import javafx.stage.Stage;
/**
 * @author  admin
 * @since   Mon Jun 16 14:39:40 MSK 2025
 */
public class EditProductDimController extends JInvFXFormController <PProductDim> 
{  
//
//
//
//    @FXML JInvLongField PRODUCT_ID;
    @FXML JInvTextField PRODUCT_NAME;
//    @FXML JInvLongField CATEGORY;
    @FXML JInvTextField PRICE;
    @FXML JInvTextField STOCK_QUANTITY;

    @FXML private ComboBox<String> productCategoryComboBox;
    @FXML private ComboBox<String> productSuppliersComboBox;
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
    
    @FXML public void onOK(ActionEvent event) {
        try {
            PSuppliersDim SupplersOnly = supplierses.stream()
                    .filter(s -> (s.getFIRST_NAME() + " " + s.getLAST_NAME())
                            .equals(productSuppliersComboBox.getSelectionModel().getSelectedItem()))
                    .findFirst().orElse(null);
            
            PCategoryDim categoryOnly = categores.stream()
                    .filter(c -> (c.getCATEGORY_NAME())
                            .equals(productCategoryComboBox.getSelectionModel().getSelectedItem()))
                    .findFirst().orElse(null);
            
            new ParamMap()
                    .add("p_name", PRODUCT_NAME.getText())
                    .add("p_category", categoryOnly.getCATEGORY_DIM_ID())
                    .add("p_price", PRICE.getText())
                    .add("p_stock_quantity", STOCK_QUANTITY.getText())
                    .add("p_supplier", SupplersOnly.getID())
                    .exec(this, "addNewProduct");
            Stage stage = (Stage) PRODUCT_NAME.getScene().getWindow();
            stage.close();
            
        } catch (SQLExpressionException ex) {
            Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            
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

