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
 * @since   Mon Jun 16 14:56:23 MSK 2025
 */
public class PEmployeesDimIs122Main extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewEmployeesDimIs122 (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PEMPLOYEESDIMIS122"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewEmployeesDimIs122, JInvDesktop будет премного благодарен") 
    public static void showViewEmployeesDimIs122 ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewEmployeesDimIs122Controller.class)
            .initProperties (map)
            .show ();
    }
    
}

