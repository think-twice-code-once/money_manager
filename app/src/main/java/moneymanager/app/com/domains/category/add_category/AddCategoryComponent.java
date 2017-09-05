package moneymanager.app.com.domains.category.add_category;

import dagger.Component;
import moneymanager.app.com.factory.ActivityScope;
import moneymanager.app.com.factory.ApplicationComponent;

/**
 * -> Created by Think-Twice-Code-Once on 9/1/2017.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface AddCategoryComponent {
    void inject(AddCategoryActivity addCategoryActivity);
}
