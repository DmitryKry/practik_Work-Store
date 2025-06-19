package ru.inversion.catalog;

import java.util.Collections;
import java.util.Map;
import ru.inversion.fx.form.ViewContext;
import ru.inversion.fx.app.BaseApp;
import ru.inversion.fx.form.FXFormLauncher;
import ru.inversion.tc.TaskContext;
import ru.inversion.annotation.StartMethod;

/**
 *
 * @author  admin
 * @since   Tue Jun 17 10:20:08 MSK 2025
 */
public class PSuppliersDimMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewSuppliersDim (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PSUPPLIERSDIM"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewSuppliersDim, JInvDesktop будет премного благодарен") 
    public static void showViewSuppliersDim ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewSuppliersDimController.class)
            .initProperties (map)
            .show ();
    }
    
}

