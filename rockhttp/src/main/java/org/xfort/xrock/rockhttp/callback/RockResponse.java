package org.xfort.xrock.rockhttp.callback;

public class RockResponse {
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
}
