package org.xfort.xrock.rockhttp.interceptor;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.DiskLruCache;

public class CacheInterceptor implements Interceptor {
    final String TAG = "CacheInterceptor";
    DiskLruCache diskLruCache;

    public CacheInterceptor(String cacheDir) {
//        diskLruCache = DiskLruCache.create(,cacheDir,);
        Log.d(TAG, "CacheInterceptor()" + SystemClock.elapsedRealtime());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheKey = request.header("cache_key");
        if (TextUtils.isEmpty(cacheKey)) {
            return chain.proceed(request);
        }
//        String cacheMode = request.header("cache_mode");
        try {
            Response response = chain.proceed(request);
            if (response.isSuccessful()) {
//              diskLruCache.edit(cacheKey).
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
