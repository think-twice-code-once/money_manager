package moneymanager.app.com.domains.category;

import dagger.Component;
import moneymanager.app.com.factory.ActivityScope;
import moneymanager.app.com.factory.ApplicationComponent;

/**
 * -> Created by Think-Twice-Code-Once on 6/18/2017.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface CategoryComponent {
    void inject(CategoryFragment categoryFragment);
}
