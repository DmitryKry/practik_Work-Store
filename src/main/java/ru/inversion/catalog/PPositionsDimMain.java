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
 * @since   Mon Jun 16 14:31:23 MSK 2025
 */
public class PPositionsDimMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewPositionsDim (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PPOSITIONSDIM"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewPositionsDim, JInvDesktop будет премного благодарен") 
    public static void showViewPositionsDim ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewPositionsDimController.class)
            .initProperties (map)
            .show ();
    }
    
}

