package com.blackflagbin.kcommon.http.transformer


import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultTransformer<T> : ObservableTransformer<IHttpResultEntity<T>, T> {

    override fun apply(observable: Observable<IHttpResultEntity<T>>): Observable<T> {
        return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(
                        Schedulers.io()).compose(ErrorCheckTransformer()).observeOn(
                        AndroidSchedulers.mainThread())
    }
}