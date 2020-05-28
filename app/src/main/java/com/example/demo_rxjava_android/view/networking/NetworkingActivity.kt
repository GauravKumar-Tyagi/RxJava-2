package com.example.demo_rxjava_android.view.networking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo_rxjava_android.R
import kotlinx.android.synthetic.main.activity_networking.*


class NetworkingActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)

        click()
    }


    private fun click() {
        map?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,MapActivity::class.java))
        }
        zip?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,ZipActivity::class.java))
        }
        flatMapAndFilter?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,FlatMapAndFilter::class.java))
        }
        take?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,TakeActivity::class.java))
        }
        flatMap?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,FlatMapActivity::class.java))
        }
        flatMapWithZip?.setOnClickListener {
            startActivity(Intent(this@NetworkingActivity,FlatMapWithZipActivity::class.java))
        }
    }
}
