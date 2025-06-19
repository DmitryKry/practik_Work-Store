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
 * @since   Mon Jun 16 14:39:40 MSK 2025
 */
public class PProductDimMain extends BaseApp 
{
    
    @Override
    protected void showMainWindow () 
    {
        showViewProductDim (getPrimaryViewContext (), null, Collections.emptyMap ());
    }

    @Override
    public String getAppID () 
    {
        return "XXI.PPRODUCTDIM"; 
    }
    
    public static void main (String[] args) 
    {
        launch (args);
    }

    @StartMethod (description = "Не поленитесь указать описание для showViewProductDim, JInvDesktop будет премного благодарен") 
    public static void showViewProductDim ( ViewContext vc, TaskContext tc, Map<String, Object> map ) 
    {
        new FXFormLauncher<> (tc, vc, ViewProductDimController.class)
            .initProperties (map)
            .show ();
    }
    
}

