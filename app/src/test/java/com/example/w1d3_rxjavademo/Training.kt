package com.example.w1d3_rxjavademo

import io.reactivex.Observable


fun exampleHowToEmitData() {
    val stringObservable = Observable.just("a", "b")

    stringObservable
        .subscribe(
            {value -> println("Received: $value")},
            {error -> println("Error: $error")},
            {println("Completed!")}
        )
}

fun main() {
    exampleHowToEmitData()
    Thread.sleep(3000)
}