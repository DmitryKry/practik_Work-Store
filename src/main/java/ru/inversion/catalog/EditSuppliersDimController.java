package ru.inversion.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;

/**
 * @author  admin
 * @since   Tue Jun 17 10:20:06 MSK 2025
 */
public class EditSuppliersDimController extends JInvFXFormController <PSuppliersDim> 
{  
//
//
//
//    @FXML JInvLongField ID;
    @FXML JInvTextField FIRST_NAME;
    @FXML JInvTextField LAST_NAME;
    @FXML JInvTextField PATRONYMIC;
    @FXML JInvTextField MAIL;
    @FXML JInvTextField PHONE;

//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception 
    {
        super.init (); 
    }    

    @FXML public void onOk() {
        if (dataObject != null){
            try {         
                new ParamMap()
                        .add("s_id", dataObject.getID())
                        .add("s_first_name", FIRST_NAME.getText())
                        .add("s_last_name", LAST_NAME.getText())
                        .add("s_patronymic", PATRONYMIC.getText())
                        .add("s_mail", MAIL.getText())
                        .add("s_phone", PHONE.getText())
                        .exec(this, "updateSuppliersNew");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }        
        }
        else {
            try {         
                new ParamMap()
                        .add("s_first_name", FIRST_NAME.getText())
                        .add("s_last_name", LAST_NAME.getText())
                        .add("s_patronymic", PATRONYMIC.getText())
                        .add("s_mail", MAIL.getText())
                        .add("s_phone", PHONE.getText())
                        .exec(this, "addSuppliersNew");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }
        Stage stage = (Stage) FIRST_NAME.getScene().getWindow();
        stage.close();
        forDorefresh.setSuppliersCheak(true);
    }
}

