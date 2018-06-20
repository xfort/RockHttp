package org.xfort.xrock.rockhttp.callback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;

import java.io.StringReader;
import java.lang.reflect.Type;

public abstract class GsonUICallback<T> extends RockUICallback<T> {

    Type type = null;

    public GsonUICallback(Fragment fragment, Type type) {
        super(fragment);
        this.type = type;
    }

    public GsonUICallback(FragmentActivity activity, Type type) {
        super(activity);
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public T parseResult(byte[] resData) {
        if (type == null) {
            return null;
        }
        try {
            return new Gson().fromJson(new StringReader(new String(resData)), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
