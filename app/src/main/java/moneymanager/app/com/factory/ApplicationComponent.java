package moneymanager.app.com.factory;

import dagger.Component;
import io.realm.Realm;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/19/2017.
 */

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Realm realm();
}
