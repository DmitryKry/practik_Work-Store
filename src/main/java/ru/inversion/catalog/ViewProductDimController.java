package ru.inversion.catalog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.sql.*;
import javafx.scene.control.TextField;
import ru.inversion.bicomp.action.JInvButtonPrint;
import ru.inversion.bicomp.action.StopExecuteActionBiCompException;
import ru.inversion.bicomp.fxreport.ApReport;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import ru.inversion.dataset.DataSetException;
/**
 *
 * @author  admin
 * @since   Mon Jun 16 14:39:38 MSK 2025
 */
public class ViewProductDimController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PProductDim> PRODUCT_DIM;   
    @FXML private JInvToolBar toolBar;
    @FXML private TextField categoryField;
    @FXML private TextField firstNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private BorderPane rootPane;
    private ComboBox<String> filterForCategoryBox;
    private List<PProductDim> products;
    private JInvFXFormController.FormModeEnum mainModeEnum;
 
   
    private final XXIDataSet<PProductDim> dsPRODUCT_DIM = new XXIDataSet<> ();    
    private final XXIDataSet<PSuppliersDim> dsSupplierSet = new XXIDataSet<> ();    
    private final XXIDataSet<PCategoryDim> dsPCategorySet = new XXIDataSet<> ();   
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsPRODUCT_DIM.setTaskContext (getTaskContext ());
        dsPRODUCT_DIM.setRowClass (PProductDim.class);
        
        dsSupplierSet.setTaskContext (getTaskContext ());
        dsSupplierSet.setRowClass (PSuppliersDim.class);
        
        dsPCategorySet.setTaskContext (getTaskContext ());
        dsPCategorySet.setRowClass (PCategoryDim.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        initDataSet ();
        dsSupplierSet.executeQuery();
        dsPCategorySet.executeQuery();
        dsPRODUCT_DIM.executeQuery();
        DSFXAdapter<PProductDim> dsfx = DSFXAdapter.bind (dsPRODUCT_DIM, PRODUCT_DIM, null, false); 
        
        dsfx.setEnableFilter (true);
        initToolBar ();

        PRODUCT_DIM.setToolBar (toolBar);       
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.FILTER, (a) -> doOperation (FormModeEnum.VM_SHOW));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
        PRODUCT_DIM.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });
        doRefresh();
        
    }
    
    private void updateTextFields(PProductDim selectSuppliers){
        if (selectSuppliers != null){
            categoryField.setText(String.valueOf(selectSuppliers.getCATEGORY_NAME()));
            categoryField.setEditable(false);
            
            firstNameField.setText(String.valueOf(selectSuppliers.getFIRST_NAME()) 
                    + " " + String.valueOf(selectSuppliers.getLAST_NAME()));
            firstNameField.setEditable(false);
            
            emailField.setText(String.valueOf(selectSuppliers.getMAIL()));
            emailField.setEditable(false);
            
            phoneField.setText(String.valueOf(selectSuppliers.getPHONE()));
            phoneField.setEditable(false);            
        } else {
            categoryField.clear();
            phoneField.clear();
            firstNameField.clear();
            emailField.clear();
        }
    }
// doRefresh
//    
    private void doRefresh () 
    {
        PRODUCT_DIM.executeQuery ();
    }
//
// initToolBar
//    
    private void initToolBar () 
    {
        Button customButton = new Button("В меню");
        customButton.setOnAction(e -> {
            new FXFormLauncher<>(this, ViewStoreController.class)
                .initProperties(getInitProperties())
                .doModal();
            getViewContext().getStage().close();
        });
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE,
                                    ActionFactory.ActionTypeEnum.FILTER);
        toolBar.getItems().add(customButton);
    }
//
// setPrintParam
//
    private void setPrintParam ( ApReport ap ) 
    {
        if (dsPRODUCT_DIM.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//        
    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PProductDim p = null;
        boolean cheakFilter = false;
        mainModeEnum = mode;
        switch (mode) {
            case VM_INS:
                p = new PProductDim ();
                break;
            case VM_NONE:
                if (dsPRODUCT_DIM.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PProductDim ();
                for (IEntityProperty<PProductDim, ?> ep : EntityMetadataFactory.getEntityMetaData (PProductDim.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsPRODUCT_DIM.getCurrentRow ()));
                break;
            case VM_EDIT:
                p = PRODUCT_DIM.getSelectionModel().getSelectedItem();
                break;
            case VM_DEL:
                PProductDim selectProduct = PRODUCT_DIM.getSelectionModel().getSelectedItem();
                //boolean temp = showErrorAlert("Удалить", "Вы точно хотите удалить " + selectProduct.getPRODUCT_NAME()); 
                boolean temp = Alerts.yesNo(PRODUCT_DIM.getSelectionModel().getSelectedItem(), "Удаление", "Удалить колонку " 
                        + PRODUCT_DIM.getSelectionModel().getSelectedItem().getPRODUCT_NAME() + "?");
                if (temp) {
                    try {
                        new ParamMap()
                                .add("p_id", selectProduct.getPRODUCT_ID())
                                .exec(this, "deleteProduct");
                        } catch (SQLExpressionException ex) {
                            Logger.getLogger(ViewProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    doRefresh();
                }
                break;
            case VM_SHOW:
                p = new PProductDim ();
                cheakFilter = true;
                new FXFormLauncher<>(this, EditProductDimFilterController.class)
                        .dataObject (p)
                        .initProperties(getInitProperties())
                        .callback (this::doFormResult)   
                        .doModal();
        }
        if (p != null && !cheakFilter) 
            new FXFormLauncher<> (this, EditProductDimController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PProductDim> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (mainModeEnum) 
            {
                case VM_INS:
                    dsPRODUCT_DIM.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    doRefresh ();
                    break;
                case VM_EDIT:                
                    dsPRODUCT_DIM.updateCurrentRow (dctl.getDataObject ());
                    doRefresh ();
                    break;
                case VM_DEL:
                    dsPRODUCT_DIM.removeCurrentRow ();      
                    doRefresh ();
                    break;
                case VM_SHOW:
                    
                    products = new ArrayList<>();
                    for (PProductDim item : dsPRODUCT_DIM.getRows())
                        products.add(item);
                    List<PProductDim> tempList = products.stream()
                            .filter(prod -> prod.getCATEGORY_NAME().equals(dctl.getDataObject().getCATEGORY_NAME()))
                            .collect(Collectors.toList());
                    PRODUCT_DIM.getItems().clear();
                    if (tempList != null) 
                        PRODUCT_DIM.getItems().addAll(tempList);   
                    else PRODUCT_DIM.getItems().addAll(products);
                default:
                    break;
            }               
        }
        PRODUCT_DIM.getSelectionModel().clearSelection();
        PRODUCT_DIM.requestFocus ();
    }        

//
//
//    
}

