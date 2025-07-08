package ru.inversion.catalog;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
    @FXML private BorderPane rootPane;
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
        DSFXAdapter<PSuppliersDim> dsfx = DSFXAdapter.bind (dsSUPPLIERS_DIM, SUPPLIERS_DIM, null, false); 

        dsfx.setEnableFilter (true);
                
        initToolBar ();

        SUPPLIERS_DIM.setToolBar (toolBar);       
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        SUPPLIERS_DIM.setAction (ActionFactory.ActionTypeEnum.CHOOSE_DIRECTORY, (a) -> doOperation (FormModeEnum.VM_CHOICE));
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
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.CHOOSE_DIRECTORY,
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
    
    private boolean showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Добавляем кастомные кнопки
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Ждём нажатия кнопки и возвращаем результат
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == okButton;
    }
    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PSuppliersDim p = null;
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
                boolean temp = showErrorAlert("Удалить", "Вы точно хотите удалить " + selectProduct.getFIRST_NAME() + selectProduct.getLAST_NAME() + selectProduct.getPATRONYMIC());
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
                p = null;
                new FXFormLauncher<> (this, EditProductDimController.class)
                .dataObject (prod)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .doModal ();
                break;
        }
        if (p != null) 
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

