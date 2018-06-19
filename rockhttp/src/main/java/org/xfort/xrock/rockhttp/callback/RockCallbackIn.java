package org.xfort.xrock.rockhttp.callback;

import okhttp3.Callback;

public interface RockCallbackIn extends Callback {
    public void onResult(byte[] resData, Exception e);
}
