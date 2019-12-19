package com.iot.smartpump.utils;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class CheckConnection extends AsyncTask<Void, Void, Boolean> {
    public interface CheckConnectionCallBack {
        void stats(boolean status);
    }

    CheckConnectionCallBack checkConnectionCallBack;

    public CheckConnection(CheckConnectionCallBack checkConnectionCallBack) {
        this.checkConnectionCallBack = checkConnectionCallBack;
    }

    @Override
    protected void onPostExecute(Boolean status) {
        if (checkConnectionCallBack != null) {
            checkConnectionCallBack.stats(status);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(1000);

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
