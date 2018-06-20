package org.xfort.xrock.rockhttp;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;

public class JSONBody extends RequestBody {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/json; " +
            "charset=utf-8");
    byte[] bytes;

    public JSONBody(String content) {
        Charset charset = Util.UTF_8;
        bytes = content.getBytes(charset);
        int byteCount = bytes.length;
        Util.checkOffsetAndCount(bytes.length, 0, byteCount);
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public long contentLength() throws IOException {
        return bytes.length;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(bytes, 0, bytes.length);
    }

    public static final class Builder {
        final JSONObject jsonObject = new JSONObject();
        private final Charset charset;

        public Builder() {
            this(null);

        }

        public Builder(Charset charset) {
            this.charset = charset;
        }

        public Builder set(String key, Object obj) throws JSONException {
            if (TextUtils.isEmpty(key) || obj == null) {
                return this;
            }
            jsonObject.put(key, obj);
            return this;
        }

        /**
         * @return
         * @throws Exception
         */
        public JSONBody build() throws Exception {
            String json = jsonObject.toString();
            if (TextUtils.isEmpty(json)) {
                throw new Exception("json 转为string失败");
            }
            return new JSONBody(json);
        }
    }
}