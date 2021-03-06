package org.xfort.xrock.rockhttp;

public interface CacheMode {

    public final String CacheKey = "rock_cache_key";

    public final String CacheModeKey = "rock_cache_mode";

    /**
     * 只是用本地缓存
     */
    public final int CacheOnly = 1;

    /**
     * 只使用网络数据
     */
    public final int NetworkOnly = 2;

    /**
     * 若有缓存数据，则不做网络请求。若无缓存，则使用网络数据
     */
    public final int Cache_Else_Network = 3;

    /**
     * 网络请求失败/网络不可用，则使用缓存数据
     */
    public final int Network_Else_Cache = 4;

    /**
     * 先使用缓存数据（若有数据），收到网络数据后和缓存数据对比，若不同则使用网络数据。先使用缓存数据填充UI，然后等待网络结果，避免UI空白
     */
    public final int Cache_And_Network = 5;

    /**
     * Response 数据来源，本地缓存|网络，在Response header中使用
     */
    public final String DataSourceType = "rock_data_source";

    public final int FromCache = 10;
    public final int FromNetwork = 11;
}
