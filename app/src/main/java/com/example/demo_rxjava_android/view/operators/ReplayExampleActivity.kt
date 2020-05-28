package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ReplayExampleActivity  : AppCompatActivity() {
    var TAG : String = ReplayExampleActivity::class.java.simpleName
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
        textFunctions?.text = "PublishSubject.create with replay &   2 Observers with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }
    /* Using replay operator, replay ensure that all observers see the same sequence
    * of emitted items, even if they subscribe after the Observable has begun emitting items
    */
    fun doSomeWork(){
        val source : PublishSubject<Int> = PublishSubject.create<Int>()
        /*
        * A  ConnectableObservable resembles an ordinary  Observable, except that it does not begin
        * emitting items when it is subscribed to, but only when its connect method is called. In this way you
        * can wait for all intended Observers to {@link Observable#subscribe} to the {@code Observable}
        * before the {@code Observable} begins emitting items.
       */
        val connectableObservable : ConnectableObservable<Int> = source.replay(3) // bufferSize = 3 to retain 3 values to replay


        connectableObservable.connect() // connecting the connectableObservable



        /*
         * it will emit 1, 2, 3, 4
         */
        connectableObservable.subscribe(getFirstObserver())

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)
        source.onNext(4)
        source.onComplete()

        /*
         * it will emit 2, 3, 4 as (count = 3), retains the 3 values for replay
         */
        connectableObservable.subscribe(getSecondObserver())
    }

    fun  getFirstObserver() : Observer<Int> {
        return object : Observer<Int>{
            override fun onComplete() {
                textView.append(" First onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " First onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                textView.append(" First onNext : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " First onNext value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" First onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " First onError : " + e.message)
            }

        }
    }

    fun  getSecondObserver() : Observer<Int> {
        return object : Observer<Int>{
            override fun onComplete() {
                textView.append(" Second onComplete")
                Log.d(TAG, " Second onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed)
                Log.d(TAG, " Second onSubscribe : " + d.isDisposed)
                textView.append(LINE_SEPARATOR)
            }

            override fun onNext(value: Int) {
                textView.append(" Second onNext : value : $value")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " Second onNext value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append(" Second onError : " + e.message)
                Log.d(TAG, " Second onError : " + e.message)
            }

        }
    }
}