package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.concurrent.TimeUnit


class IntervalExampleActivity : AppCompatActivity() {
    var TAG : String = IntervalExampleActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"
    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // clearing it : do not emit after destroy
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)
        btn?.setOnClickListener {
            doSomeWork()
        }
        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }
        textFunctions?.text = "Observable.interval  &   DisposableObserver with : "+" "+"onNext , "+"onError  ,  "+" onComplete "
    }
    /*
    * simple example using interval to run task at an interval of 2 sec
    * which start immediately
    */
    fun doSomeWork(){
        disposables.add(
            getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    fun getObservable() : Observable<Long> {
        return Observable.interval(0, 2, TimeUnit.SECONDS);
    }

    fun getObserver() : DisposableObserver<Long>{
        return object: DisposableObserver<Long>(){
            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
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

        }
    }
}