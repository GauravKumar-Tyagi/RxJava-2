package com.example.demo_rxjava_android.view.rxBus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxbus.*


class RxBusActivity : AppCompatActivity(){
    var TAG : String = RxBusActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxbus)

        btn?.setOnClickListener {
            doSomeWork()
        }


        postMessage()
    }

    private fun postMessage() {

        var d1 : Disposable ?= (application as MyApplication)?.bus()?.toObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe{
                if(it is Events.AutoEvent){
                    textView.setText("Auto Event Received");
                }else if(it is Events.TapEvent){
                    textView.setText("Tap Event Received");
                }
            }


       /* var d2 : Disposable ?=(application as MyApplication)?.bus()?.toObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Consumer<Any> {
                override fun accept(t: Any?) {
                    if(t is Events.AutoEvent){
                        textView.setText("Auto Event Received");
                    }else if(t is Events.TapEvent){
                        textView.setText("Tap Event Received");
                    }
                }

            })*/


        d1?.let {
           disposable?.add(it)
        }
    }

    fun doSomeWork(){
        (application as MyApplication)?.bus()?.send( Events.TapEvent())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear() // do not send event after activity has been destroyed
    }
}