package com.example.demo_rxjava_android.view.networking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.helper.model.User
import com.example.demo_rxjava_android.network.ApiClient
import com.example.demo_rxjava_android.network.ApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class TakeActivity : AppCompatActivity() {
    var TAG: String = TakeActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    private var apiService: ApiService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_example)

        /********************/
        apiService = ApiClient.getClient(applicationContext)?.create(ApiService::class.java)
        /*******************/

        btn?.setOnClickListener {
            doSomeWork()
        }

        help?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://reactivex.io/documentation/operators.html")))
        }

        textFunctions?.text =
            "  Observable<List<User>> with flatMap  to return users one by one then take(4)  will only emit first 4 users out of all  &   Observer<List<User>> with : " + " onSubscribe ,  " + "onNext , " + "onError , " + "onComplete  "

    }

    fun doSomeWork() {
        getUserListObservable() // flatMap - to return users one by one
            /*
                    ?.flatMap(object: Function< List<User>,ObservableSource<User> >{  // flatMap - to return users one by one
                    override fun apply(usersList: List<User>): ObservableSource<User> {
                        var item : Observable<User> = Observable.fromIterable(usersList) // returning user one by one from usersList.
                        return item
                        }
                    })
            */
            ?.flatMap {
                Observable.fromIterable(it) // returning user one by one from usersList.
            }
            ?.take(4) // it will only emit first 4 users out of all
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<User> {
                override fun onComplete() {
                    Log.d(TAG, "onComplete")

                    textView.append("onComplete")
                    textView.append(LINE_SEPARATOR)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG,"onSubscribe : " + d.isDisposed) // onSubscribe : false ,  Returns true if this resource has been disposed.
                }

                override fun onNext(user: User) {
                    // // only four user comes here one by one
                    Log.d(TAG, "user : $user")

                    textView.append("onNext : value : ")
                    textView.append(LINE_SEPARATOR)

                    textView.append("user :  $user")
                    textView.append(LINE_SEPARATOR)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError : " + e.message)

                    textView.append("onError : " + e.message)
                    textView.append(LINE_SEPARATOR)

                }

            })
    }


    fun getUserListObservable(): Observable<List<User>>? {
        return apiService?.getAllUsersAPI("0", "10")
    }

}