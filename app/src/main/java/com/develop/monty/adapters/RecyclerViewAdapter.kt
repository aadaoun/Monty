package com.develop.monty.adapters

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.*
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.develop.monty.R
import com.develop.monty.Show
import com.develop.monty.models.Repo
import pl.droidsonroids.gif.GifImageView

class RecyclerViewAdapter(val load : GifImageView,var mainrecycler: RecyclerView): PagingDataAdapter<Repo, RecyclerViewAdapter.MyViewHolder >(DiffUtilCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerViewAdapter.MyViewHolder  {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder , position: Int) {
        try {
            load.visibility=View.GONE
            mainrecycler.visibility=View.VISIBLE
            holder.bind(getItem(position)!!)
            holder.setIsRecyclable(false);
            Log.d("@@@", "onBindViewHolder: binded")
        }catch (e: Exception){
            Log.d("@@@", "onBindViewHolder: "+e.toString())
        }
    }
        class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.avatar)
            val name: TextView = view.findViewById(R.id.repo_name)
            val created_at: TextView = view.findViewById(R.id.repo_date)
            val lay_back :LinearLayout = view.findViewById(R.id.lay_back)
            fun bind(data: Repo) {
                name.text = data.name
                created_at.text = data.created_at.substring(0,10)
                Glide.with(imageView)
                    .load(data.owner.avatar_url)
                    .circleCrop()
                    .into(imageView)
                lay_back.setOnClickListener(View.OnClickListener {
                val intent = Intent(lay_back.getContext(), Show::class.java)
                intent.putExtra("name", data.name)
                intent.putExtra("date", data.created_at)
                intent.putExtra("url", data.owner.avatar_url)
                intent.putExtra("count", data.stargazers_count)
                    lay_back.getContext()
                    .startActivity(intent)
            });
//        }

            }
        }





    }
        class DiffUtilCallBack: DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.name == newItem.name
                        && oldItem.created_at == newItem.created_at
            }

    }

