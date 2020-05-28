package com.example.demo_rxjava_android.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    val TAG = SearchActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setUpSearchObservable()
    }

    private fun setUpSearchObservable() {

       /* var d : Disposable ?= RxSearchObservable.fromView(searchView)
            .debounce(300,TimeUnit.MILLISECONDS)
            .filter(object: Predicate<String>{
                override fun test(text: String): Boolean {
                    if (text.isEmpty()) {
                        textViewResult.setText("");
                        return false;
                    } else {
                        return true;
                    }
                }
            })
            .distinctUntilChanged()
            .switchMap(object : Function<String, ObservableSource<String>>{
                override fun apply(query: String): ObservableSource<String>? {
                    return dataFromNetwork(query)
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String>{
                override fun accept(result: String?) {
                    textViewResult.setText(result);
                }
            })*/

        var d : Disposable ?= RxSearchObservable.fromView(searchView)
            .debounce(300,TimeUnit.MILLISECONDS)
            .filter{
                if (it.isEmpty()) {
                    textViewResult.setText("");
                     false;
                } else {
                     true;
                }
            }
            .distinctUntilChanged()
            .switchMap{
                dataFromNetwork(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                textViewResult.setText(it);
            }

    }


    /**
    * Simulation of network data
    */
    fun dataFromNetwork(query : String) : Observable<String>? {

         /*return Observable.just(true)
            ?.delay(2, TimeUnit.SECONDS)
            ?.map(object : Function<Boolean,String>{
                override fun apply(value: Boolean): String {
                    return query;
                }
            })*/

        return Observable.just(true)
            ?.delay(2, TimeUnit.SECONDS)
            ?.map{
                query
            }

    }
}