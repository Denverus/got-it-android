package org.coursera.capstone.gotit.client.provider;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.coursera.capstone.gotit.client.activities.LoginScreenActivity;
import org.coursera.capstone.gotit.client.model.CheckIn;
import org.coursera.capstone.gotit.client.oauth.SecuredRestBuilder;
import org.coursera.capstone.gotit.client.unsafe.EasyHttpClient;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.converter.GsonConverter;

/**
 * Created by dtrotckii on 11/6/2015.
 */
public class ProviderFactory {

    public static final int INTERNAL_PROVIDER = 1;
    public static final int SPRING_PROVIDER = 2;

    public static final String CLIENT_ID = "mobile";

    private static final ServiceApi internalProvider = new InternalProvider();

    private static ServiceApi service;

    public static synchronized ServiceApi getOrShowLogin(Context ctx) {
        if (service != null) {
            return service;
        } else {
            Intent i = new Intent(ctx, LoginScreenActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    public static synchronized void init(int serverType, String server, String user, String pass) {

        switch (serverType) {
            case INTERNAL_PROVIDER: {
                service = internalProvider;
                break;
            }
            case SPRING_PROVIDER: {
                service = createRestProvider(server, user, pass);
                break;
            }
        }
    }

    private static ServiceApi createRestProvider(String server, String user, String pass) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CheckIn.class, new CheckinDeserializer())
                .create();

        return new SecuredRestBuilder()
                .setLoginEndpoint(server + ServiceApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build()
                .create(ServiceApi.class);
    }
}
