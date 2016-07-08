package com.ruthiefloats.usegooglebooksapi;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpManager {

    /**
     * @param uri     Used to construt a new URL object.
     * @param context Used to access the app's strings.xml
     * @return The returned input from the connection's stream.
     */

    public static String getData(String uri, Context context) {

        BufferedReader reader = null;

        /*Use the passed in uri to construct a URL, make an http connection
         * and record the connection's input stream. */

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            /*The calling code can check for this failure and notify user */
            return (context.getResources().getString(R.string.uh_oh));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return (context.getResources().getString(R.string.uh_oh));
                }
            }
        }
    }
}