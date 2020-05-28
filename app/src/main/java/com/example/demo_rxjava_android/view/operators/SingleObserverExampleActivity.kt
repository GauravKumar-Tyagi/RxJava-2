package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SingleObserverExampleActivity : AppCompatActivity() {
    var TAG : String = SingleObserverExampleActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)
        btn?.setOnClickListener {
            doSomeWork1()
            //doSomeWork2()
        }
        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }
        textFunctions?.text = "Single.just  &  SingleObserver with : "+"onSubscribe , "+"onSuccess , "+"onError  "+"  "
    }
    /*****************************************************************************************************************/
    /*
     * simple example using SingleObserver
     */
    private fun doSomeWork1() {
        getObservable1()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getSingleObserver1())
    }




    fun getObservable1() : Single<String> {
        //return Observable.just("item 1","item 2") // You can put multiple item in case of Observable
        return Single.just("item 1") // You can put only 1 item in case of Single
    }



    fun getSingleObserver1() : SingleObserver<String>{

        return object : SingleObserver<String> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: String) {
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
    /*****************************************************************************************************************/
    /*****************************************************************************************************************/
    private fun doSomeWork2() {
        getObservable2()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getSingleObserver2())
    }
    fun getObservable2() : Single<List<String>> {
        var list : List<String> = listOf("item 1","item 2","item 3","item 4")
        return Single.just(list)
    }
    fun getSingleObserver2() : SingleObserver<List<String>>{

        return object : SingleObserver<List<String>> {

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: List<String>) {
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
    /*****************************************************************************************************************/
}