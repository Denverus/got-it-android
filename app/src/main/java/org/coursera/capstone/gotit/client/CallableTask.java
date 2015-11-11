/* 
**
** Copyright 2014, Jules White
**
** 
*/
package org.coursera.capstone.gotit.client;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.provider.ProviderFactory;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;


public class CallableTask<T> extends AsyncTask<Void,Double,T> {

    private static final String TAG = CallableTask.class.getName();

    public static <V> void invoke(Context context, ServiceCall<V> call, TaskCallback<V> callback){
        ServiceApi srv = ProviderFactory.getOrShowLogin(context);
        if (srv != null) {
            new CallableTask<V>(srv, call, callback).execute();
        } else {
            Toast.makeText(context, "Service error", Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceCall<T> callable_;

    private TaskCallback<T> callback_;

    private ServiceApi service;
    
    private Exception error_;

    public CallableTask(ServiceApi srv, ServiceCall<T> callable, TaskCallback<T> callback) {
        callable_ = callable;
        callback_ = callback;
        service = srv;
    }

    @Override
    protected T doInBackground(Void... ts) {
        T result = null;
        try{
            result = callable_.call(service);
        } catch (Exception e){
            Log.e(TAG, "Error invoking callable in AsyncTask callable: "+callable_, e);
            error_ = e;
        }
        return result;
    }

    @Override
    protected void onPostExecute(T r) {
    	if(error_ != null){
    		callback_.error(error_);
    	}
    	else {
    		callback_.success(r);
    	}
    }
}

