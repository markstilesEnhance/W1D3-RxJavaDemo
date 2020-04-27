package com.example.w1d3_rxjavademo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.w1d3_rxjavademo.R
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_rx_java_example.*

class RxJavaExampleActivity : AppCompatActivity() {

    val source = PublishSubject.create<String>()
    val disposables = CompositeDisposable()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_example)

        disposables.add(
            source.subscribe(
                { v -> counterTxt.text = v },
                { e -> counterTxt.text = "Error: $e" }
            )
        )

        countBtn.setOnClickListener {
            source.onNext("Counter: ${++count}")
            if (count == 10) {
                source.onComplete()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }


    fun <T> applyObservableAsync(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun exampleHowToEmitData() {
        Observable.just("Apple", "Orange", "Banana")
            .compose(applyObservableAsync())
            .subscribe { v -> println("The First Observable Received: $v") }

        Observable.just("Water", "Fire", "Wood")
            .compose(applyObservableAsync())
            .subscribe { v -> println("The Second Observable Received: $v") }
    }
}
