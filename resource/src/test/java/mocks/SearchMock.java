package mocks;

import br.feevale.labex.model.SearchItem;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class SearchMock {
    public static SearchItem getSearch(Long id) {
        return new SearchItem("Java EE", id, 0, 5);
    }

    public static SearchItem getSearchWithMaxValue(Long id, int max) {
        return new SearchItem("Java EE", id, 0, max);
    }

    public static SearchItem getInvalidSearch() {
        return new SearchItem(null, null, 0, 0);
    }
}
