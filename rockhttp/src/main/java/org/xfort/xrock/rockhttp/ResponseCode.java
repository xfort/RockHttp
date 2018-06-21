package org.xfort.xrock.rockhttp;

public interface ResponseCode {

    public final int Error = 1000;
    /**
     * 网络不可用
     */
    public final int NetworkError = 1001;
    /**
     * 读取市局失败
     */
    public final int ReadFail = 1002;

    /**
     * 解析数据失败
     */
    public final int ParseResponseFail = 1003;

}


