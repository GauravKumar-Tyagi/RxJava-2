package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
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

class DisposableExampleActivity : AppCompatActivity() {

    var TAG : String = DisposableExampleActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    private val disposables = CompositeDisposable()  // MOST IMP


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)
        btn?.setOnClickListener {
            doSomeWork()
        }
        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }
        textFunctions?.text = "Observable.defer with Observable.just  &  DisposableObserver with : "+""+"onNext , "+"onError , "+"onComplete  "
    }
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // do not send event after activity has been destroyed
        /*
        Comment the disposables.clear() and run the app
        click on button and go back from the activity
        See the log message.
        You will get the data after destroy the app if you will comment the above lone.
         */
    }

    /*
        * Example to understand how to use disposables.
        * disposables is cleared in onDestroy of this activity.
    */
    fun doSomeWork(){
        sampleObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableObserver<String>(){

                override fun onNext(value: String) {
                    textView.append(" onNext : value : $value")
                    textView.append(LINE_SEPARATOR)
                    Log.d(TAG, " onNext value : $value")
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

            })
            ?.let {
                disposables?.add(
                    it
                )
            }
    }

    fun sampleObservable() : Observable<String> {
        return Observable.defer {
            // Do some long running operation
            SystemClock.sleep(9000)
            return@defer Observable.just("one", "two", "three", "four", "five")
        }
    }
}