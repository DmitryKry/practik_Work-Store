package ru.inversion.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.inversion.fx.form.JInvFXFormController;
import ru.inversion.fx.form.controls.*;
import javafx.fxml.FXML;
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
        owners = new ArrayList<>();
        super.init (); 
    }    

}

