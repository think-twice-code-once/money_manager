package moneymanager.app.com.factory;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

@EApplication
public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setApplicationComponent(DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
