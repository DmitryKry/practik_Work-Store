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
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.inversion.bicomp.util.ParamMap;
import ru.inversion.db.expr.SQLExpressionException;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
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
    private Thread thread;
 
   
    private final XXIDataSet<PProductDim> dsPRODUCT_DIM = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsPRODUCT_DIM.setTaskContext (getTaskContext ());
        dsPRODUCT_DIM.setRowClass (PProductDim.class);
    }
    
    private void centerStage(Stage stage) {
        // Получаем размеры экрана
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        
        // Вычисляем координаты для центрирования
        double x = (screenWidth - stage.getWidth()) / 2;
        double y = (screenHeight - stage.getHeight()) / 2;
        
        // Устанавливаем позицию
        stage.setX(x);
        stage.setY(y);
    }
    
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        
        // Устанавливаем начальный размер окна
        stage.setWidth(800);  // Ширина
        stage.setHeight(400); // Высота
        
        // Центрируем окно на экране
        centerStage(stage);
        setTitle (getBundleString ("VIEW.TITLE"));
        initDataSet ();
        DSFXAdapter<PProductDim> dsfx = DSFXAdapter.bind (dsPRODUCT_DIM, PRODUCT_DIM, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        PRODUCT_DIM.setToolBar (toolBar);       
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        PRODUCT_DIM.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
        PRODUCT_DIM.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });
        doRefresh();
        
        forDorefresh.setProductCheak(false);
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                if (forDorefresh.getProductCheak()){
                    doRefresh ();
                    
                    forDorefresh.setProductCheak(false);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ViewProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        thread.start();
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
        if (dsPRODUCT_DIM.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PProductDim p = null;

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
            case VM_SHOW:
            case VM_DEL:
                PProductDim selectProduct = PRODUCT_DIM.getSelectionModel().getSelectedItem();
                    try {
                        new ParamMap()
                                .add("p_id", selectProduct.getPRODUCT_ID())
                                .exec(this, "deleteProduct");
                        } catch (SQLExpressionException ex) {
                            Logger.getLogger(ViewProductDimController.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    doRefresh();
                break;
        }
        if (p != null) 
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
            switch (dctl.getFormMode ()) 
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
                    
                    break;
                default:
                    break;
            }               
        }
        doRefresh ();

        PRODUCT_DIM.requestFocus ();
    }        
    
    @FXML
    private void load_suppliers(ActionEvent event){
        if (thread != null) {
            thread.interrupt();  // Посылаем сигнал прерывания
            thread = null;
        }
        new FXFormLauncher<>(this, ViewSuppliersDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
    
    @FXML
    private void load_product(ActionEvent event){
        if (thread != null) {
            thread.interrupt();  // Посылаем сигнал прерывания
            thread = null;
        }
        new FXFormLauncher<>(this, ViewProductDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
    
    @FXML
    private void load_category(ActionEvent event){
        if (thread != null) {
            thread.interrupt();  // Посылаем сигнал прерывания
            thread = null;
        }
        new FXFormLauncher<>(this, ViewCategoryDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
       
    @FXML
    private void load_store(ActionEvent event){
        if (thread != null) {
            thread.interrupt();  // Посылаем сигнал прерывания
            thread = null;
        }
        new FXFormLauncher<>(this, ViewStoreController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }   

//
//
//    
}

