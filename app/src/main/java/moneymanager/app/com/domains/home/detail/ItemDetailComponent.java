package moneymanager.app.com.domains.home.detail;

import dagger.Component;
import moneymanager.app.com.factory.ActivityScope;
import moneymanager.app.com.factory.ApplicationComponent;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/30/2017.
 */

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface ItemDetailComponent {
    void inject(ItemDetailActivity itemDetailActivity);
}
