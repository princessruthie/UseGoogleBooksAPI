package com.ruthiefloats.usegooglebooksapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ruthiefloats.usegooglebooksapi.parser.BookJSONParser;
import com.ruthiefloats.usegooglebooksapi.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=insubject:";
    private final String SAVED_USER_SEARCH_TEXT_KEY = "user_search_text";
    /*The String books api returns if there are no books matching the user's subject */
    private final String EMPTY_RESULT_JSON = "{\"kind\":\"books#volumes\",\"totalItems\":0}";
    /* A spinning progress bar */
    ProgressBar pb;
    /* A List to hold the queued tasks */
    List<MyTask> tasks;

    List<Book> bookList;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* Save whatever the user has typed */
        outState.putString(SAVED_USER_SEARCH_TEXT_KEY, getUserSafeSearchText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);


        /*A String to hold the search text, without spaces. */
        String safeSearch;

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Check if you're online then request data  Otherwise throw a toast.*/
                if (isOnline()) {
                    String safeSearch = getUserSafeSearchText();
                    requestData(BASE_URL + safeSearch);
                } else {
                    Toast.makeText(MainActivity.this,
                            R.string.network_unavail_message, Toast.LENGTH_LONG).show();
                }
            }
        });

        if (savedInstanceState != null) {
            /* if the user has already entered a search, reinstate it. */
            safeSearch = savedInstanceState.getString(SAVED_USER_SEARCH_TEXT_KEY);
            if (!safeSearch.equals("")) {
                requestData(BASE_URL + safeSearch);
            }
        }
    }

    /**
     * @return The user's search String where spaces have been converted to +'s
     */
    private String getUserSafeSearchText() {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String searchText = editText.getText().toString();
        return searchText.replace(" ", "+");
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
        if (bookList != null) {
            BookAdapter adapter = new BookAdapter(this, (ArrayList<Book>) bookList);
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
                pb.setVisibility(View.VISIBLE);
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
                bookList = BookJSONParser.parseFeed(result);
                updateDisplay();
            }

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }
    }
}