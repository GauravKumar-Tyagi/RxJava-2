package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit


class TakeWhileExampleActivity  : AppCompatActivity() {
    var TAG : String = TakeWhileExampleActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)
        btn?.setOnClickListener {
            doSomeWork1()
        }
        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }
        textFunctions?.text = "Observable.just with zipWith , takeWhile &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }

    fun doSomeWork1(){
        getObservable()
            //Delay item emission by one second
            .zipWith(Observable.interval(0, 1, TimeUnit.SECONDS), object : BiFunction<String, Long, String> {
                override fun apply(s: String, aLong: Long): String {
                    return s
                }
            })
            //Take the items until the condition is met.
            .takeWhile(object : Predicate<String> {
                override fun test(s: String): Boolean {
                    return !s.toLowerCase().contains("honey")
                }
            })
            //We need to observe on MainThread because delay works on background thread to avoid UI blocking.
            ?.observeOn(AndroidSchedulers.mainThread())
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(getObserver())
    }

    fun doSomeWork2(){
        getObservable()
            //Delay item emission by one second
            .zipWith(Observable.interval(0, 1, TimeUnit.SECONDS),
                BiFunction<String, Long, String> { s, aLong -> s })
            //Take the items until the condition is met.
            //.takeWhile(Predicate<String> { s -> !s.toLowerCase().contains("honey") })
            .takeWhile { s -> !s.toLowerCase().contains("honey") }
            //We need to observe on MainThread because delay works on background thread to avoid UI blocking.
            .observeOn(AndroidSchedulers.mainThread())
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(getObserver())
    }

    fun getObservable() : Observable<String>{
        return Observable.just("Alpha", "Beta", "Cupcake", "Doughnut", "Eclair", "Froyo", "GingerBread",
            "Honeycomb", "Ice cream sandwich");
    }
    fun  getObserver() : Observer<String>{
        return object: Observer<String>{
            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {
                textView.append(" onNext : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onNext : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

        }
    }
}