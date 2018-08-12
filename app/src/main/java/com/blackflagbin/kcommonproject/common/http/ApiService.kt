package com.blackflagbin.kcommonproject.common.http

import com.blackflagbin.kcommonproject.BuildConfig
import com.blackflagbin.kcommonproject.common.entity.net.DataItem
import com.blackflagbin.kcommonproject.common.entity.net.HttpResultEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by blackflagbin on 2018/3/19.
 */
interface ApiService {
    @GET("${BuildConfig.EXTRA_URL}search/query/listview/category/{type}/count/{limit}/page/{pageNo}")
    fun getMainDataList(
            @Path("type") type: String, @Path("limit") limit: Int, @Path(
                    "pageNo") pageNo: Int): Observable<HttpResultEntity<List<DataItem>>>
}