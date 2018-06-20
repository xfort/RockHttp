package org.xfort.xrock.rockhttp.callback;

import android.arch.lifecycle.Lifecycle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.lang.ref.WeakReference;

public abstract class RockUICallback<T> extends RockCallback implements Runnable {

    public abstract void onUIResult(T data);

    public abstract T parseResult(byte[] resData);

    WeakReference<Fragment> weakFragment;
    WeakReference<FragmentActivity> weakActivity;
    T objData;

    public RockUICallback(Fragment fragment) {
        weakFragment = new WeakReference<>(fragment);
    }

    public RockUICallback(FragmentActivity activity) {
        weakActivity = new WeakReference<>(activity);
    }

    @Override
    public void onResult(byte[] resData, Exception e) {
        objData = parseResult(resData);
        Lifecycle.State statcode = null;
        FragmentActivity fragmentActivity = null;
        if (weakFragment != null && weakFragment.get() != null) {
            Fragment fragment = weakFragment.get();
            if (!fragment.isDetached() && !fragment.isRemoving() && fragment.getActivity() !=
                    null) {
                fragmentActivity = fragment.getActivity();
                statcode = fragment.getLifecycle().getCurrentState();
            }
        } else if (weakActivity != null && weakActivity.get() != null) {
            fragmentActivity = weakActivity.get();
            statcode = fragmentActivity.getLifecycle().getCurrentState();
        }

        if (fragmentActivity != null && statcode != null && statcode != Lifecycle.State.DESTROYED) {
            fragmentActivity.runOnUiThread(this);
        }
    }

    @Override
    public void run() {
        onUIResult(objData);
    }
}
