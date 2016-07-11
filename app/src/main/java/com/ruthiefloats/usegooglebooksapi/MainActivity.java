package com.ruthiefloats.usegooglebooksapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruthiefloats.usegooglebooksapi.model.Article;
import com.ruthiefloats.usegooglebooksapi.parser.ArticleJSONParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=insubject:";
    private final String BASE_URL = "http://content.guardianapis.com/search?api-key=test&q=politics&show-fields=thumbnail&show-tags=contributor";
    private final String SAVED_USER_SEARCH_TEXT_KEY = "user_search_text";
    /*The String guardian api returns if there are no books matching the user's subject */
//    private final String EMPTY_RESULT_JSON = "{\"kind\":\"books#volumes\",\"totalItems\":0}";
    private final String EMPTY_RESULT_JSON = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":0,\"startIndex\":0,\"pageSize\":10,\"currentPage\":1,\"pages\":0,\"orderBy\":\"relevance\",\"results\":[]}}";


    /* A spinning progress bar */
    ProgressBar progressBar;
    /* A List to hold the queued tasks */
    List<MyTask> tasks;

    ArticleAdapter adapter;

    List<Article> articleList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.the_only_menu_option) {
            requestData(BASE_URL);
            return true;
        } else

            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("key", (ArrayList<Article>) articleList);
//        Log.v("MyActy articleList", articleList.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tasks = new ArrayList<>();

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null || !savedInstanceState.containsKey("key")) {

            articleList = new ArrayList<>();
            adapter = new ArticleAdapter(this, (ArrayList) articleList);

        } else {
            articleList = savedInstanceState.getParcelableArrayList("key");
            //TODO reword this so that we're calling the updateData method
            adapter = new ArticleAdapter(this, (ArrayList) articleList);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * @return Whether the device is online.
     */

    protected boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param uri The entire String to send to google, including search parameters.
     */
    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    /**
     * Populate the ListView with the information in the Book list.
     */
    protected void updateDisplay() {
        if (articleList != null) {
            /*Clear out the adapter and addAll again
            * If I call this from onCreate, it clears out the array.  So...*/
            adapter.clear();
            adapter.addAll(articleList);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    /**
     * An AsyncTask subclass to make network calls on a separate thread.
     */
    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0], MainActivity.this);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            /**
             *If doInBackground is returning the warning String, we've caught one of the
             * connection exceptions and will Toast the user.  Otherwise fill up the
             * bookList Array.
             */

            /* Take out all of the spaces and returns to compare the strings. */
            String testString = result.replaceAll(" ", "");
            testString = testString.replaceAll("\n", "");

            /* Debugging Log */
            Log.i("MainActy compare bool", String.valueOf(result.equals(EMPTY_RESULT_JSON)));

            /*If HttpManager returned the warning string, show toast.  Else if there were no books
            matching, show toast.  Otherwise, parse the result.
             */
            if (testString.equals(getString(R.string.warning))) {
                Toast.makeText(MainActivity.this, R.string.warning, Toast.LENGTH_LONG).show();
            } else if (testString.equals(EMPTY_RESULT_JSON)) {
                Toast.makeText(MainActivity.this, R.string.no_results_message,
                        Toast.LENGTH_LONG).show();
            } else {
                articleList = ArticleJSONParser.parseFeed(result);
                updateDisplay();
            }

            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}