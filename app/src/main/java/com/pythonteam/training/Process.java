package com.pythonteam.training;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class Process extends AsyncTask<Void,Integer,Void>{

    private ProgressBar pgBar;
    private Context mContext;

    public Process(ProgressBar pgBar, Context mContext)
    {
        this.pgBar = pgBar;
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 1; i < 5; i++) {
            publishProgress(i*20);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mContext.startActivity(new Intent(mContext, MainActivity.class));
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pgBar.setProgress(values[0]);
    }
}


