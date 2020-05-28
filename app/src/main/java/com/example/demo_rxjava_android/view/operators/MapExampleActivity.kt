package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.helper.Utils
import com.example.demo_rxjava_android.helper.model.ApiUser
import com.example.demo_rxjava_android.helper.model.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_example.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class MapExampleActivity : AppCompatActivity() {
    var TAG : String = MapExampleActivity::class.java.simpleName
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
        textFunctions?.text = "Observable.create with map  &  Observer with : "+"onSubscribe , "+"onNext , "+"onError , "+"onComplete  "
    }


        /*
        * Here we are getting ApiUser Object from api server
        * then we are converting it into User Object because
        * may be our database support User Not ApiUser Object
        * Here we are using Map Operator to do that
        */
    private fun doSomeWork() {
        getObservable()
            // Run on a background thread
            ?.subscribeOn(Schedulers.io())
            // Be notified on the main thread
            ?.observeOn(AndroidSchedulers.mainThread())
            /*********************************************************************************/
           /* ?.map(object : Function<  List<ApiUser>,List<User>  >{
                override fun apply(t: List<ApiUser>): List<User> {
                    return Utils.convertApiUserListToUserList(t)
                }

            })*/
            /*?.map(Function<  List<ApiUser>,List<User>  >{
                 Utils.convertApiUserListToUserList(it)
            })*/
            ?.map {
                Utils.convertApiUserListToUserList(it)
            }
            /*********************************************************************************/
            ?.subscribe(getObserver())
        /*
       * Here we are getting ApiUser Object from api server
       * then we are converting it into User Object because
       * may be our database support User Not ApiUser Object
       * Here we are using Map Operator to do that
       */
    }


    /*
    Observable is probably most used observable among all. Observable can emit one or more items.
    In the below example, we have an Observable that emits Note items one by one. We can also emit list of Notes at once.
        // emitting single Note
        Observable<Note>
        // emitting list of notes at once, but in this case considering Single Observable is best option
        Observable<List<Note>>
    */
    fun getObservable() : Observable<List<ApiUser>>{
        return Observable.create {
            if(!it?.isDisposed){
                it?.onNext(Utils.apiUserList)
                it?.onComplete()
            }
        }
    }

    fun getObserver() : Observer<List<User>>{

        return object: Observer<List<User>>{

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