package com.example.santo.youtrends2.app.json;

import com.example.santo.youtrends2.app.youtrends.MyApplication;
import static com.example.santo.youtrends2.app.extras.UrlEndpoints.*;

/**
 * Created by Santo on 13/05/2015.
 */
public class EndPoints {
    public static String getRequestURL (int categoryId, int limit, String regionCode) {
        String s = BASE_URL
                + CHAR_QUESTION
                + PARAM_LIMIT + limit
                + CHAR_AMPERSAND
                + PARAM_CHART
                + CHAR_AMPERSAND
                + PARAM_PART
                + CHAR_AMPERSAND
                + PARAM_API_KEY + MyApplication.API_KEY_YOUTUBE;

        if (categoryId != 0) {
            s += CHAR_AMPERSAND + PARAM_CATEGORY_ID + categoryId;
        }

        if (regionCode != "ALL") {
            s += CHAR_AMPERSAND + PARAM_REGION + regionCode;
        }
        return s;

    }
}
