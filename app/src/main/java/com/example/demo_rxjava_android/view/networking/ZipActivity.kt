package com.example.demo_rxjava_android.view.networking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.helper.Utils.filterUserWhoLovesBoth
import com.example.demo_rxjava_android.helper.model.User
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


class ZipActivity : AppCompatActivity(){
    var TAG : String = ZipActivity::class.java.simpleName
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

        textFunctions?.text = "  Observable<List<User>> with zip to get users who love both  &   Observer<List<User>> with : "+" onSubscribe ,  "+"onNext , "+"onError , "+"onComplete  "

    }

    // findUsersWhoLovesBoth
    fun  doSomeWork(){
        findUsersWhoLovesBoth()
    }

   /*
   * This do the complete magic, make both network call
   * and then returns the list of user who loves both
   * Using zip operator to get both response at a time
   */
    private fun findUsersWhoLovesBoth() {
       // here we are using zip operator to combine both request
      /* Observable.zip(getCricketFansObservable(),getFootballFansObservable(),object :  BiFunction<List<User>, List<User>, List<User>> {
           override fun apply(cricketFans: List<User>, footballFans: List<User>): List<User> {
                var userWhoLovesBoth : List<User> = filterUserWhoLovesBoth(cricketFans, footballFans)
               return userWhoLovesBoth
           }
       })*/
       // here we are using zip operator to combine both request
       Observable.zip(getCricketFansObservable(),getFootballFansObservable(), BiFunction<List<User>, List<User>, List<User>> { cricketFans, footballFans ->
               var userWhoLovesBoth : List<User> = filterUserWhoLovesBoth(cricketFans, footballFans)
               userWhoLovesBoth
        })

       // Run on a background thread
       ?.subscribeOn(Schedulers.io())
       // Be notified on the main thread
       ?.observeOn(AndroidSchedulers.mainThread())
           ?.subscribe(object : Observer<List<User>>{
               override fun onComplete() {
                   Log.d(TAG, "onComplete")

                   textView.append("onComplete")
                   textView.append(LINE_SEPARATOR)
               }

               override fun onSubscribe(d: Disposable) {
                   Log.d(TAG, "onSubscribe : " + d.isDisposed) // onSubscribe : false ,  Returns true if this resource has been disposed.
               }

               override fun onNext(users: List<User>) {
                   // do anything with user who loves both
                   Log.d(TAG, "userList size : " + users.size)

                   textView.append("onNext : value : ")
                   textView.append(LINE_SEPARATOR)
                   for (user in users) {
                       Log.d(TAG, "user : " + user.toString())

                       textView.append(""+user.toString())
                       textView.append(LINE_SEPARATOR)
                   }
               }

               override fun onError(e: Throwable) {
                   Log.d(TAG, "onError : "+e.message)

                   textView.append("onError : " + e.message)
                   textView.append(LINE_SEPARATOR)

               }

           })

    }



    /**
     * This observable return the list of User who loves cricket
     */
    fun getCricketFansObservable() : Observable<List<User>>? {
        return  apiService?.getAllCricketFansAPI()
    }
    /*
    * This observable return the list of User who loves Football
    */
    fun getFootballFansObservable() : Observable<List<User>>?{
        return  apiService?.getAllFootballFansAPI()
    }
}