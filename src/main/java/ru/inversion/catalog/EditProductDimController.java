package ru.inversion.catalog;

import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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
//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception 
    {
        productCategoryComboBox.getItems().addAll(
                "Молочные продукты",
                "Хлебобулочные изделия",
                "Мясные продукты",
                "Напитки",
                "Фрукты и овощи",
                "Замороженные продукты",
                "Кондитерские изделия",
                "Бакалея",
                "Замороженные продукты",
                "Полуфабрикаты"
        );
        super.init (); 
    }    

}

