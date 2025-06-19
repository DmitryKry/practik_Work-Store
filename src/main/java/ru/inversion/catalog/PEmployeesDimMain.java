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
 * @since   Mon Jun 16 14:31:50 MSK 2025
 */
public class PEmployeesDimMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewEmployeesDim (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PEMPLOYEESDIM"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewEmployeesDim, JInvDesktop будет премного благодарен") 
    public static void showViewEmployeesDim ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewEmployeesDimController.class)
            .initProperties (map)
            .show ();
    }
    
}

