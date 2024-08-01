package com.example.funprimeassesment.ui.fragments.recycler_screen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.funprimeassesment.data_layer.models.ApiModelItem
import com.example.funprimeassesment.databinding.ItemImageBinding

class RecyclerAdapter(private var clickCallBack: (imageItem:ApiModelItem)->Unit) : RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>() {
    private var listOfData = ArrayList<ApiModelItem>()
    open class ViewHolderClass(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }

    override fun getItemCount(): Int {
       return listOfData.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        holder.binding.itemImg.load(listOfData[position].thumbnailUrl)
        holder.binding.itemTitle.text=listOfData[position].title
        holder.itemView.setOnClickListener {
            clickCallBack.invoke(listOfData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(list:ArrayList<ApiModelItem>){
        this.listOfData=list
        notifyDataSetChanged()
    }
}