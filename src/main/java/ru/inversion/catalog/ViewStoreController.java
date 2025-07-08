package ru.inversion.catalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

/**
 *
 * @author  admin
 * @since   Thu Jun 19 12:17:07 MSK 2025
 */
public class ViewStoreController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PStore> Pstore_table;   
    @FXML private JInvToolBar toolBar;
    @FXML private TextField timeOpenField;
    @FXML private TextField timeCloseField;
    @FXML private TextField firstNameField;
    @FXML private TextField secondNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private BorderPane rootPane;
 
   
    private final XXIDataSet<PStore> dsPstore_table = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsPstore_table.setTaskContext (getTaskContext ());
        dsPstore_table.setRowClass (PStore.class);
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
        DSFXAdapter<PStore> dsfx = DSFXAdapter.bind (dsPstore_table, Pstore_table, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        Pstore_table.setToolBar (toolBar);       
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
        Pstore_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTextFields(newSelection);
            }
        });
        doRefresh();
    }
    
    private void updateTextFields(PStore selectSuppliers){
        if (selectSuppliers != null){
            timeOpenField.setText(String.valueOf(selectSuppliers.getTIME_OPEN_STORE().toLocalTime()));
            timeOpenField.setEditable(false);
            
            timeCloseField.setText(String.valueOf(selectSuppliers.getTIME_CLOSE_STORE().toLocalTime()));
            timeCloseField.setEditable(false);
            
            secondNameField.setText(String.valueOf(selectSuppliers.getSECOND_NAME_OWNER()));
            secondNameField.setEditable(false);
            
            firstNameField.setText(String.valueOf(selectSuppliers.getFIRST_NAME_OWNER()));
            firstNameField.setEditable(false);
            
            lastNameField.setText(String.valueOf(selectSuppliers.getLAST_NAME_OWNER()));
            lastNameField.setEditable(false);
            
            emailField.setText(String.valueOf(selectSuppliers.getMAIL()));
            emailField.setEditable(false);
            
            phoneField.setText(String.valueOf(selectSuppliers.getPHONE()));
            phoneField.setEditable(false);            
        } else {
            secondNameField.clear();
            phoneField.clear();
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
            timeOpenField.clear();
            timeCloseField.clear();
        }
    }   
//
// doRefresh
//    
    private void doRefresh () 
    {
        Pstore_table.executeQuery ();
    }
//
// initToolBar
//    
    private void initToolBar () 
    {
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
    }
//
// setPrintParam
//
    private void setPrintParam ( ApReport ap ) 
    {
        if (dsPstore_table.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PStore p = null;

        switch (mode) {
            case VM_INS:
                p = new PStore ();
                break;
            case VM_NONE:
                if (dsPstore_table.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PStore ();
                for (IEntityProperty<PStore, ?> ep : EntityMetadataFactory.getEntityMetaData (PStore.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsPstore_table.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_DEL:
                p = dsPstore_table.getCurrentRow ();
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditStoreController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PStore> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsPstore_table.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsPstore_table.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsPstore_table.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    
        doRefresh ();
        Pstore_table.getSelectionModel().clearSelection();
        Pstore_table.requestFocus ();
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

