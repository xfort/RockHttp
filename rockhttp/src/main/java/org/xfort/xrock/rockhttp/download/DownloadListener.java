package org.xfort.xrock.rockhttp.download;

public interface DownloadListener {
    public void onDownloading(long totalSize, long downloadedSize);

    public void onFinished(String outFile);
}
