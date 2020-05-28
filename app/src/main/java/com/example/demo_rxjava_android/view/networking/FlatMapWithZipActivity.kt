package com.example.demo_rxjava_android.view.networking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Pair
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.helper.model.User
import com.example.demo_rxjava_android.helper.model.UserDetail
import com.example.demo_rxjava_android.network.ApiClient
import com.example.demo_rxjava_android.network.ApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FlatMapWithZipActivity : AppCompatActivity() {
    var TAG: String = FlatMapWithZipActivity::class.java.simpleName
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
            "  Observable<List<User>> with flatMap  to return users one by one then again flatMap with ZIP to get \"user details and user\" one by one  &   Observer<List<User>> with : " + " onSubscribe ,  " + "onNext , " + "onError , " + "onComplete  "

    }


    fun doSomeWork(){
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
            /*?.flatMap(object : Function< User, ObservableSource<Pair<UserDetail, User>>>{
                override fun apply(user: User): ObservableSource<Pair<UserDetail, User>> {
                    // here we get the user one by one and then we are zipping
                    // two observable - one getUserDetailObservable (network call to get userDetail)
                    // and another Observable.just(user) - just to emit user
                    return Observable.zip(getUserDetailObservable(user.id),Observable.just(user),
                        object : BiFunction< UserDetail, User, Pair<UserDetail, User> >{
                            override fun apply(userDetail: UserDetail, user: User): Pair<UserDetail, User> {
                                // runs when network call completes
                                // we get here userDetail for the corresponding user
                                return Pair(userDetail, user) // returning the pair(userDetail, user)
                            }
                        })
                }

            })*/
            ?.flatMap {
                // here we get the user one by one and then we are zipping
                // two observable - one getUserDetailObservable (network call to get userDetail)
                // and another Observable.just(user) - just to emit user
                Observable.zip(getUserDetailObservable(it.id),Observable.just(it),
                    BiFunction< UserDetail, User, Pair<UserDetail, User> > { userDetail, user ->
                        // runs when network call completes
                        // we get here userDetail for the corresponding user
                        Pair(userDetail, user)  // returning the pair(userDetail, user)
                        /*
                        *Pair :
                        * Container to ease passing around a tuple of two objects. This object provides a sensible
                        * implementation of equals(), returning true if equals() is true on each of the contained
                        * objects.
                         */
                    }
                )
            }
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object :  Observer<Pair<UserDetail, User>> {
                override fun onComplete() {
                    Log.d(TAG, "onComplete")

                    textView.append("onComplete")
                    textView.append(LINE_SEPARATOR)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG,"onSubscribe : " + d.isDisposed) // onSubscribe : false ,  Returns true if this resource has been disposed.
                }

                override fun onNext( pair : Pair<UserDetail, User>) {
                    // here we are getting the userDetail for the corresponding user one by one
                    val userDetail : UserDetail = pair.first
                    val user : User = pair.second
                    Log.d(TAG, "user : " + user.toString())
                    Log.d(TAG, "userDetail : " + userDetail.toString())

                    // here we are getting the userDetail for the corresponding user one by one

                    textView.append("onNext : value : ")
                    textView.append(LINE_SEPARATOR)

                    textView.append("user :  $user")
                    textView.append(LINE_SEPARATOR)
                    textView.append("userDetail :  $userDetail")
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

    fun getUserDetailObservable(id:Long) :  Observable<UserDetail>? {
        return apiService?.getAnUserDetailAPI(id.toString())
    }
}