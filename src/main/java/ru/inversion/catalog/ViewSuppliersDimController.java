package ru.inversion.catalog;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
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
import ru.inversion.fx.form.action.ActionBuilder;
import ru.inversion.icons.IconDescriptorBuilder;
import ru.inversion.icons.enums.IonIcon;

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
    @FXML private BorderPane rootPane;
    private JInvButton customButton;
    private boolean exitInMenu;
    private static List<PSuppliersDim> supplersList;
    private Button createProductButton;
 
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
        exitInMenu = false;
        
        initDataSet ();
        DSFXAdapter<PSuppliersDim> dsfx = DSFXAdapter.bind (dsSUPPLIERS_DIM, SUPPLIERS_DIM, null, false); 

        dsfx.setEnableFilter (true);
                
        initToolBar ();

        SUPPLIERS_DIM.setToolBar (toolBar);       
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        //SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CHOOSE_DIRECTORY, (a) -> doOperation (FormModeEnum.VM_CHOICE));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
        doRefresh();
        
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
        Button customButton = new Button(getBundleString("MAIN"));
        customButton = (Button) ActionFactory.createButton(
                new ActionBuilder()
                .handler(this::openMenu)
                .title(getBundleString("MAIN"))
                .toolTipText(getBundleString("INFO.BUTTON_MENU"))
                .setKeyCombination(new KeyCodeCombination(KeyCode.F1))
                .build()
        );
        
        createProductButton = (Button) ActionFactory.createButton(
                new ActionBuilder()
                .handler(this::openCreateProduct)
                .title(getBundleString("MAIN"))
                .toolTipText(getBundleString("INFO.BUTTON_PRODUCT"))          
                .setKeyCombination(new KeyCodeCombination(KeyCode.F9))
                .icon(
                    new IconDescriptorBuilder().iconId(IonIcon.ion_document).build())
                .build()
        );
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
        toolBar.getItems().add(createProductButton);
        toolBar.getItems().add(customButton);
    }
   
    @FXML
    private void openCreateProduct(ActionEvent event){
        doOperation (FormModeEnum.VM_CHOICE);
    }
    
    @FXML
    private void openMenu(ActionEvent event){
        new FXFormLauncher<>(this, ViewStoreController.class)
                .initProperties(getInitProperties())
                .doModal();
            getViewContext().getStage().close();
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
        boolean cheakProduct = false;
        PProductDim prod = null;
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
                p = SUPPLIERS_DIM.getSelectionModel().getSelectedItem();
                break;
            case VM_SHOW:
            case VM_DEL:
                PSuppliersDim selectProduct = SUPPLIERS_DIM.getSelectionModel().getSelectedItem();
                boolean temp = Alerts.yesNo(SUPPLIERS_DIM.getSelectionModel().getSelectedItem(), "Удаление", "Удалить колонку " 
                        + selectProduct.getFIRST_NAME() + " " + selectProduct.getLAST_NAME() + " " + selectProduct.getPATRONYMIC() + "?");
                if (temp){
                    try {
                        new ParamMap()
                                .add("s_id", selectProduct.getID())
                                .exec(this, "deleteSuppliers");
                    } catch (SQLExpressionException ex) {
                        Logger.getLogger(ViewProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                    }      
                    doRefresh();
                }
                break;
            case VM_CHOICE:
                mode = FormModeEnum.VM_INS;
                p = SUPPLIERS_DIM.getSelectionModel().getSelectedItem();
                prod = new PProductDim();
                prod.setFIRST_NAME(p.getFIRST_NAME());
                prod.setLAST_NAME(p.getLAST_NAME());
                cheakProduct = true;
                new FXFormLauncher<> (this, EditProductDimController.class)
                .dataObject (prod)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .doModal ();
                break;
        }
        if (p != null && !cheakProduct) 
            new FXFormLauncher<> (this, EditSuppliersDimController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
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
                    doRefresh();
                    break;
                case VM_EDIT:                
                    dsSUPPLIERS_DIM.updateCurrentRow (dctl.getDataObject ());
                    doRefresh();
                    break;
                case VM_DEL:
                    dsSUPPLIERS_DIM.removeCurrentRow ();
                    doRefresh();
                    break;
                default:
                    break;
            }    
        }            

        SUPPLIERS_DIM.getSelectionModel().clearSelection();
        SUPPLIERS_DIM.requestFocus ();
    }        
    
//
//
//    
}

