package com.example.demo_rxjava_android.view.pagination

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_rxjava_android.R
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pagination.*
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/*
* Most Imp:
* PublishProcessor is subclassed from Flowables so you can use a BackPressure Strategy when you make use of them.
* PublishSubject's superclass is Observable so at very least there is no BackPressure Strategy.
*/
class PaginationActivity : AppCompatActivity() {

    val TAG = PaginationActivity::class.java.simpleName
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val paginator : PublishProcessor<Int> = PublishProcessor.create<Int>()

    private var layoutManager : LinearLayoutManager ?= null
    private var paginationAdapter: PaginationAdapter? = null

    private var loading : Boolean = false
    private var pageNumber : Int = 1
    private val VISIBLE_THRESHOLD : Int = 1
    private var lastVisibleItem: Int = 0
    private var totalItemCount:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        recyclerView?.layoutManager = layoutManager
        paginationAdapter =  PaginationAdapter()
        recyclerView?.adapter= paginationAdapter

        setUpLoadMoreListener()
        subscribeForData2()
        //subscribeForData1()
    }


    /**
     * setting listener to get callback for load more
     */
    private fun setUpLoadMoreListener() {
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(layoutManager?.getItemCount() != null && layoutManager?.findLastVisibleItemPosition() != null){
                    layoutManager?.getItemCount()?.let {
                        totalItemCount = it // totalItemCount = layoutManager.getItemCount();
                    }
                    layoutManager?.findLastVisibleItemPosition()?.let {
                        lastVisibleItem = it // layoutManager.findLastVisibleItemPosition();
                    }
                    if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        pageNumber++
                        paginator.onNext(pageNumber)
                        loading = true
                    }
                }
            }
        })
    }






    /**
     * subscribing for data
     */
    private fun subscribeForData2() {

     /*  var disposable : Disposable =  paginator
            .onBackpressureDrop()
            .doOnNext(object : Consumer<Int>{
                override fun accept(page: Int?) {
                    loading = true;
                    progressBar.setVisibility(View.VISIBLE);
                }
            })
            .concatMapSingle(object : Function< Int, SingleSource<List<String>>>{
                override fun apply(page: Int): SingleSource<List<String>> {
                    return dataFromNetwork2(page).subscribeOn(Schedulers.io())
                        .doOnError{throwable ->
                            // handle error
                        }
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<String>>{
                override fun accept(items: List<String>?) {
                    if(items != null){
                        paginationAdapter?.addItems(items);
                    }
                    paginationAdapter?.notifyDataSetChanged();
                    loading = false;
                    progressBar.setVisibility(View.INVISIBLE);

                }

            })
        compositeDisposable.add(disposable);

        paginator.onNext(pageNumber);*/


        var disposable : Disposable =  paginator
            .onBackpressureDrop()
            .doOnNext{page : Int ->
                loading = true;
                progressBar.setVisibility(View.VISIBLE);
            }
            .concatMapSingle{page ->
                dataFromNetwork2(page)
                    .subscribeOn(Schedulers.io())
                    .doOnError{throwable ->
                        // handle error
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{items ->
                if(items != null){
                    paginationAdapter?.addItems(items);
                }
                paginationAdapter?.notifyDataSetChanged();
                loading = false;
                progressBar.setVisibility(View.INVISIBLE);
            }
        compositeDisposable.add(disposable);
        paginator.onNext(pageNumber);

    }

    /**
     * Simulation of network data
     */
    fun dataFromNetwork2(page : Int) : Single<List<String>> {

        return Single.just(true)
            .delay(2,TimeUnit.SECONDS)
            /*.map(object : Function< Boolean,List<String> >{
                override fun apply(value: Boolean): List<String> {
                    var items : ArrayList<String> =  ArrayList<String>();
                    for (i in 1..10)  {
                        items.add("Item " + (page * 10 + i));
                    }
                    return items;
                }
            })*/
            .map{
                    var items : ArrayList<String> =  ArrayList<String>();
                    for (i in 1..10)  {
                        items.add("Item " + (page * 10 + i));
                    }
                    items;
            }



    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    /***********************************************************************************************************************/
    /***********************************************************************************************************************/

    /**
     * subscribing for data
     */
    private fun subscribeForData1() {
        /**************************************************************************/
        /* var disposable1 : Disposable ?= paginator?.onBackpressureDrop()
              ?.concatMap(object : Function<Int, Publisher<List<String>>>{
                  override fun apply(page: Int): Publisher<List<String>> {
                      loading = true
                      progressBar.visibility = View.VISIBLE
                      return dataFromNetwork(page)
                  }
              })
              //?.subscribeOn(Schedulers.io())// DON'T USE IT
              ?.observeOn(AndroidSchedulers.mainThread())
              ?.subscribe(object : Consumer<List<String>>{
                  override fun accept(items: List<String>?) {

                      items?.let { paginationAdapter?.addItems(it) };
                      paginationAdapter?.notifyDataSetChanged();
                      loading = false;
                      progressBar.setVisibility(View.INVISIBLE);

                  }

              })*/
        /**************************************************************************/
        var disposable2 : Disposable ?= paginator?.onBackpressureDrop()
            ?.concatMap{
                loading = true
                progressBar.visibility = View.VISIBLE
                dataFromNetwork1(it)
            }
            //?.subscribeOn(Schedulers.io()) // DON'T USE IT
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe{
                paginationAdapter?.addItems(it)
                paginationAdapter?.notifyDataSetChanged();
                loading = false;
                progressBar.setVisibility(View.INVISIBLE);
            }
        /**************************************************************************/
        disposable2?.let { compositeDisposable.add(it) }

        paginator.onNext(pageNumber)

    }

    /**
     * Simulation of network data
     */
    fun dataFromNetwork1(page : Int) : Flowable<List<String>> {
        /* return Flowable.just(true)
             .delay(2, TimeUnit.SECONDS)
             .map(object:Function< Boolean,List<String> >{
                 override fun apply(value: Boolean): List<String> {

                     val items : ArrayList<String> = ArrayList<String>()
                     for (i in 1..10) {
                         items.add("Item " + (page * 10 + i))
                     }
                     return items

                 }

             })*/

        return Flowable.just(true)
            .delay(2, TimeUnit.SECONDS)
            .map{
                val items : ArrayList<String> = ArrayList<String>()
                for (i in 1..10) {
                    items.add("Item " + (page * 10 + i))
                }
                items
            }



    }




}