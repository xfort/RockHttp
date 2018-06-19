package org.xfort.xrock.rockhttp.callback;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public abstract class RockUICallback<T> extends RockCallback implements Runnable {

    public abstract void onUIResult(T data);

    public abstract T parseResult(byte[] resData);

    WeakReference<Fragment> weakFragment;
    WeakReference<Activity> weakActivity;
    T objData;

    public RockUICallback(Fragment fragment) {
        weakFragment = new WeakReference<>(fragment);
    }

    public RockUICallback(Activity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    public void onResult(byte[] resData, Exception e) {
        objData = parseResult(resData);
        if (weakFragment != null) {
            if (weakFragment.get() != null && weakFragment.get().getActivity() != null) {
                weakFragment.get().getActivity().runOnUiThread(this);
            }
        } else if (weakActivity != null) {
            if (weakActivity.get() != null) {
                weakActivity.get().runOnUiThread(this);
            }
        }
    }

    @Override
    public void run() {
        onUIResult(objData);
    }
}
