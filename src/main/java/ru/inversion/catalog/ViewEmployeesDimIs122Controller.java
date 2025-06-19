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
 * @since   Mon Jun 16 14:56:19 MSK 2025
 */
public class ViewEmployeesDimIs122Controller extends JInvFXBrowserController 
{
    @FXML private JInvTable<PEmployeesDimIs122> EMPLOYEES_DIM_IS122;   
    @FXML private JInvToolBar toolBar;

 
   
    private final XXIDataSet<PEmployeesDimIs122> dsEMPLOYEES_DIM_IS122 = new XXIDataSet<> ();    
//
// initDataSet
//    
    private void initDataSet () throws Exception 
    {
        dsEMPLOYEES_DIM_IS122.setTaskContext (getTaskContext ());
        dsEMPLOYEES_DIM_IS122.setRowClass (PEmployeesDimIs122.class);
    }
//
// Initializes the controller class.
//
    @Override
    protected void init() throws Exception
    {
        setTitle (getBundleString ("VIEW.TITLE"));
        
        initDataSet ();
        DSFXAdapter<PEmployeesDimIs122> dsfx = DSFXAdapter.bind (dsEMPLOYEES_DIM_IS122, EMPLOYEES_DIM_IS122, null, false); 

        dsfx.setEnableFilter (true);
 
                
        initToolBar ();

        EMPLOYEES_DIM_IS122.setToolBar (toolBar);       
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.CREATE, (a) -> doOperation (FormModeEnum.VM_INS));
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.CREATE_BY, (a) -> doOperation (FormModeEnum.VM_NONE));
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.VIEW, (a) -> doOperation (FormModeEnum.VM_SHOW));
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.UPDATE, (a) -> doOperation (FormModeEnum.VM_EDIT));
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.DELETE, (a) -> doOperation (FormModeEnum.VM_DEL));
        EMPLOYEES_DIM_IS122.setAction (ActionFactory.ActionTypeEnum.REFRESH, (a) -> doRefresh ());

        doRefresh ();
    }        
//
// doRefresh
//    
    private void doRefresh () 
    {
        EMPLOYEES_DIM_IS122.executeQuery ();
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
        if (dsEMPLOYEES_DIM_IS122.isEmpty ())
            throw new StopExecuteActionBiCompException ();
    }
//
// doOperation
//    
    private void doOperation ( JInvFXFormController.FormModeEnum mode ) 
    {
        PEmployeesDimIs122 p = null;

        switch (mode) {
            case VM_INS:
                p = new PEmployeesDimIs122 ();
                break;
            case VM_NONE:
                if (dsEMPLOYEES_DIM_IS122.getCurrentRow () == null) 
                    break;
                mode = FormModeEnum.VM_INS;
                p = new PEmployeesDimIs122 ();
                for (IEntityProperty<PEmployeesDimIs122, ?> ep : EntityMetadataFactory.getEntityMetaData (PEmployeesDimIs122.class).getPropertiesMap ().values ())
                    if (! (ep.isTransient () || ep.isId ()))
                        ep.invokeSetter (p, ep.invokeGetter (dsEMPLOYEES_DIM_IS122.getCurrentRow ()));
                break;
            case VM_EDIT:
            case VM_SHOW:
            case VM_DEL:
                p = dsEMPLOYEES_DIM_IS122.getCurrentRow ();
                break;
        }

        if (p != null) 
            new FXFormLauncher<> (this, EditEmployeesDimIs122Controller.class)
                .dataObject (p)
                .dialogMode (mode)
                .initProperties (getInitProperties ())
                .callback (this::doFormResult)    
                .doModal ();
    }
//
// doFormResult 
//
    private void doFormResult ( JInvFXFormController.FormReturnEnum ok, JInvFXFormController<PEmployeesDimIs122> dctl )    
    {
        if (JInvFXFormController.FormReturnEnum.RET_OK == ok)
        {
            switch (dctl.getFormMode ()) 
            {
                case VM_INS:
                    dsEMPLOYEES_DIM_IS122.insertRow (dctl.getDataObject (), IDataSet.InsertRowModeEnum.AFTER_CURRENT, true);
                    break;
                case VM_EDIT:                
                    dsEMPLOYEES_DIM_IS122.updateCurrentRow (dctl.getDataObject ());
                    break;
                case VM_DEL:
                    dsEMPLOYEES_DIM_IS122.removeCurrentRow ();
                    break;
                default:
                    break;
            }                
        }    

        EMPLOYEES_DIM_IS122.requestFocus ();
    }        
    
    @FXML
    private void load_employers(ActionEvent event){
        new FXFormLauncher<>(this, ViewEmployeesDimIs122Controller.class)
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
        new FXFormLauncher<>(this, ViewStoreDimController.class)
                .initProperties(getInitProperties())
                .doModal();
        getViewContext().getStage().close();
    }
//
//
//    
}

