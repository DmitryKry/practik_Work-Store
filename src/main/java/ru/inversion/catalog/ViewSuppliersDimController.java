package ru.inversion.catalog;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.inversion.dataset.IDataSet;
import ru.inversion.dataset.XXIDataSet;
import ru.inversion.dataset.fx.DSFXAdapter;
import ru.inversion.dataset.aggr.AggrFuncEnum;
import ru.inversion.fx.form.controls.dsbar.DSInfoBar;
import ru.inversion.fx.form.controls.table.toolbar.AggregatorType;
import ru.inversion.meta.EntityMetadataFactory;
import ru.inversion.meta.IEntityProperty;

import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;

import ru.inversion.bicomp.action.JInvButtonPrint;
import ru.inversion.bicomp.action.StopExecuteActionBiCompException;
import ru.inversion.bicomp.fxreport.ApReport;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;

/**
 *
 * @author  admin
 * @since   Tue Jun 17 10:20:04 MSK 2025
 */
public class ViewSuppliersDimController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PSuppliersDim> SUPPLIERS_DIM;   
    @FXML private JInvToolBar toolBar;
    @FXML private TextField EMAILField;
    @FXML private TextField PhoneField;
    private static List<PSuppliersDim> supplersList;
 
    public List<PSuppliersDim> getSupllers(){
        for(PSuppliersDim item : SUPPLIERS_DIM.getItems()){
            supplersList.add(item);
        }
        return supplersList;
    }
    
   
    private final XXIDataSet<PSuppliersDim> dsSUPPLIERS_DIM = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsSUPPLIERS_DIM.setTaskContext (getTaskContext ());
        dsSUPPLIERS_DIM.setRowClass (PSuppliersDim.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PSuppliersDim> dsfx = DSFXAdapter.bind (dsSUPPLIERS_DIM, SUPPLIERS_DIM, null, false); 

        dsfx.setEnableFilter (true);
                
        initToolBar ();

        SUPPLIERS_DIM.setToolBar (toolBar);       
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
        SUPPLIERS_DIM.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });
        doRefresh();
    }
    
    private void updateTextFields(PSuppliersDim selectSuppliers){
        if (selectSuppliers != null){
            EMAILField.setText(String.valueOf(selectSuppliers.getMAIL()));
            PhoneField.setText(String.valueOf(selectSuppliers.getPHONE()));
            EMAILField.setEditable(false);
            PhoneField.setEditable(false);            
        } else {
            EMAILField.clear();
            PhoneField.clear();
        }
    }
    
//
// doRefresh
//    
    private void doRefresh () 
    {
        SUPPLIERS_DIM.executeQuery ();
    }
//
// initToolBar
//    
    private void initToolBar () 
    {
        JInvButtonPrint bp = new JInvButtonPrint (this::setPrintParam);        
        bp.setReportTypeId (200000);
        toolBar.getItems ().add (bp);

        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.CREATE_BY, 
                                    ActionFactory.ActionTypeEnum.VIEW,
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
    }
//
// setPrintParam
//
    private void setPrintParam ( ApReport ap ) 
    {
        if (dsSUPPLIERS_DIM.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PSuppliersDim p = null;

        switch (mode) {
            case VM_INS:
                p = new PSuppliersDim ();
                break;
            case VM_NONE:
                if (dsSUPPLIERS_DIM.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PSuppliersDim ();
                for (IEntityProperty<PSuppliersDim, ?> ep : EntityMetadataFactory.getEntityMetaData (PSuppliersDim.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsSUPPLIERS_DIM.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                dsSUPPLIERS_DIM.getCurrentRow ();
                PSuppliersDim selectProduct = SUPPLIERS_DIM.getSelectionModel().getSelectedItem();
                try {
                    new ParamMap()
                            .add("s_id", selectProduct.getID())
                            .exec(this, "deleteSuppliers");
                } catch (SQLExpressionException ex) {
                    Logger.getLogger(ViewProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                } 
                          
                                             
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditSuppliersDimController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
        doRefresh();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PSuppliersDim> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsSUPPLIERS_DIM.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsSUPPLIERS_DIM.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsSUPPLIERS_DIM.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    

        SUPPLIERS_DIM.requestFocus ();
    }        
    
    @FXML
    private void load_suppliers(ActionEvent event){
        new FXFormLauncher<>(this, ViewSuppliersDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
    
    @FXML
    private void load_product(ActionEvent event){
        new FXFormLauncher<>(this, ViewProductDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
    
    @FXML
    private void load_category(ActionEvent event){
        new FXFormLauncher<>(this, ViewCategoryDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
   
    @FXML
    private void load_professions(ActionEvent event){
        new FXFormLauncher<>(this, ViewPositionsDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
    
    @FXML
    private void load_store(ActionEvent event){
        new FXFormLauncher<>(this, ViewStoreController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }   
//
//
//    
}

