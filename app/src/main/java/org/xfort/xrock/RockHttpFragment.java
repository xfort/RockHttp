package org.xfort.xrock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xfort.xrock.rockhttp.callback.RockUICallback;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class RockHttpFragment extends Fragment {
    OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initOkhttp();
        testRockUICallback();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    void initOkhttp() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(logging).addInterceptor()
                .build();
    }

    void testRockUICallback() {
        Request request = new Request.Builder().url("").build();
        okHttpClient.newCall(request).enqueue(new RockUICallback<Object>(this) {
            @Override
            public void onUIResult(Object data) {

            }

            @Override
            public Object parseResult(byte[] resData) {
                return null;
            }
        });
    }
}
