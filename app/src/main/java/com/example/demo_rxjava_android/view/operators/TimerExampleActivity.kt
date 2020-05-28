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
import java.util.concurrent.TimeUnit

class TimerExampleActivity  : AppCompatActivity() {
    var TAG : String = TimerExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.timer  &  Observer with : "+"onSubscribe , "+"onNext , "+"onError , "+"onComplete  "
    }

    /*
    * simple example using timer to do something after 2 second
    */
    private fun doSomeWork() {
        getObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver());
    }

    /*private fun getObservable(): Observable<out Long> {
        return Observable.timer(2, TimeUnit.SECONDS)
    }*/

    private fun getObservable(): Observable<Long> {
        return Observable.timer(2, TimeUnit.SECONDS)
    }



    fun getObserver() : Observer<Long>{
        return object :  Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Long) {
                textView.append(" onNext : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onNext : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }
            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }

        }
    }
}