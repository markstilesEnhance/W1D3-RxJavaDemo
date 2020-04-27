package com.example.w1d3_rxjavademo.network

import com.example.w1d3_rxjavademo.network.model.Price
import com.example.w1d3_rxjavademo.network.model.Ticket
import com.google.gson.Gson
import io.reactivex.Single

class FakeApiService {

    val ticketsJson = "[{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"1K-970\",\"departure\":\"16:22\",\"arrival\":\"19:20\",\"duration\":\"2h 58m\",\"instructions\":\"Free Meals\\/Snacks\",\"stops\":1,\"airline\":{\"id\":1105,\"name\":\"Jet Airways\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/jetairways.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"HS-633\",\"departure\":\"13:07\",\"arrival\":\"15:49\",\"duration\":\"2h 42m\",\"instructions\":\"Non Refundable\",\"stops\":2,\"airline\":{\"id\":1103,\"name\":\"Spicejet\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/spicejet.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"CD-977\",\"departure\":\"20:02\",\"arrival\":\"22:04\",\"duration\":\"2h 2m\",\"instructions\":\"\",\"stops\":1,\"airline\":{\"id\":1102,\"name\":\"Air India\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/airindia.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"KS-728\",\"departure\":\"08:25\",\"arrival\":\"11:02\",\"duration\":\"2h 37m\",\"instructions\":\"\",\"stops\":1,\"airline\":{\"id\":1104,\"name\":\"Go Air\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/goair.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"JY-424\",\"departure\":\"10:16\",\"arrival\":\"12:21\",\"duration\":\"2h 5m\",\"instructions\":\"Partially Refundable\",\"stops\":2,\"airline\":{\"id\":1105,\"name\":\"Jet Airways\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/jetairways.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"V9-824\",\"departure\":\"08:22\",\"arrival\":\"10:28\",\"duration\":\"2h 6m\",\"instructions\":\"Non Refundable\",\"stops\":1,\"airline\":{\"id\":1101,\"name\":\"IndiGo\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/indigo.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"8R-645\",\"departure\":\"18:19\",\"arrival\":\"21:00\",\"duration\":\"2h 41m\",\"instructions\":\"\",\"stops\":2,\"airline\":{\"id\":1103,\"name\":\"Spicejet\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/spicejet.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"0W-384\",\"departure\":\"18:19\",\"arrival\":\"20:24\",\"duration\":\"2h 5m\",\"instructions\":\"Non Refundable\",\"stops\":2,\"airline\":{\"id\":1104,\"name\":\"Go Air\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/goair.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"0V-901\",\"departure\":\"08:46\",\"arrival\":\"11:04\",\"duration\":\"2h 18m\",\"instructions\":\"\",\"stops\":1,\"airline\":{\"id\":1105,\"name\":\"Jet Airways\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/jetairways.png\"}},{\"from\":\"DEL\",\"to\":\"HYD\",\"flight_number\":\"PA-931\",\"departure\":\"22:30\",\"arrival\":\"00:30\",\"duration\":\"22h 0m\",\"instructions\":\"Non Refundable\",\"stops\":1,\"airline\":{\"id\":1102,\"name\":\"Air India\",\"logo\":\"https:\\/\\/api.androidhive.info\\/json\\/images\\/airindia.png\"}}]"
    val priceJson = "{\"price\":2432,\"seats\":16,\"currency\":\"INR\",\"flight_number\":\"6E-ARIfrom=DEL\",\"from\":\"\",\"to\":\"CHE\"}"
    val gson = Gson()

    fun getPrice(flightNumber: String, from: String,to: String): Single<Price> {
        val price: Price = gson.fromJson(priceJson, Price::class.java)
        Thread.sleep(1000)
        return Single.just(price)
    }

    fun searchTickets(from: String, to: String): Single<List<Ticket>> {
        val ticketArray: Array<Ticket> = gson.fromJson(ticketsJson, Array<Ticket>::class.java)
        return Single.just(ticketArray.toList())
    }
}