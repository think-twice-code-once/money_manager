package moneymanager.app.com.domains.home;

import dagger.Component;
import moneymanager.app.com.factory.ActivityScope;
import moneymanager.app.com.factory.ApplicationComponent;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
