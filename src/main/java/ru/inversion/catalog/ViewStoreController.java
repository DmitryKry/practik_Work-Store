package ru.inversion.catalog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import javafx.scene.control.Label;
import ru.inversion.fx.form.*;
import ru.inversion.fx.form.controls.*;

/**
 *
 * @author  admin
 * @since   Thu Jun 19 12:17:07 MSK 2025
 */
public class ViewStoreController extends JInvFXBrowserController 
{ 
    @FXML private JInvLabel adressInvLabel;
    @FXML private JInvLabel dateOpenStoreInvLabel;
    @FXML private JInvLabel firstNameOwnerInvLabel;
    @FXML private JInvLabel maiLabel;
    @FXML private JInvLabel nameStoreLabel;
    @FXML private JInvLabel phoneInvLabel;
    @FXML private JInvLabel timeOpenInvLabel;
    private PStore infoStore;
   
    private final XXIDataSet<PStore> dsPstore_table = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsPstore_table.setTaskContext (getTaskContext ());
        dsPstore_table.setRowClass (PStore.class);
        dsPstore_table.executeQuery();
    }
    
// Initializes the controller class.
//    
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        initDataSet ();
        infoStore = dsPstore_table.getRows().get(0);
        Properties properties = new Properties();
        // Вариант 1 (лучший):
        try (InputStream input = getClass().getResourceAsStream("/ru/inversion/catalog/res/ViewStore.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл не найден! Проверьте путь: /ru/inversion/catalog/res/ViewStore.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        adressInvLabel.setText(properties.getProperty("ADDRESS") + ": " + infoStore.getADDRESS().toString());
        dateOpenStoreInvLabel.setText(properties.getProperty("DATE_OPEN_STORE") + ": " + infoStore.getDATE_OPEN_STORE().toString());
        firstNameOwnerInvLabel.setText(properties.getProperty("FULL_NAME") + ": " + infoStore.getSECOND_NAME_OWNER().toString() + " "
                + infoStore.getFIRST_NAME_OWNER().toString() + " "
                + infoStore.getLAST_NAME_OWNER().toString());
        maiLabel.setText(properties.getProperty("MAIL") + ": " + infoStore.getMAIL().toString());
        nameStoreLabel.setText(properties.getProperty("NAME_STORE") + ": " + infoStore.getNAME_STORE().toString());
        phoneInvLabel.setText(properties.getProperty("PHONE") + ": " + infoStore.getPHONE().toString());
        timeOpenInvLabel.setText(properties.getProperty("TIME_OF_WORK") + ": c " + String.valueOf(infoStore.getTIME_OPEN_STORE().toLocalTime()) + " до "
        + String.valueOf(infoStore.getTIME_CLOSE_STORE().toLocalTime()));
        
    } 
//
// doRefresh
//    
//
// initToolBar
//    
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
//
//
//    
}

