package ru.inversion.catalog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.db.expr.SQLExpressionException;

/**
 * @author  admin
 * @since   Thu Jun 19 12:17:09 MSK 2025
 */
public class EditStoreController extends JInvFXFormController <PStore> 
{  
//
//
//
//    @FXML JInvLongField ID_STORE;
//    @FXML JInvTextField ADDRESS;
//    @FXML JInvTextField NAME_STORE;
//    @FXML JInvTextField DATE_OPEN_STORE;
//    @FXML JInvTextField TIME_OPEN_STORE;
//    @FXML JInvTextField TIME_CLOSE_STORE;
//    @FXML JInvTextField FIRST_NAME_OWNER;
//    @FXML JInvTextField SECOND_NAME_OWNER;
//    @FXML JInvTextField LAST_NAME_OWNER;
//    @FXML JInvLongField PHONE;
//    @FXML JInvTextField MAIL;
    @FXML JInvComboBox ownersBox;

    private List<POwner_Store> owners;
//
// Initializes the controller class.
//
    private final XXIDataSet<POwner_Store> dsOwnerSet = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsOwnerSet.setTaskContext (getTaskContext ());
        dsOwnerSet.setRowClass (POwner_Store.class);
        
    }
    
    @FXML public void onOk() {
    }
    @Override
    protected void init () throws Exception 
    {
        initDataSet();
        owners = new ArrayList<>();
        dsOwnerSet.executeQuery();
        productComboBox();
        super.init (); 
    }    

    private void productComboBox(){
        ownersBox.getItems().clear();
        Set<String> unigTetles = new LinkedHashSet<>();
        owners = new ArrayList<>();
        for (POwner_Store item : dsOwnerSet.getRows()){
            unigTetles.add(item.getFIRST_NAME_OWNER() + " " + item.getLAST_NAME_OWNER() + " " + item.getMAIL());
            owners.add(item);
        }
        ownersBox.getItems().addAll(unigTetles);
        if (!ownersBox.getItems().isEmpty())
            ownersBox.getSelectionModel().selectFirst();
    }
}

