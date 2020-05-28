package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ReplaySubjectExampleActivity : AppCompatActivity() {
    var TAG : String = ReplaySubjectExampleActivity::class.java.simpleName
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
        textFunctions?.text = "ReplaySubject.create with onNext &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }

    /* ReplaySubject emits to any observer all of the items that were emitted
    * by the source Observable, regardless of when the observer subscribes.
    */

    fun doSomeWork(){
        val source :  ReplaySubject<Int>  = ReplaySubject.create<Int>()

        source.subscribe(getFirstObserver()) // it will get 1, 2, 3, 4

        source.onNext(1)
        source.onNext(2)
        source.onNext(3)
        source.onNext(4)
        source.onComplete()

        /*
         * it will emit 1, 2, 3, 4 for second observer also as we have used replay
         */
        source.subscribe(getSecondObserver())
    }

    fun getFirstObserver() : Observer<Int>{
        return object: Observer<Int>{
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


    fun getSecondObserver() : Observer<Int>{
        return object: Observer<Int>{
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