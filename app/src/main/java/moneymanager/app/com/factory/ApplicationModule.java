package moneymanager.app.com.factory;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Realm provideRealm() {
        Realm.init(application);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build());
        return Realm.getDefaultInstance();
    }
}
