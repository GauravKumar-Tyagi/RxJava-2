package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.helper.Utils
import com.example.demo_rxjava_android.helper.model.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class ZipExampleActivity  : AppCompatActivity() {
    var TAG : String = ZipExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.create with zip  &  Observer with : "+"onSubscribe , "+"onNext , "+"onError , "+"onComplete  "
    }

    /*
   * Here we are getting two user list
   * One, the list of cricket fans
   * Another one, the list of football fans
   * Then we are finding the list of users who loves both
   */
    private fun doSomeWork() {
        /*********************************************************************************/
        /*Observable.zip(getCricketFansObservable(),getFootballFansObservable(),
             object : BiFunction<List<User>, List<User>, List<User>>{
                 override fun apply(cricketFans: List<User>, footballFans: List<User>): List<User> {
                     return Utils.filterUserWhoLovesBoth(cricketFans, footballFans)
                 }
             })*/
        Observable.zip(getCricketFansObservable(),getFootballFansObservable(),
           BiFunction { cricketFans : List<User> , footballFans : List<User> ->
                 Utils.filterUserWhoLovesBoth(cricketFans, footballFans)
           })
            /*********************************************************************************/
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(getObserver());
    }


    fun getCricketFansObservable() : Observable<List<User>>? {
        return Observable.create {
            if (!it.isDisposed()) {
                it.onNext(Utils.userListWhoLovesCricket)
                it.onComplete()
            }
        }

    }

    fun getFootballFansObservable() : Observable<List<User>> {
        return Observable.create{
            if(!it.isDisposed()){
                it.onNext(Utils.userListWhoLovesFootball)
            }
        }
    }

    fun getObserver() : Observer<List<User>>{
        return  object : Observer<List<User>>{


            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed)
            }

            override fun onNext(userList: List<User>) {
                textView.append(" onNext")
                textView.append(LINE_SEPARATOR)
                for (user in userList) {
                    textView.append(" firstname : " + user.firstname)
                    textView.append(LINE_SEPARATOR)
                }
                Log.d(TAG, " onNext : " + userList.size)
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