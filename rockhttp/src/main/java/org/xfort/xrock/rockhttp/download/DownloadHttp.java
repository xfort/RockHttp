package org.xfort.xrock.rockhttp.download;

import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * 下载文件，待优化。 下目标 使用单一OKhttp,实现断点下载
 */
public class DownloadHttp {

    String url, outFilepath;
    DownloadListener listener;

    public DownloadHttp(String url, String outFilepath, DownloadListener listener) {
        this.url = url;
        this.outFilepath = outFilepath;
        this.listener = listener;
    }


    public boolean downloadFile(Object httpTag) {
        File outFile = new File(outFilepath);
        File parentFile = outFile.getParentFile();
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            if (!parentFile.mkdirs()) {
                return false;
            }
        }

        File tmpFile = new File(parentFile, outFile.getName() + System.currentTimeMillis() + "" +
                ".tmp");
        try {
            if (!tmpFile.exists() || tmpFile.isDirectory()) {
                if (!tmpFile.createNewFile()) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new ProgressResponse(originalResponse
                        .body(), listener)).build();
            }
        }).build();

        Request.Builder builder = new Request.Builder().url(url);
        if (httpTag != null) {
            builder.tag(httpTag);
        }

        try {
            Response response = client.newCall(builder.build()).execute();
            if (!response.isSuccessful()) {
                return false;
            }
            BufferedSink sink = Okio.buffer(Okio.sink(tmpFile));
            sink.writeAll(response.body().source());
            sink.close();
            tmpFile.renameTo(outFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
