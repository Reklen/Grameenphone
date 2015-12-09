package com.cc.grameenphone.generator;

import com.cc.grameenphone.utils.Logger;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by aditlal on 23/07/15.
 */
public class ServiceGenerator {

    public static final String BASE_URL = "http://202.56.229.146:8992/";
   // public static final String BASE_URL = "http://202.56.5.215:8989/";

    //http://202.56.5.215:8989/GPTxn/jsp/CelliciumSimulator.html

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Logger.i("Retro", msg);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}