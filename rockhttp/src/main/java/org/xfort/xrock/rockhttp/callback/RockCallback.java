package org.xfort.xrock.rockhttp.callback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public abstract class RockCallback implements RockCallbackIn {
    @Override
    public void onFailure(Call call, IOException e) {
        if (call.isCanceled()) {
            return;
        }
        onResult(null, e);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (call.isCanceled()) {
//            onResult(null, new Exception("canceled"));
            return;
        }
        if (response == null) {
            onResult(null, new Exception("response null"));
            return;
        }
        try {
            byte[] resData = response.body().bytes();
            onResult(resData, null);
        } catch (Exception e) {
            onResult(null, e);
        }
    }
}
