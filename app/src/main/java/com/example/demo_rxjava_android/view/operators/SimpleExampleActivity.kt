package com.example.demo_rxjava_android.view.operators


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demo_rxjava_android.R

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import android.content.Intent

import android.net.Uri


class SimpleExampleActivity : AppCompatActivity() {
    var TAG : String = SimpleExampleActivity::class.java.simpleName
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


        textFunctions?.text = "Observable.just  &  Observer with : "+"onSubscribe , "+"onNext , "+"onError , "+"onComplete  "

    }
    /*
    * simple example to emit two value one by one
    */
    private fun doSomeWork() {
        getObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver())
    }

    /*
    Observable is probably most used observable among all. Observable can emit one or more items.
    In the below example, we have an Observable that emits Note items one by one. We can also emit list of Notes at once.
        // emitting single Note
        Observable<Note>
        // emitting list of notes at once, but in this case considering Single Observable is best option
        Observable<List<Note>>
    */

    fun getObservable() : Observable<String> {
        return Observable.just("item 1","item 2")
    }

    fun getObserver() : Observer<String>{

        return object : Observer<String>{


            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed) // onSubscribe : false ,  Returns true if this resource has been disposed.
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

            override fun onComplete() {
                textView.append(" onComplete")
                textView.append(LINE_SEPARATOR)
                Log.d(TAG, " onComplete")
            }

        }
    }
}
