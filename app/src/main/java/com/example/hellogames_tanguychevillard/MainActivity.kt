package com.example.hellogames_tanguychevillard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    val data = arrayListOf<Game2>()// The base URL where the WebService is located


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"// Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())// Create a Retrofit client object targeting the provided URL// and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()// Use the client to create a service:// an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {// We got our data !
                     val responseData = response.body()

                    if (responseData != null)
                    {
                        Log.d("TAG", "WebService success : " + responseData.size)
                        getInfos(responseData[0].id, 1)
                        getInfos(responseData[1].id, 2)
                        getInfos(responseData[2].id, 3)
                        getInfos(responseData[3].id, 4)
                    }
                }
            }
        }


        service.listGames().enqueue(wsCallback)
    }

    fun getInfos(id : Int?, num : Int) {
        if (id == null)
            return
        Log.d("TAG", "getting first detail")

        val detailsCallback: Callback<Game2> = object : Callback<Game2> {
            override fun onFailure(call: Call<Game2>, t: Throwable) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                Log.d("TAG", t.toString())
            }

            override fun onResponse(call: Call<Game2>, response: Response<Game2>) {
                val responseData = response.body()
                if (responseData != null) {
                    data.add(responseData)
                    if (num == 1) {
                        Glide.with(this@MainActivity).load(responseData.picture).into(imageButton)
                        imageButton.setOnClickListener(View.OnClickListener {
                            Log.d("TAG", "clicked on button")
                            var intent : Intent = Intent(this@MainActivity, GameInfos::class.java)
                            var b : Bundle = Bundle()
                            b.putInt("game", responseData.id!!)
                            intent.putExtras(b)
                            startActivity(intent)
                        })
                    }
                    else if (num == 2) {
                        Glide.with(this@MainActivity).load(responseData.picture).into(imageButton2)
                        imageButton2.setOnClickListener(View.OnClickListener {
                            var intent : Intent = Intent(this@MainActivity, GameInfos::class.java)
                            var b : Bundle = Bundle()
                            b.putInt("game", responseData.id!!)
                            intent.putExtras(b)
                            startActivity(intent)
                        })
                    }
                    else if (num == 3) {
                        Glide.with(this@MainActivity).load(responseData.picture).into(imageButton3)
                        imageButton3.setOnClickListener(View.OnClickListener {
                            var intent : Intent = Intent(this@MainActivity, GameInfos::class.java)
                            var b : Bundle = Bundle()
                            b.putInt("game", responseData.id!!)
                            intent.putExtras(b)
                            startActivity(intent)
                        })
                    }
                    else if (num == 4) {
                        Glide.with(this@MainActivity).load(responseData.picture).into(imageButton4)
                        imageButton4.setOnClickListener(View.OnClickListener {
                            var intent : Intent = Intent(this@MainActivity, GameInfos::class.java)
                            var b : Bundle = Bundle()
                            b.putInt("game", responseData.id!!)
                            intent.putExtras(b)
                            startActivity(intent)
                        })
                    }

                    Log.d("TAG", "got first detail")
                }
            }
        }
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"// Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())// Create a Retrofit client object targeting the provided URL// and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()// Use the client to create a service:// an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
        service.GameDetails(id).enqueue(detailsCallback)

    }
}

interface WebServiceInterface {
    @GET("game/list")
    fun listGames(): Call<List<Game>>

    @GET("game/details")
    fun GameDetails(@Query("game_id") game_id : Int) : Call<Game2>
}