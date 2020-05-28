package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FlowableExampleActivity : AppCompatActivity() {
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
        textFunctions?.text = "Flowable.just with reduce &  SingleObserver with : "+"onSubscribe , "+"onSuccess , "+"onError  "+"  "
    }
    /*****************************************************************************************************************/
    /*
     * simple example using Flowable
     */
    private fun doSomeWork() {
        getObservable1()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            /*********************************************************************************/
            /*?.reduce(50, object: BiFunction<Int,Int,Int>{
                override fun apply(t1: Int, t2: Int): Int {
                    return t1+t2
                }
            }) */
           /* ?.reduce(50, BiFunction { t1, t2 -> t1+t2  })*/
           /* ?.reduce(50,  { t1 : Int, t2 : Int -> t1+t2  })*/
            ?.reduce(50,  { t1 , t2  -> t1+t2  })
            /*********************************************************************************/
            ?.subscribe(getObserver())
    }
    fun getObservable1() : Flowable<Int> {
        return Flowable.just(1,2,3,4,5)
        // OR
        //val observable : Flowable<Int> = Flowable.just(1, 2, 3, 4)
        //return observable
    }

    fun getObservable2() : Observable<Int> {
        return Observable.just(1,2,3,4,5)
    }


    fun getObserver() : SingleObserver<Int> {

        return object : SingleObserver<Int>{

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: Int) {
                textView.append(" onSuccess : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onSuccess : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

        }
    }
}