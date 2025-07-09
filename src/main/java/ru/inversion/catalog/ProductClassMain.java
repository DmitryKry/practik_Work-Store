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
 * @since   Wed Jun 18 14:43:39 MSK 2025
 */
public class ProductClassMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewroductClass (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PRODUCTCLASS"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewroductClass, JInvDesktop будет премного благодарен") 
    public static void showViewroductClass ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewroductClassController.class)
            .initProperties (map)
            .show ();
    }
    
}

