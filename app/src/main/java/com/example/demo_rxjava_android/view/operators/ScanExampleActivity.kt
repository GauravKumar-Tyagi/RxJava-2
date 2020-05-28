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


class ScanExampleActivity : AppCompatActivity() {
    var TAG : String = ScanExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.just with scan &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }

    /*
    * Using scan operator, it sends also the previous result
    */
    fun doSomeWork(){
            getObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            /*********************************************************************************/
            /*?.scan(object : BiFunction<Int, Int, Int> {
                   override fun apply(t: Int, u: Int): Int {
                        return t+u
                   }
            })*/
            /*?.scan { t1: Int, t2: Int -> t1+t2 }*/
            ?.scan { t1 , t2 -> t1+t2 }
            /*********************************************************************************/
            ?.subscribe(getObserver())
    }

    fun getObservable() : Observable<Int>{
        return Observable.just(1, 2, 3, 4, 5)
    }
    fun getObserver() : Observer<Int>{
            return object: Observer<Int>{
                override fun onComplete() {
                    textView.append(" onComplete")
                    textView.append(LINE_SEPARATOR)
                    Log.d(TAG, " onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, " onSubscribe : " + d.isDisposed)
                }

                override fun onNext(value: Int) {
                    textView.append(" onNext : value : $value")
                    textView.append(LINE_SEPARATOR)
                    Log.d(TAG, " onNext value : $value")
                }

                override fun onError(e: Throwable) {
                    textView.append(" onError : " + e.message)
                    textView.append(LINE_SEPARATOR)
                    Log.d(TAG, " onError : " + e.message)
                }

            }
    }
}