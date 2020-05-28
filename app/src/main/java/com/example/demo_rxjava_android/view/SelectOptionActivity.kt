package com.example.demo_rxjava_android.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo_rxjava_android.R
import com.example.demo_rxjava_android.view.networking.NetworkingActivity
import com.example.demo_rxjava_android.view.operators.OperatorsActivity
import com.example.demo_rxjava_android.view.pagination.PaginationActivity
import com.example.demo_rxjava_android.view.rxBus.Events
import com.example.demo_rxjava_android.view.rxBus.MyApplication
import com.example.demo_rxjava_android.view.rxBus.RxBusActivity
import com.example.demo_rxjava_android.view.search.SearchActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_select_option.*
import java.util.concurrent.TimeUnit

class SelectOptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_option)
        click()
    }

    private fun click() {
        idOperators?.setOnClickListener{
            startActivity(Intent(this@SelectOptionActivity, OperatorsActivity::class.java))
        }
        idNetworking?.setOnClickListener {
            startActivity(Intent(this@SelectOptionActivity, NetworkingActivity::class.java))
        }
        idRx_bus?.setOnClickListener {

             Observable.timer(2, TimeUnit.SECONDS)
             /******************************************************************************/
                /* .subscribe(object : Consumer<Long> {
                     override fun accept(t: Long?) {
                         (application as MyApplication)?.bus()?.send(Events.AutoEvent())
                     }
                 })*/
             /******************************************************************************/
             .subscribe {
                (application as MyApplication)?.bus()?.send(Events.AutoEvent())
             }
            /******************************************************************************/
            startActivity(Intent(this@SelectOptionActivity, RxBusActivity::class.java))

        }
        idPagination?.setOnClickListener {
            startActivity(Intent(this@SelectOptionActivity, PaginationActivity::class.java))
        }
        idSearch?.setOnClickListener {
            startActivity(Intent(this@SelectOptionActivity, SearchActivity::class.java))
        }
    }
}
