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
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MergeExampleActivity : AppCompatActivity() {
    var TAG : String = MergeExampleActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)
        btn?.setOnClickListener {
            doSomeWork()
        }
        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }
        textFunctions?.text = "Observable.fromArray with Observable.merge &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }

    /*
     * Using merge operator to combine Observable : merge does not maintain
     * the order of Observable.
     * It will emit all the 7 values may not be in order
     * Ex - "A1", "B1", "A2", "A3", "A4", "B2", "B3" - may be anything
     */
    fun doSomeWork(){
        Observable.merge(getObservable1(),getObservable2())
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver())
    }

    fun getObservable1() : Observable<String> {
        val fromArray : Observable<String> = Observable.fromArray("A1", "A2", "A3", "A4")
        return fromArray
    }

    fun getObservable2() : Observable<String> {
        val fromArray : Observable<String> = Observable.fromArray("B1", "B2", "B3")
        return fromArray
    }

    fun getObserver() : Observer<String>{
        return object : Observer<String>{
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