package com.example.w1d3_rxjavademo

/*import android.annotation.SuppressLint
import com.example.w1d3_rxjavademo.network.model.Airline
import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

fun main() {
    chainExample()
    Thread.sleep(3000)
}

/***
 * There is another operator from(), which takes array of an object as input and emits
 * the object one after another same as just() operator.
 * Below is the code snippets to emit 1 to 5 integer
 */
@SuppressLint("CheckResult")
fun fromExample() {
    Observable.fromArray(arrayOf(1, 2, 3, 4, 5))
        .subscribe { x ->
            x.forEach {
                println("Item = {$it}")
            }
        }
}


////////// Filter operators
/***
 * take() is counter to the skip() operator. take(n) will emit only first n data elements and
 * ignores all data elements after n elements emitted.
 *
 * takeLast() operator will emit only the last element from the data stream.
 *
 * Opposite to above, takeFirst() operator will emit only first element of the data stream and
 * ignores subsequent data elements.
 */
@SuppressLint("CheckResult")
fun takeExample() {
    Observable.just(1, 2, 3, 4, 5)
        .take(2)
        .subscribe { x ->
            println("take = $x")
        }

    Observable.just(1, 2, 3, 4, 5)
        .takeLast(2)
        .subscribe { x ->
            println("takeLast = $x")
        }
}

/***
 * There is another operator skipLast().
 * This operator will emit only last element of the data stream.
 */
@SuppressLint("CheckResult")
fun skipLastExample() {
    Observable.just(1, 2, 3, 4, 5)
        .skipLast(1)
        .subscribe { x ->
            println("Item = $x")
        }
}

/***
 * skip(n) will suppress the first n items emitted by an Observable and emits data after n elements.
 * So, skip(2) will emit first 2 elements and starts from emitting the 3rd element.
 */
@SuppressLint("CheckResult")
fun skipExample() {
    Observable.just(1, 2, 3, 4, 5)
        .skip(1)
        .subscribe { x ->
            println("Item = $x")
        }
}

/***
 * Filter
 * Emits only those items from an Observable that pass
 * a predicate test (certain condition is met).
 */
@SuppressLint("CheckResult")
fun filterExample() {
    /***
    For each input item x, if x > 10, emit it in the resulting stream. 3 out of the 6 source values in the diagram are greater than 10 so the resulting stream should emit those 3 items.
    Source stream emits item with value 2. It’s not greater than 10 so it does not get emitted by the resulting stream.
    Source stream emits item with value 30. It’s greater than 10 so the resulting stream emits it.
    Source stream emits item with value 22. It’s greater than 10 so the resulting stream emits it.
    Source stream emits item with value 5. It’s not greater than 10 so it does not get emitted by the resulting stream.
    Source stream emits item with value 60. It’s greater than 10 so the resulting stream emits it.
    Source terminates which causes the resulting stream to terminate.
     */
    Observable.just(User("a", 10), User("b", 15), User("c", 20))
        .filter { user ->
            user.age > 10
        }
        .filter { user ->
            user.name == "b"
        }
        .subscribe { x ->
            println("Item = $x")
        }
}

////////// Merge operators
/***
 * merge() operator works same as the concat() operator and combines data stream from two different
 * observables. The only difference between merge() and concat() operator is merge can interleave
 * the outputs, while concat will first wait for earlier streams to finish before processing later
 * streams.
 * So, unlike concat() operator merge() operator doesn’t wait for data from observable 1 to complete.
 * It emits data from both the observable simultaneously as soon as the data becomes available to emit.
 */
@SuppressLint("CheckResult")
fun concatExample() {
    val obs1 = Observable.just(1, 1, 1)
    val obs2 = Observable.just(2, 2)
    val obs3 = Observable.just("a", "b")
    Observable.concat(obs1, obs2, obs3)
        .subscribe { x ->
            println("item: $x")
        }
}


/***
 * As the name suggest, you can use concat() operator to concat two different observable and emit
 * the data stream for both the operators one after another.
 */
@SuppressLint("CheckResult")
fun mergeExample() {
    val obs1 = Observable.just(1, 1, 1, 1, 1, 1)
    val obs2 = Observable.just(2, 2, 2, 2, 2, 2, 2, 2, 2)
    Observable.merge(obs1, obs2)
        .subscribe { x ->
            println("merge: $x")
        }
}

/***
 * Transforms the items emitted by an Observable by applying a
 * function to each item (or converting from one item type into another).
 */
@SuppressLint("CheckResult")
fun mapExample() {
    Observable.just(1, 2, 3)
        .map { x ->
            User("name", x)
        }
        .subscribe { user ->
            println("Item = ${user.age}, ${user.name}")
        }
}

/***
 * Transforms the items in Observable stream into separate
 * Observables, then flattens them into a single Observable stream.
 * So what is different between map() and flatMap()?
 * The map() operator transforms each value in an Observable stream
 * into a single value. flatMap() operator transforms each value in an
 * Observable stream into an arbitrary number (zero or more) values.
 *
 * Unlike the map() operation, flatMap() is often used when each
 * item emitted by the source stream needs to have its own threading
 * operators applied to it. For instance, a stream of user ids, is
 * flatMapped into Observable<User> objects where each User object is
 * looked up from the database on the IO thread using the
 * subscribeOn(Schedulers.io()) operator.
 * Note that flatMap() does not guarantee the order of the items in the resulting stream.
 */
@SuppressLint("CheckResult")
fun flatMapExample() {
    Observable.just(User("a", 10), User("b", 15), User("c", 20))
        .flatMap { user ->
            getUserDetailsByName(user)
        }
        .subscribe { userDetails ->
            println("Item = $userDetails")
        }
}

/***
 * SwitchMap
 * Whenever a new item is emitted by the Observable, it will unsubscribe to the Observable that was
 * generated from the previously emitted item and begin only mirroring the current one.
 * In other words, it returns the latest Observable and emits the items from it.
 */
@SuppressLint("CheckResult")
fun switchMapExample() {
    Observable.range(1, 20)
        .switchMap {
            if (it % 2 == 0) {
                Observable.just("a", "b", "c")
            } else {
                Observable.just(it)
            }
        }
        .subscribe { userDetails ->
            println("Item = $userDetails")
        }
}
/***
 * Map operator can be used when we fetch items from the server and need to modify it before emitting to the UI.
 *
 * FlatMap operator can be used when we know that the order of the items are not important.
 *
 * SwitchMap is best suited for scenarios such as a feed page, when pull to refresh is enabled.
 * When user refreshes the screen, the older feed response is ignored and only the latest request
 * results are emitted to the UI when using a SwitchMap.
 */

/***
 * GroupBy
 * This operator divides an Observable into a set of Observables that each emit a different group
 * of items from the original Observable, organised by key.
 * Sample Implementation: The below code will create an Observable with range of 1 to 10 numbers.
 * We use the groupBy() operator to emit only even numbers from the list.
 */
@SuppressLint("CheckResult")
fun groupByExample() {
    Observable.range(1, 20)
        .groupBy {
            (it % 2 == 0)
        }
        .subscribe {
            println("Item = ${it.key}")
        }
}

/***
 * The zip() operator will combine the values of multiple Observable together through a specific function.
 */
@SuppressLint("CheckResult")
fun zipExample() {
    Observable.zip(
        Observable.just(
            "Roses", "Sunflowers", "Leaves", "Clouds", "Violets", "Plastics"),
        Observable.just(
            "Red", "Yellow", "Green", "White or Grey", "Purple"),
        BiFunction<String, String, String> { type, color ->
            "$type are $color"
        }
    ).subscribe { v -> println("Received: $v") }
}

/***
 * Scan
 * This operator Transform each item into another item, like you did with map.
 * But also include the “previous” item when you get around to doing a transform.
 */
@SuppressLint("CheckResult")
fun scanExample() {
    Observable.range(1, 20)
        .scan { t1: Int, t2: Int ->
            println("xyz $t1 plus $t2")
            t1 + t2
        }
        .subscribe {
            println("Item = $it")
        }
}

/***
 * This operator will repeat the emission of the values however many times we may need.
 */
@SuppressLint("CheckResult")
fun repeatExample() {
    Observable.just("Apple", "Orange", "Banana")
        .repeat(2)
        .subscribe { v -> println("Received: $v") }
}

////////// Other operators

fun takeExample2() {
    Observable.just("Apple", "Orange", "Banana")
        .take(2)
        .subscribe { v -> println("Received: $v") }
}

/***
 * Instead of writing the array of numbers manually, you can do the same using range.
 */
@SuppressLint("CheckResult")
fun rangeExample() {
    Observable.range(1, 20)
        .subscribe { userDetails ->
            println("Item = $userDetails")
        }
}

/***
 * Buffer This operator periodically gather items from an Observable into bundles and emit these
 * bundles rather than emitting the items one at a time.
 */
@SuppressLint("CheckResult")
fun bufferExample() {
    Observable.range(1, 20)
        .buffer(2)
        .doOnNext {
            println("doOnNext = $it")
        }
        .subscribe { userDetails ->
            println("Item = $userDetails")
        }
}

/***
 * Chaining multiple operators
 * Sometimes the desired data stream can’t achieved using a single operator.
 * In that case you can use multiple operators together.
 *
 * range(): Range operator generates the numbers from 1 to 20
 * filter(): Filters the numbers by applying a condition onto each number
 * map(): Map transform the data from Integer to String by appending the string at the end
 * In the operator chain, filter() will be executed first and map() takes the result from
 * filter and performs it’s job
 */
@SuppressLint("CheckResult")
fun chainExample() {
    Observable.range(1, 20)
        .filter{
            it % 2 == 0
        }
        .map {
            "string - $it"
        }
        .subscribe { userDetails ->
            println("Item = $userDetails")
        }
}

fun getUserDetailsByName(user: User): Observable<UserDetails> {
    return if (user.name == "a")
        Observable.just(UserDetails("a", 10))
    else
        Observable.just(UserDetails("x", 0))
}

data class User(val name:String, val age:Int)
data class UserDetails(val name: String, val value: Int)


@SuppressLint("CheckResult")
fun exampleHowToEmitData() {
    val stringObservable = Observable.just("a", "b")

    stringObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { value -> println("Received: $value") }, // onNext
            { error -> println("Error: $error") },    // onError
            { println("Completed!") }                 // onComplete
        )
}

fun observableTryingToHandleBackPressure() {
    val observable = PublishSubject.create<Int>()
    observable.observeOn(Schedulers.computation())
        .subscribe (
            {
                println("The Number Is: $it")
            },
            {t->
                print(t.message)
            }
        )
    for (i in 0..1000000){
        observable.onNext(i)
    }
}

fun flowableHandlingBackPressure() {
    println("Back")
    val observable = PublishSubject.create<Int>()
    observable
        .toFlowable(BackpressureStrategy.DROP)
        .observeOn(Schedulers.computation())
        .subscribe (
            {
                println("The Number Is: $it")
            },
            {t->
                print(t.message)
            }
        )
    for (i in 0..1000000){
        observable.onNext(i)
    }
}

fun flowableExample() {
    Flowable.just("This is a Flowable")
        .subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: $error") },
            { println("Completed") }
        )
}

fun maybeExample() {
    Maybe.just("This is a Maybe")
        .subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: $error") },
            { println("Completed") }
        )
}

fun singleExample() {
    Single.just("This is a Single")
        .subscribe(
            { v -> println("Value is: $v") },
            { e -> println("Error: $e")}
        )
}

fun completableExample() {
    Completable.create { emitter ->
        emitter.onComplete()
        emitter.onError(Exception())
    }
}

fun completableExample2() {
    Observable.just("Hello")
        .doOnSubscribe { println("Subscribed") }
        .doOnNext { s -> println("Received: $s") }
        .doAfterNext { println("After Receiving") }
        .doOnError { e -> println("Error: $e") }
        .doOnComplete { println("Complete") }
        .doFinally { println("Do Finally!") }
        .doOnDispose { println("Do on Dispose!") }
        .subscribe { println("Subscribe") }
}

fun subscribeOnExample() {
    Observable.just("Apple", "Orange", "Banana")
        .subscribeOn(Schedulers.io())
        .subscribe{ v -> println("Received: $v") }
}

fun observerOnExample() {
    Observable.just("Apple", "Orange", "Banana")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{ v -> println("Received: $v") }
}

fun chainExample2() {
    val ticket1 = Ticket(
        "a", "b", "c", "d", "e", "j", "t", 3,
        Airline(2, "delta", "o"),
        null
    )

    val ticket2 = Ticket(
        "c", "b", "c", "d", "e", "j", "t", 3,
        Airline(2, "delta", "o"),
        null
    )

    val ticket3 = Ticket(
        "d", "b", "c", "d", "e", "j", "t", 3,
        Airline(2, "spirit", "o"),
        null
    )

    val ticket4 = Ticket(
        "e", "b", "c", "d", "e", "j", "t", 3,
        Airline(2, "AA", "o"),
        null
    )


    Observable.just(ticket1, ticket2, ticket3, ticket4)
        .filter { it ->
            it.airline.name == "delta"
        }
        .map {
            it.price = Price(
                10f,
                "seats",
                "currency",
                "fn",
                "from",
                "to"
            )
            //Observable.just(it)
        }
        .subscribe { x ->
            println("Item = $x")
        }

}


 */
