package com.burocreativo.notelimites.utils;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Juan C. Acosta on 1/31/2017.
 *
 */

public class CitySearchRecentSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = CitySearchRecentSuggestionProvider.class.getName();
    public static final int MODE = DATABASE_MODE_QUERIES;

    public CitySearchRecentSuggestionProvider(){
        setupSuggestions(AUTHORITY,MODE);
    }
}
