package org.xfort.xrock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xfort.xrock.rockhttp.callback.RockUICallback;
import org.xfort.xrock.rockhttp.interceptor.CacheInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class RockHttpFragment extends Fragment {
    OkHttpClient okHttpClient;
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rockhttp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = view.findViewById(R.id.textview);
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
        new okhttp3.internal.cache.CacheInterceptor()
        CacheInterceptor cacheInterceptor = new org.xfort.xrock.rockhttp.interceptor
                .CacheInterceptor(getContext().getApplicationContext().getCacheDir()
                .getAbsolutePath());
        okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(logging).addInterceptor
                (cacheInterceptor).build();
    }

    void testRockUICallback() {
        Request request = new Request.Builder().url("http://www.163.com/").build();

        okHttpClient.newCall(request).enqueue(new RockUICallback<String>(this) {
            @Override
            public void onUIResult(String data) {
                onresult(data);
            }

            @Override
            public String parseResult(byte[] resData) {
                return new String(resData);
            }
        });
    }

    void onresult(String data) {
        tv.setText(data);
    }
}
