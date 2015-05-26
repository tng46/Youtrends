package com.example.santo.youtrends2.app.json;

import com.example.santo.youtrends2.app.pojo.Trend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_DATE;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_DESCR;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_DISLIKE_COUNT;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_ID;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_ITEMS;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_LIKE_COUNT;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_MEDIUM;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_SNIPPET;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_STATS;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_THUMB;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_THUMB_URL;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_TITLE;
import static com.example.santo.youtrends2.app.extras.Keys.EndpointsMusica.KEY_VIEW_COUNT;

/**
 * Created by Santo on 13/05/2015.
 */
public class Parser {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static ArrayList<Trend> parseJSONResponse (JSONObject response){


        ArrayList<Trend> listTrends = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {

                if (response.has(KEY_ITEMS)) {
                    JSONArray arrayItems = response.getJSONArray(KEY_ITEMS);
                    for (int i = 0; i < arrayItems.length(); i++) {
                        JSONObject currentItem = arrayItems.getJSONObject(i);
                        String currentId = currentItem.getString(KEY_ID);
                        JSONObject currentSnippet = currentItem.getJSONObject(KEY_SNIPPET);
                        String currentDate = currentSnippet.getString(KEY_DATE).substring(0, currentSnippet.getString(KEY_DATE).indexOf("T"));
                        String currentTitle = currentSnippet.getString(KEY_TITLE);
                        String currentDescr = currentSnippet.getString(KEY_DESCR);
                        String currentThumb = currentSnippet.getJSONObject(KEY_THUMB).getJSONObject(KEY_MEDIUM).getString(KEY_THUMB_URL);
                        JSONObject currentStats = currentItem.getJSONObject(KEY_STATS);
                        String currentViewCount = currentStats.getString(KEY_VIEW_COUNT);
                        String currentLikeCount = currentStats.getString(KEY_LIKE_COUNT);
                        String currentDislikeCount = currentStats.getString(KEY_DISLIKE_COUNT);


                        Trend trend = new Trend();
                        trend.setId(currentId);
                        trend.setPosition("" + (i + 1));
                        trend.setTitle(currentTitle);
                        trend.setUrlThumb(currentThumb);

                        Date date = dateFormat.parse(currentDate);
                        trend.setPublishedAt(date);
                        trend.setDescription(currentDescr);
                        trend.setViewCount(currentViewCount);
                        trend.setLikeCount(currentLikeCount);
                        trend.setDislikeCount(currentDislikeCount);

                        listTrends.add(trend);
                    }
                }

            } catch (JSONException e) {

            } catch (ParseException e) {

            }
        }

        return listTrends;
    }
}
