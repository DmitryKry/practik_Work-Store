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

 
   
    private final XXIDataSet<PStore> dsPstore_table = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsPstore_table.setTaskContext (getTaskContext ());
        dsPstore_table.setRowClass (PStore.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PStore> dsfx = DSFXAdapter.bind (dsPstore_table, Pstore_table, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        Pstore_table.setToolBar (toolBar);       
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        Pstore_table.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());

        doRefresh ();
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
            case VM_SHOW:
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

        Pstore_table.requestFocus ();
    }        
//
//
//    
}

