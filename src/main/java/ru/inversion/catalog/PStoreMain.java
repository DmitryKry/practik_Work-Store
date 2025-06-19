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
 * @since   Thu Jun 19 12:17:13 MSK 2025
 */
public class PStoreMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewStore (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PSTORE"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewStore, JInvDesktop будет премного благодарен") 
    public static void showViewStore ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewStoreController.class)
            .initProperties (map)
            .show ();
    }
    
}

