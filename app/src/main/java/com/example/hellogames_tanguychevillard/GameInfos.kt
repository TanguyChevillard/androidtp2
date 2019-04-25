package com.example.hellogames_tanguychevillard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game_infos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameInfos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_infos)

        var b : Bundle = intent.extras
        var values = -1
        if (b != null)
            values = b.getInt("game")

            val detailsCallback: Callback<Game2> = object : Callback<Game2> {
                override fun onFailure(call: Call<Game2>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    Log.d("TAG", t.toString())
                }

                override fun onResponse(call: Call<Game2>, response: Response<Game2>) {
                    val responseData = response.body()
                    if (responseData != null) {
                        Glide.with(this@GameInfos).load(responseData.picture).into(imageView)
                        name.setText(responseData.name)
                        type.setText(responseData.type)
                        desc.setText(responseData.description_en)
                        year.setText(responseData.year)
                        nb.setText(responseData.players)
                        Log.d("TAG", "got first detail")
                    }
                }
            }
            val baseURL = "https://androidlessonsapi.herokuapp.com/api/"// Use GSON library to create our JSON parser
            val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())// Create a Retrofit client object targeting the provided URL// and add a JSON converter (because we are expecting json responses)
            val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()// Use the client to create a service:// an object implementing the interface to the WebService
            val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
            service.GameDetails(values).enqueue(detailsCallback)
    }
}
