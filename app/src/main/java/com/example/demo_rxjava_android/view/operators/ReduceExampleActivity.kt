package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ReduceExampleActivity : AppCompatActivity() {
    var TAG : String = FlowableExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.just with reduce &   MaybeObserver with : "+"onSubscribe , "+"onSuccess , "+"onError  ,  "+" onComplete "
    }
    /*****************************************************************************************************************/
    /*
     * simple example using reduce to add all the number
     */
    private fun doSomeWork() {
        getObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
           /* ?.reduce(object : BiFunction<Int,Int,Int>{
                override fun apply(t1: Int, t2: Int): Int {
                    return t1+t2
                }

            })*/
            ?.reduce { t1: Int, t2:Int ->
                t1+t2
            }
            ?.subscribe(getObserver())
    }
    fun getObservable() : Observable<Int>{
        return Observable.just(1,2,3,4)
    }
    fun getObserver() : MaybeObserver<Int>{
        return object :  MaybeObserver<Int>{
            override fun onSuccess(value: Int) {
                textView.append(" onSuccess : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onSuccess : value : $value")
            }

            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

        }
    }
}