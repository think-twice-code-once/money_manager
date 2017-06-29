package moneymanager.app.com.domains.home.add_item;

import android.text.TextUtils;
import android.widget.Filter;

import java.util.List;

import moneymanager.app.com.models.Category;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * -> Created by phong.nguyen@beesightsoft.com on 6/29/2017.
 */

public class CategoryFilter extends Filter {

    private CategoryAdapter categoryAdapter;
    private List<Category> originalCategories;

    public CategoryFilter(CategoryAdapter categoryAdapter, List<Category> originalCategories) {
        this.categoryAdapter = categoryAdapter;
        this.originalCategories = originalCategories;
    }

    @Override
    protected FilterResults performFiltering(CharSequence content) {
        FilterResults filterResults = new FilterResults();

        /*TODO: WTF Realm ? performFiltering is invoked in a worker thread, but Realm is created in main thread,
        use the method category.getContent() that relate to Realm, so that it throws exception about
         incorrect thread from Realm*/

        if (!TextUtils.isEmpty(content)) {
            Observable.from(originalCategories)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(category
                            -> category.getContent().toLowerCase().contains(content.toString().trim().toLowerCase()))
                    .toSortedList((category1, category2)
                            -> category1.getContent().compareToIgnoreCase(category2.getContent()))
                    .subscribe(categories -> {
                        filterResults.values = categories;
                        filterResults.count = categories.size();
                        publishResults(content, filterResults);
                    }, Throwable::printStackTrace);
        } else {
            filterResults.values = originalCategories;
            filterResults.count = originalCategories.size();
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results.values instanceof List) {
            categoryAdapter.clear();
            categoryAdapter.addAll((List) results.values);
            categoryAdapter.notifyDataSetChanged();
        }
    }
}
