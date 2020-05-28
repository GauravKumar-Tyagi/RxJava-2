package com.example.demo_rxjava_android.helper.model

import io.reactivex.Observable
import io.reactivex.ObservableSource
import java.util.concurrent.Callable

class Car {
    private var brand: String? = null

    fun setBrand(brand: String) {
        this.brand = brand
    }

    fun brandDeferObservable1(): Observable<String> {
        return Observable.defer { Observable.just(brand) }
    }

    fun brandDeferObservable2(): Observable<String> {
        return Observable.defer(object : Callable<ObservableSource< String>>{
            override fun call(): ObservableSource<String> {
                return Observable.just(brand)
            }

        })
    }
}