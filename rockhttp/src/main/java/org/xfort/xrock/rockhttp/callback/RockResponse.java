package org.xfort.xrock.rockhttp.callback;

public class RockResponse {

    /**
     * 结果状态码，可以是 网络异常,http通信异常，解析数据异常等
     */
    public int resCode;

    /**
     * request中缓存模式
     */
    public int cacheMode;
    /**
     * response 结果来源， @{CacheMode}
     * 来自本地缓存|来自网络
     */
    public int resSourceCode;

    public String resData;

    public String resMsg;
}
