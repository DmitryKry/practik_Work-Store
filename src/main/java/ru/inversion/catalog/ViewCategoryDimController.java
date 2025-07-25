package ru.inversion.catalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import ru.inversion.fx.form.action.ActionBuilder;

/**
 *
 * @author  admin
 * @since   Mon Jun 16 14:30:28 MSK 2025
 */
public class ViewCategoryDimController extends JInvFXBrowserController 
{
    @FXML private JInvTable<PCategoryDim> CATEGORY_DIM;   
    @FXML private JInvToolBar toolBar;
    @FXML private BorderPane rootPane;
 
   
    private final XXIDataSet<PCategoryDim> dsCATEGORY_DIM = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsCATEGORY_DIM.setTaskContext (getTaskContext ());
        dsCATEGORY_DIM.setRowClass (PCategoryDim.class);
    }
   
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PCategoryDim> dsfx = DSFXAdapter.bind (dsCATEGORY_DIM, CATEGORY_DIM, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        CATEGORY_DIM.setToolBar (toolBar);
        CATEGORY_DIM.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        CATEGORY_DIM.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        CATEGORY_DIM.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        CATEGORY_DIM.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());
       
        doRefresh ();
        
    }        
//
// doRefresh
//    
    private void doRefresh () 
    {
        CATEGORY_DIM.executeQuery ();
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
        toolBar.setStandartActions (ActionFactory.ActionTypeEnum.CREATE, 
                                    ActionFactory.ActionTypeEnum.UPDATE,
                                    ActionFactory.ActionTypeEnum.DELETE);
        toolBar.getItems().add(customButton);
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
        if (dsCATEGORY_DIM.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PCategoryDim p = null;
        switch (mode) {
            case VM_INS:
                p = new PCategoryDim ();
                break;
            case VM_NONE:
                if (dsCATEGORY_DIM.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PCategoryDim ();
                for (IEntityProperty<PCategoryDim, ?> ep : EntityMetadataFactory.getEntityMetaData (PCategoryDim.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsCATEGORY_DIM.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_DEL:
                p = dsCATEGORY_DIM.getCurrentRow ();
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditCategoryDimController.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PCategoryDim> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsCATEGORY_DIM.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsCATEGORY_DIM.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsCATEGORY_DIM.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }  
        doRefresh ();

        CATEGORY_DIM.requestFocus ();
    }   
//
//
//    
}

