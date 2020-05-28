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

class BufferExampleActivity : AppCompatActivity() {
    var TAG : String = BufferExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.just with buffer &   Observer with : "+"onSubscribe , "+"onNext , "+"onError  ,  "+" onComplete "
    }
    /*****************************************************************************************************************/
    /*
      * simple example using buffer operator - bundles all emitted values into a list
      */
    private fun doSomeWork() {

        val buffered :  Observable<List<String>>  = getObservable().buffer(3, 1)

        // 3 means,  it takes max of three from its start index and create list
        // 1 means, it jumps one step every time
        // so the it gives the following list
        // 1 - one, two, three
        // 2 - two, three, four
        // 3 - three, four, five
        // 4 - four, five
        // 5 - five


        buffered
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver())
    }

    fun getObservable() : Observable<String>{
        return Observable.just("one", "two", "three", "four", "five");
    }

    fun getObserver() : Observer<List<String>>{
        return object : Observer<List<String>>{
            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(stringList: List<String>) {
                textView.append(" onNext size : " + stringList.size)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onNext : size :" + stringList.size)
                for (value in stringList) {
                    textView.append(" value : $value")
                    textView.append(LINE_SEPARATOR)
                    Log.d(TAG, " : value :$value")
                }
            }

            override fun onError(e: Throwable) {
                textView.append(" onError : " + e.message)
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onError : " + e.message)
            }

        }
    }
}