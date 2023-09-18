package com.sarrawi.img.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sarrawi.img.databinding.ImgDesignBinding
import com.sarrawi.img.databinding.TypesDesignBinding
import com.sarrawi.img.model.Img_Types_model
import com.sarrawi.img.model.Imgs_Model

class ImgAdapter(val con: Context): RecyclerView.Adapter<ImgAdapter.ViewHolder>() {

    class ViewHolder(val binding:ImgDesignBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Imgs_Model>(){
        override fun areItemsTheSame(oldItem: Imgs_Model, newItem: Imgs_Model): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Imgs_Model, newItem: Imgs_Model): Boolean {
            return newItem == oldItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var img_list: List<Imgs_Model>
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