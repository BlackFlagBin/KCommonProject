package com.blackflagbin.kcommon.http.transformer


import com.blackflagbin.kcommon.entity.net.ApiException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class ErrorCheckTransformer<T> : ObservableTransformer<IHttpResultEntity<T>, T> {

    override fun apply(httpResultObservable: Observable<IHttpResultEntity<T>>): Observable<T> {
        return httpResultObservable.map { httpResult ->
            if (!httpResult.isSuccess) {
                throw ApiException(httpResult.errorCode, httpResult.errorMessage)
            }

            if (httpResult.result == null) {
                Any() as T
            } else {
                httpResult.result
            }
        }
    }
}
