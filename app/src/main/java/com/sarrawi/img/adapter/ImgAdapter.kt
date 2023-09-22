package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarrawi.img.databinding.ImgDesignBinding
import com.sarrawi.img.model.ImgsModel

class ImgAdapter(val con: Context): RecyclerView.Adapter<ImgAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:ImgDesignBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            val current_imgModel = img_list[position]


            Glide.with(con)
                .load(current_imgModel.image_url) // تحديد URL الصورة
                .circleCrop()
                .into(binding.imgadapterImgViewContent) // تحديد ImageView كهدف لعرض الصورة


        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ImgsModel>(){
        override fun areItemsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImgsModel, newItem: ImgsModel): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var img_list: List<ImgsModel>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(ImgDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return img_list.size
    }
}