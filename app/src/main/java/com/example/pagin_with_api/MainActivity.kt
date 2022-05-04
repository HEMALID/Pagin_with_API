package com.example.pagin_with_api

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagin_with_api.databinding.ActivityMainBinding
import com.example.pagin_with_api.dataclass.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var userlist: ArrayList<Data.DataItem>
    private var isLoading = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val BASE_URL = "https://api.github.com/"
      /*  "https://api.github.com/"*/

    var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userlist = arrayListOf()
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = linearLayoutManager
        myAdapter = MyAdapter(baseContext, userlist)
        binding.recyclerview.adapter = myAdapter

        setAdapter()
        getData(num)
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if (!isLoading) {
                    if (linearLayoutManager.findLastVisibleItemPosition() == userlist.size - 1) {
                        getData(num)
                    }
                }

                    /*val visibleItemCount: Int = linearLayoutManager.childCount
                    val totleItemCount: Int = linearLayoutManager.itemCount
                    val firstVisibleItemPosition: Int =
                        linearLayoutManager.findFirstVisibleItemPosition()

                    if (isLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totleItemCount && firstVisibleItemPosition >= 0) {
                            num++
                            getData(num)
                        }
                    }*/
            }
        })
    }
    private fun setAdapter() {
    }

    private fun getData(since:Int){

        isLoading = true
        binding.progressbar.visibility = View.VISIBLE
        binding.progressbar.progressTintList = ColorStateList.valueOf(Color.RED)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)

        val retrofitData = retrofitBuilder.getData(since,5)

        retrofitData.enqueue(object : Callback<ArrayList<Data.DataItem>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ArrayList<Data.DataItem>?>, response: Response<ArrayList<Data.DataItem>?>) {

                var responseBody=response.body()
                userlist.addAll(responseBody!!)

                this@MainActivity.num = userlist[userlist.size-1].id

                binding.progressbar.postDelayed(Runnable {
                    Log.d("TAG2","$userlist.size isSize")
                    myAdapter.notifyDataSetChanged()
                    isLoading = false
                    binding.progressbar.visibility = View.GONE
                }, 3000)
            }

            override fun onFailure(call: Call<ArrayList<Data.DataItem>?>, t: Throwable) {
                Log.d("MainActivity","onfailer"+t.message)
            }
        })
    }
}