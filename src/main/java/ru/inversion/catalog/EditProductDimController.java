package ru.inversion.catalog;

import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import java.util.List;
import java.util.ArrayList;
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
//    @FXML JInvTextField PRODUCT_NAME;
//    @FXML JInvLongField CATEGORY;
//    @FXML JInvTextField PRICE;
//    @FXML JInvLongField STOCK_QUANTITY;

     @FXML private ComboBox<String> productCategoryComboBox;
     @FXML private ComboBox<String> productSuppliersComboBox;
     @FXML private ComboBox<String> simpleBox;
//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception 
    {
        super.init (); 
        
        simpleBox.getItems().addAll("привет", "Пока");
        simpleBox.getSelectionModel().selectFirst();
    }    
    
}

