package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PublishSubjectExampleActivity  : AppCompatActivity() {
    var TAG : String = PublishSubjectExampleActivity::class.java.simpleName
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
    /* PublishSubject emits to an observer only those items that are emitted
    * by the source Observable, subsequent to the time of the subscription.
    */
    fun doSomeWork(){

        val source : PublishSubject<Int> = PublishSubject.create<Int>()

        source.subscribe(getFirstObserver()) // it will get 1, 2, 3, 4 and onComplete

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver())

        source.onNext(4)
        source.onComplete()
    }


    fun getFirstObserver() : Observer<Int> {
        return object: Observer<Int> {
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


    fun getSecondObserver() : Observer<Int> {
        return object: Observer<Int> {
            override fun onComplete() {
                textView.append(" Second onComplete")
                textView.append(LINE_SEPARATOR)
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
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " Second onError : " + e.message)
            }

        }
    }
}