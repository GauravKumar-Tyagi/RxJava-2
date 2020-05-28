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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MapActivity : AppCompatActivity(){
    var TAG : String = MapActivity::class.java.simpleName
    var LINE_SEPARATOR = "\n"

    private var apiService: ApiService? = null
    private val disposable = CompositeDisposable()

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


        textFunctions?.text = " Observable<ApiUser> with map to transform into <User>  &  DisposableObserver with : "+" "+"onNext , "+"onError , "+"onComplete  "

    }
    /**
     * Map Operator Example to transformation of the Object
     */
    private fun doSomeWork() {

        var d : Disposable?= apiService?.getAnUserAPI("1")
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            /*?.map(object: Function<ApiUser,User >{
                override fun apply(apiUser: ApiUser): User {
                    // here we get ApiUser from server
                    // then by converting, we are returning user
                    return User(apiUser)
                }
            })*/
            ?.map {
                // here we get ApiUser from server
                // then by converting, we are returning user
                User(it)
            }
            ?.subscribeWith(object : DisposableObserver<User>() {
                /*
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed) // onSubscribe : false ,  Returns true if this resource has been disposed.
                    }
                */
                override fun onComplete() {
                    Log.d(TAG, "onComplete")

                    textView.append("onComplete")
                    textView.append(LINE_SEPARATOR)

                }

                override fun onNext(user: User) {
                    Log.d(TAG, "user : " + user.toString())

                    textView.append("onNext : value : ")
                    textView.append(LINE_SEPARATOR)
                    textView.append(""+user.toString())
                    textView.append(LINE_SEPARATOR)

                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError : "+e.message)

                    textView.append("onError : " + e.message)
                    textView.append(LINE_SEPARATOR)

                }

            })

        d?.let {
            disposable?.add(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.clear()
    }
}