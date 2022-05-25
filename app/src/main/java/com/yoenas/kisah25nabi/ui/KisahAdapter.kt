package com.yoenas.kisah25nabi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yoenas.kisah25nabi.data.KisahResponse
import com.yoenas.kisah25nabi.databinding.RowItemKisahBinding
import com.yoenas.kisah25nabi.utils.OnItemClickCallback

class KisahAdapter : RecyclerView.Adapter<KisahAdapter.MyViewHolder>() {

    private var listKisah = ArrayList<KisahResponse>()

    fun setData(data: List<KisahResponse>?) {
        if (data == null) return
        listKisah.clear()
        listKisah.addAll(data)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: RowItemKisahBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemKisahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listKisah[position]
        holder.binding.apply {
            itemName.text = data.name
            Glide.with(itemImg.context)
                .load(data.imageUrl)
                .apply(RequestOptions())
                .override(500, 500)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(itemImg)

            itemRowNabi.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }
        }
    }

    override fun getItemCount() = listKisah.size
}