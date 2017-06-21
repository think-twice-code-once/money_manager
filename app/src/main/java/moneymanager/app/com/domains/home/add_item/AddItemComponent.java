package moneymanager.app.com.domains.home.add_item;

import dagger.Component;
import moneymanager.app.com.factory.ActivityScope;
import moneymanager.app.com.factory.ApplicationComponent;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/21/2017.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface AddItemComponent {
    void inject(AddItemActivity addItemActivity);
}
