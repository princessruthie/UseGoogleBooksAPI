package com.ruthiefloats.useguardianapi.parser;

import android.util.Log;

import com.ruthiefloats.useguardianapi.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleJSONParser {
    public static List<Article> parseFeed(String content) {

        try {
            /*
            Construct a Json object out of the passed-in String,
            get a Json array and initialize an empty List of Articles.
             */

            JSONObject obj = new JSONObject(content);
            JSONObject response = obj.getJSONObject("response");

            /*
            Debugging log.
             */
            int numItems = response.getInt("total");
            Log.i("Number of Items", String.valueOf(numItems));

            JSONArray ar = response.getJSONArray("results");
            List<Article> articleList = new ArrayList<>();
            /*Initialize the tags object now so that it doesn't */
            JSONObject tags = null;

            /*
            Iterate over the elements of the JSON array.
            The thumbnail and webUrl must be exposed in a show-fields and show-tags
            in the GET
             */
            for (int i = 0; i < ar.length(); i++) {
                JSONObject item = ar.getJSONObject(i);
                JSONObject fields = item.getJSONObject("fields");
                JSONArray tagsArray = item.getJSONArray("tags");

                boolean hasTags = false;
                if (tagsArray.length() > 0) {
                    tags = tagsArray.getJSONObject(0);
                    hasTags = true;
                }

                /*
                Get the String info and if it's missing, say so.
                 */

                String webTitle = item.optString("webTitle", "No Title Information");
                String webUrl = item.optString("webUrl", "No Web Link Information");
                String thumbnail = fields.optString("thumbnail", "No Thumbnail Information");
                String contributorWebTitle;
                /*
                While the tags array is always present, sometimes it
                is empty.  In the event it's empty, we just record that.
                 */
                if (hasTags) {
                    contributorWebTitle = tags.optString("webTitle", "No Contributor Information");
                } else
                    contributorWebTitle = "No Contributor Information";

                /*
                Create a new article and set the title and author info.
                Then add that article to the List<Article>.
                 */

                articleList.add(new Article(webTitle, webUrl, thumbnail, contributorWebTitle));
            }
            return articleList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}