package ru.inversion.catalog;

import com.itextpdf.text.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.*;
import ru.inversion.fx.form.valid.Validator;

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
    private JInvTextField[] evereField;
    private String[] nameFields;
    private static final String EMAIL_REGEX = 
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern pattern_EMAIL = Pattern.compile(EMAIL_REGEX);
    private static final String PHONE_REGEX = 
            "^(\\+7|8)[-\\s]?\\(?[0-9]{3}\\)?[-\\s]?[0-9]{3}[-\\s]?[0-9]{2}[-\\s]?[0-9]{2}$";;
    private static final Pattern pattern_PHONE = Pattern.compile(PHONE_REGEX);
    String phoneNumber = "";
            
//
// Initializes the controller class.
//
    @Override
    protected void init () throws Exception 
    {
        evereField = new JInvTextField[]{FIRST_NAME, LAST_NAME, PATRONYMIC, MAIL, PHONE};
        nameFields = new String[]{"FIRST_NAME", "LAST_NAME", "PATRONYMIC", "MAIL", "PHONE"};
        getValidMan().bindValidators2Control(FIRST_NAME,(value)-> {
            if (value==null)
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.ERROR"), value));
            return null;
        });
        getValidMan().bindValidators2Control(LAST_NAME,(value)-> {
            if (value==null)
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.ERROR"), value));
            return null;
        });
        
        getValidMan().bindValidators2Control(PATRONYMIC,(value)-> {
            if (value==null)
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.ERROR"), value));
            return null;
        });
        getValidMan().bindValidators2Control(PHONE,(value)-> {
            if (value==null)
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.ERROR"), value));  
            if (!isValidEmail((String) value, pattern_PHONE)){
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.PHONE"), value));  
                }
            return null;
        });
        getValidMan().bindValidators2Control(MAIL,(value)-> {
            if (value==null)
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.ERROR"), value));
            if (!isValidEmail((String) value, pattern_EMAIL)){
                return new Validator.Result(value,String.format(bundle.getString("VALIDATOR.EMAIL"), value));
            }
            return null;
        });
        super.init (); 
    }    
    
    private static boolean isValidEmail(String element, Pattern anotherPattern) {
        if (element == null) {
            return false;
        }
        Matcher matcher = anotherPattern.matcher(element);
        return matcher.matches();
    }
    
    @Override
    public boolean onOK() {
        char[] arrayNumber = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String tempPhone = PHONE.getText();
        for (int j = 0; j < tempPhone.length(); j++) {
            if (j == 0){
                if (tempPhone.charAt(j) == '+'){
                    phoneNumber += '8';
                    j++;
                    continue;
                }
            }
            for (char item : arrayNumber){
                if (item == tempPhone.charAt(j)){
                    phoneNumber += tempPhone.charAt(j);
                }
            }
        }
        
        if (dataObject.getFIRST_NAME() != null){
            try {         
                new ParamMap()
                        .add("s_id", dataObject.getID())
                        .add("s_first_name", FIRST_NAME.getText())
                        .add("s_last_name", LAST_NAME.getText())
                        .add("s_patronymic", PATRONYMIC.getText())
                        .add("s_mail", MAIL.getText())
                        .add("s_phone", phoneNumber)
                        .exec(this, "updateSuppliersNew");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
                return false;
            }        
        }
        else {
            try {         
                new ParamMap()
                        .add("s_first_name", FIRST_NAME.getText())
                        .add("s_last_name", LAST_NAME.getText())
                        .add("s_patronymic", PATRONYMIC.getText())
                        .add("s_mail", MAIL.getText())
                        .add("s_phone", phoneNumber)
                        .exec(this, "addSuppliersNew");
            } catch (SQLExpressionException ex) {
                Logger.getLogger(EditProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
                return false;
            }
        }
        this.getFXEntity().commit();
        return true;
    }
}

