package com.develop.monty

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Button
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.develop.monty.adapters.ReposLoadStateAdapter
import com.develop.monty.viewmodels.MainViewModel
import com.develop.monty.adapters.RecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import pl.droidsonroids.gif.GifImageView


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var retry: Button
    private lateinit var search: SearchView
    private lateinit var connecting: ProgressBar
    lateinit var load : GifImageView
    lateinit var Error : TextView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var recyclerSearchViewAdapter: RecyclerViewAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("@@@", "onCreate: 1")
        search = findViewById(R.id.filter)
        mainrecycler = findViewById(R.id.recycler)
        Error = findViewById(R.id.error_con)
        load = findViewById(R.id.main_load_gif)
        retry = findViewById(R.id.Retry)
        connecting = findViewById(R.id.connecting)
        retry.setOnClickListener(View.OnClickListener {
            connecting.visibility=View.VISIBLE
            if(isOnline(this)) {
                retry.visibility = View.GONE
                connecting.visibility=View.GONE
                Error.visibility=View.GONE
                load.visibility=View.VISIBLE
                Log.d("@@@", "onCreate: ")
                initRecyclerView()
                initViewModel()
            }else{
                retry.visibility = View.VISIBLE
                connecting.visibility=View.GONE
                load.visibility=View.GONE
                Error.visibility=View.VISIBLE
            }

        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callSearch(newText)
                return true
            }

            fun callSearch(query: String?) {
                viewModel.remove_all()
                initSearchView()
                initSearchViewModel(query.toString())
            }
        })

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if(isOnline(this)) {
            Log.d("@@@", "onCreate: here")
            initRecyclerView()
            initViewModel()
        }else{
            retry.visibility = View.VISIBLE
            load.visibility=View.GONE
            Error.visibility=View.VISIBLE
            connecting.visibility=View.GONE
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

    private fun initRecyclerView() {
        mainrecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter(load,mainrecycler)
            adapter = recyclerViewAdapter
            adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { recyclerViewAdapter.retry() },
                footer = ReposLoadStateAdapter { recyclerViewAdapter.retry() }
            )

        }
    }
    fun Context.print_error(error:String){

    }
    private fun initSearchView() {
        mainrecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerSearchViewAdapter = RecyclerViewAdapter(load,mainrecycler)
            adapter = recyclerSearchViewAdapter
            adapter = recyclerSearchViewAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { recyclerSearchViewAdapter.retry() },
                footer = ReposLoadStateAdapter { recyclerSearchViewAdapter.retry() }
            )

        }
    }
    fun visi_prog(){
        connecting.visibility=View.GONE
//        mainrecycler.visibility = View.VISIBLE
    }
    private fun initViewModel() {
        val viewModel  = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                visi_prog()

                recyclerViewAdapter.submitData(it)

            }

        }
    }
    private fun initSearchViewModel(que:String) {
        val viewModel  = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getSearchData(que).collectLatest {
                visi_prog()
                recyclerSearchViewAdapter.submitData(it)
            }

        }
    }
}