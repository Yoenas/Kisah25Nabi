package com.yoenas.kisah25nabi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.kisah25nabi.data.KisahResponse
import com.yoenas.kisah25nabi.databinding.ActivityMainBinding
import com.yoenas.kisah25nabi.ui.detail.DetailActivity
import com.yoenas.kisah25nabi.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getDataForView()

        viewModel.kisahResponse.observe(this) { showData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.isError.observe(this) { showError(it) }
    }

    private fun showError(isError: Throwable?) {
        Toast.makeText(this, isError?.localizedMessage, Toast.LENGTH_SHORT).show()
        Log.e("TAG", isError?.localizedMessage.toString())
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.progressMain.visibility = View.VISIBLE
        } else {
            binding.progressMain.visibility = View.GONE
        }
    }

    private fun showData(data: List<KisahResponse>?) {
        val mAdapter = KisahAdapter()
        binding.recyclerMain.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            mAdapter.setData(data)
            adapter = mAdapter
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(item: KisahResponse) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_DATA, item)
                    )
                }
            })
        }
    }
}