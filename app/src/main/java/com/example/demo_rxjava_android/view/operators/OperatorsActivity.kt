package com.example.demo_rxjava_android.view.operators

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo_rxjava_android.R
import kotlinx.android.synthetic.main.activity_operators.*

class OperatorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operators)

        click()
    }

    private fun click() {
        simple?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,SimpleExampleActivity::class.java))
        }
        map?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,MapExampleActivity::class.java))
        }
        zip?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ZipExampleActivity::class.java))
        }
        disposable?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,DisposableExampleActivity::class.java))
        }
        take?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,TakeExampleActivity::class.java))
        }
        timer?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,TimerExampleActivity::class.java))
        }
        singleObserver?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,SingleObserverExampleActivity::class.java))
        }
        completableObserver?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,CompletableObserverExampleActivity::class.java))
        }
        flowable?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,FlowableExampleActivity::class.java))
        }
        reduce?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ReduceExampleActivity::class.java))
        }
        buffer?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,BufferExampleActivity::class.java))
        }
        filter?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,FilterExampleActivity::class.java))
        }
        skip?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,SkipExampleActivity::class.java))
        }
        scan?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ScanExampleActivity::class.java))
        }
        concat?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ConcatExampleActivity::class.java))
        }
        merge?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,MergeExampleActivity::class.java))
        }
        defer?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,DeferExampleActivity::class.java))
        }
        distinct?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,DistinctExampleActivity::class.java))
        }
        last?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,LastOperatorExampleActivity::class.java))
        }
        replay_subject?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ReplaySubjectExampleActivity::class.java))
        }
        publish_subject?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,PublishSubjectExampleActivity::class.java))
        }
        behavior_subject?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,BehaviorSubjectExampleActivity::class.java))
        }
        async_subject?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,AsyncSubjectExampleActivity::class.java))
        }
        debounce?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,DebounceExampleActivity::class.java))
        }
        delay?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,DelayExampleActivity::class.java))
        }
        switch_map?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,SwitchMapExampleActivity::class.java))
        }
        take_while?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,TakeWhileExampleActivity::class.java))
        }
        take_until?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,TakeUntilExampleActivity::class.java))
        }
        interval?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,IntervalExampleActivity::class.java))
        }
        replay?.setOnClickListener {
            startActivity(Intent(this@OperatorsActivity,ReplayExampleActivity::class.java))
        }
    }
}
