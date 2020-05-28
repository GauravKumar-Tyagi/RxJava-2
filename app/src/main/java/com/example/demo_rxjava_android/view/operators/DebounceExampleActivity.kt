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

class DebounceExampleActivity  : AppCompatActivity() {
    var TAG : String = DebounceExampleActivity::class.java.simpleName
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
        textFunctions?.text = "PublishSubject.create with onNext &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }


    /*
    * Using debounce() -> only emit an item from an Observable if a particular time-span has
    * passed without it emitting another item, so it will emit 2, 4, 5 as we have simulated it.
    */
    /*
    *only emit an item from an Observable if a particular timespan has passed without it emitting another item
     */

    /**
     * The Debounce operator filters out items emitted by the source Observable that are rapidly followed by another emitted item.
     */
    /**
     * Last item that is before onComplete will always print for debounce
     */
    fun doSomeWork(){
        getObservable()
            ?.debounce(500, TimeUnit.MILLISECONDS)
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver())
    }

    fun getObservable() : Observable<Int> {
            /*return Observable.create(object : ObservableOnSubscribe<Int>{
                override fun subscribe(emitter: ObservableEmitter<Int>) {
                    // send events with simulated time wait
                    emitter.onNext(1) // skip
                    Thread.sleep(400)
                    emitter.onNext(2) // deliver
                    Thread.sleep(505)
                    emitter.onNext(3) // skip
                    Thread.sleep(100)
                    emitter.onNext(4) // deliver
                    Thread.sleep(605)
                    emitter.onNext(5) // deliver
                    Thread.sleep(110)
                    //Thread.sleep(610);
                    emitter.onComplete()
                }
            })*/

        return Observable.create { emitter ->
           // send events with simulated time wait
           emitter.onNext(1) // skip
           Thread.sleep(400)
           emitter.onNext(2) // deliver
           Thread.sleep(505)
           emitter.onNext(3) // skip
           Thread.sleep(100)
           emitter.onNext(4) // deliver
           Thread.sleep(605)
           emitter.onNext(5) // deliver
           Thread.sleep(110)
           //Thread.sleep(610);
           emitter.onComplete()
        }
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
                textView.append(" onNext : ")
                textView.append(LINE_SEPARATOR)
                textView.append(" value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onNext ")
                Log.d(TAG, " value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

        }
    }
}