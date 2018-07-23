package org.xfort.xrock.rockhttp.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponse extends ResponseBody {
    ResponseBody resBody;
    DownloadListener listener;
    BufferedSource bufferedSource;

    public ProgressResponse(ResponseBody resBody, DownloadListener listener) {
        this.resBody = resBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return resBody.contentType();
    }

    @Override
    public long contentLength() {
        return resBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(progressSource(resBody.source(), contentLength()));
        }
        return bufferedSource;
    }

    Source progressSource(Source source, long total) {
        final long totalSize = total;
        ForwardingSource forwardingSource = new ForwardingSource(source) {
            long readCount = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readBytes = super.read(sink, byteCount);
                readCount += readBytes != -1 ? readBytes : 0;
                listener.onDownloading(totalSize, readCount);

                return readBytes;
            }
        };
        return forwardingSource;
    }
}
