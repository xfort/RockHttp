package org.xfort.xrock.rockhttp.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.jakewharton.disklrucache.DiskLruCache;

import org.xfort.xrock.rockhttp.CacheMode;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

/**
 * 根据 request.header 中rock_cache_key 内容在做缓存key。get，post均可使用
 */
public class CacheInterceptor implements Interceptor {
    final String TAG = "CacheInterceptor";
    DiskLruCache diskLruCache;
    WeakReference<Context> weakCtx;

    public CacheInterceptor(DiskLruCache diskLruCache, Context appContext) {
        this.diskLruCache = diskLruCache;
        weakCtx = new WeakReference<>(appContext);
//        Log.d(TAG, "CacheInterceptor()" + System`Clock.elapsedRealtime());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheMode = request.header(CacheMode.CacheModeKey);
        String cacheKey = request.header(CacheMode.CacheKey);

        if (TextUtils.isEmpty(cacheKey)) {
            //未设置缓存key，则不做任何处理
            return chain.proceed(request);
        }

        if (TextUtils.equals(cacheMode, CacheMode.CacheOnly + "")) {
            //只用缓存模式
            String cacheData = getCache(diskLruCache, String.valueOf(cacheKey.hashCode()));

            if (TextUtils.isEmpty(cacheData)) {
                //无缓存数据|读取缓存失败
                return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).message
                        ("缓存数据为空").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).header(CacheMode
                        .DataSourceType, CacheMode.FromCache + "").receivedResponseAtMillis(System.currentTimeMillis
                        ()).build();
            } else {
                // 从缓存中读到数据
                return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).body
                        (ResponseBody.create(null, cacheData)).header(CacheMode.DataSourceType, CacheMode.FromCache +
                        "").build();
            }
        }

        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.equals(cacheMode, CacheMode.NetworkOnly + "")) {
            //只使用网络数据
            response.header(CacheMode.DataSourceType, CacheMode.FromNetwork + "");
            return response;
        }

        if (TextUtils.equals(cacheMode, CacheMode.Network_Else_Cache + "")) {
            //先使用网络，若失败则使用本地数据
            if (response == null || !response.isSuccessful()) {
                String cacheData = getCache(diskLruCache, String.valueOf(cacheKey.hashCode()));
                if (TextUtils.isEmpty(cacheData)) {
                    //无缓存数据|读取缓存失败
                    return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200)
                            .message("缓存数据为空").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).header(CacheMode
                                    .DataSourceType, CacheMode.FromCache + "").receivedResponseAtMillis(System
                                    .currentTimeMillis()).build();
                } else {
                    // 从缓存中读到数据
                    return new Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(200).body
                            (ResponseBody.create(null, cacheData)).header(CacheMode.DataSourceType, CacheMode
                            .FromCache + "").build();
                }
            }
        }
        return response;
    }

    String getCache(DiskLruCache diskLruCache, String cacheKey) {
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(String.valueOf(cacheKey.hashCode()));
            if (snapshot != null) {
                return snapshot.getString(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void setCache(DiskLruCache diskLruCache, String cacheKey) {

    }

}
