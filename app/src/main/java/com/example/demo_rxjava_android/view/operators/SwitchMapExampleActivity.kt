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
import java.util.*
import java.util.concurrent.TimeUnit

class SwitchMapExampleActivity : AppCompatActivity() {
    var TAG : String = SwitchMapExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.just with switchMap &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }
    /* whenever a new item is emitted by the source Observable, it will unsubscribe to and stop
    * mirroring the Observable that was generated from the previously-emitted item,
    * and begin only mirroring the current one.
    *
    * Result: 5x
    */
    fun doSomeWork(){
        getObservable()
            /*?.switchMap(object : Function< Int, ObservableSource<String>>{
                override fun apply(item: Int): ObservableSource<String> {
                    val delay : Int = Random().nextInt(2)
                    return Observable.just<String>(item.toString() + "x")
                        .delay(delay.toLong(), TimeUnit.SECONDS, Schedulers.io())
                }

            })*/
            ?.switchMap{
                val delay : Int = Random().nextInt(2)
                Observable.just<String>(it.toString() + "x")
                    .delay(delay.toLong(), TimeUnit.SECONDS, Schedulers.io())
            }
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver())
    }


    fun getObservable() : Observable<Int>{
        return Observable.just(1, 2, 3, 4, 5)
    }
    fun  getObserver() : Observer<String> {
        return object: Observer<String> {
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