package com.example.recyclerviewsearch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewsearch.R
import com.example.recyclerviewsearch.database.User
import java.util.*

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder>() {
	private var items = listOf<User>()
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): RecyclerViewAdapter.UserViewHolder {
		val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_element,parent,false)
		return UserViewHolder(inflater)
	}

	fun setRecyclerListData(data: List<User>){
		Log.d("MyTestHere","Called")
		items = data
		notifyDataSetChanged()
	}

	override fun onBindViewHolder(holder: RecyclerViewAdapter.UserViewHolder, position: Int) {
		holder.bind(items[position])
	}

	override fun getItemCount(): Int {
		return items.size
	}

	class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name = itemView.findViewById<TextView>(R.id.recyler_name)
		val age = itemView.findViewById<TextView>(R.id.recyler_age)
		val gender = itemView.findViewById<TextView>(R.id.recycler_gender)
		fun bind(data: User){
			name.text = data.name
			age.text = data.age.toString()
			gender.text = data.gender
		}
	}

}