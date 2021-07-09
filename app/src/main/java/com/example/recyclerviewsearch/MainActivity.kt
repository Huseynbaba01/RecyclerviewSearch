package com.example.recyclerviewsearch

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewsearch.adapter.RecyclerViewAdapter
import com.example.recyclerviewsearch.database.ObjectBox.boxStore
import com.example.recyclerviewsearch.database.ObjectBox.init
import com.example.recyclerviewsearch.database.User
import com.example.recyclerviewsearch.database.User_
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.objectbox.Box
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlin.random.Random


class MainActivity : AppCompatActivity(){
	private lateinit var adapterForSpinner: ArrayAdapter<CharSequence>
	private lateinit var spinner: Spinner
	private lateinit var recyclerView: RecyclerView
	private lateinit var searchView: androidx.appcompat.widget.SearchView
	private lateinit var box: Box<User>
	private lateinit var query: Query<User>
	private lateinit var subscription: DataSubscription
	private lateinit var adapter: RecyclerViewAdapter
	private var id = 0
	private lateinit var floatingActionButton: FloatingActionButton
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initializeUi()
		initializeBox()
		initializeQuery()
		initializeObserver()
		buttonsSetOnClickListeners()
	}

	private fun buttonsSetOnClickListeners() {
		floatingActionButton.setOnClickListener {
			box.put(generateUser())
		}
		spinner.onItemSelectedListener = object: AdapterView.OnItemClickListener,
			AdapterView.OnItemSelectedListener {
			override fun onItemClick(
				parent: AdapterView<*>?,
				view: View?,
				position: Int,
				id: Long
			) {
				val list = box.all
				if(position == 0) {
					list.sortBy {
						it.name
					}
				}
				else{
					list.sortByDescending {
						it.name
					}
				}
//				Toast.makeText(this@MainActivity,list[0].name,Toast.LENGTH_SHORT).show()
				adapter.setRecyclerListData(list)
			}

			override fun onItemSelected(
				parent: AdapterView<*>?,
				view: View?,
				position: Int,
				id: Long
			) {
				val list = box.all
				if(position == 0) {
					list.sortBy {
						it.name
					}
				}
				else{
					list.sortByDescending {
						it.name
					}
				}
//				Toast.makeText(this@MainActivity,list[0].name,Toast.LENGTH_SHORT).show()

				adapter.setRecyclerListData(list)
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				Toast.makeText(this@MainActivity,"Nothing selected",Toast.LENGTH_SHORT).show()
			}

		}

		/*searchView.setOnQueryTextListener{

		}*/
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
			androidx.appcompat.widget.SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(queryForString: String): Boolean {
				return false
			}

			override fun onQueryTextChange(newText: String): Boolean {
				val list = box.query().contains(User_.name,newText).build().find()
				adapter.setRecyclerListData(list)
				if(list.size == 0){
					Toast.makeText(this@MainActivity,"No matching items found",Toast.LENGTH_SHORT).show()
				}
				return false
			}
		})
	}

	private fun initializeObserver() {
		subscription = query.subscribe().observer {
			adapter.setRecyclerListData(it)
		}
	}

	private fun initializeQuery() {
		query = box
			.query()
			.build()
	}

	private fun initializeBox() {
		init(this)
		box = boxStore!!.boxFor(User::class.java)
	}

	private fun initializeUi() {
		spinner = findViewById(R.id.spinner)
		adapterForSpinner = ArrayAdapter.createFromResource(this,R.array.names,android.R.layout.simple_spinner_item)
		adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinner.adapter = adapterForSpinner
		recyclerView = findViewById(R.id.recycler_view)
		searchView = findViewById(R.id.search_view)
		floatingActionButton = findViewById(R.id.button)
		adapter = RecyclerViewAdapter()
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(this)
	}
	private fun generateUser(): User{
		var random: Int = Random.nextInt()
		if(random < 0) {
			random *= -1
		}
		val gender = when(random % 2) {
			0 -> {
				"male"
			}
			else -> {
				"female"
			}
		}
		id++
		return User(0, "User$id",random % 100, gender)
	}

//	override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//		val list = box.all
//		if(position == 0){
//			list.sortBy {
//				it.name
//			}
//
//		}
//		else{
//			list.sortByDescending {
//				it.name
//			}
//		}
//		box.put(list)
//	}
//
//	override fun onNothingSelected(parent: AdapterView<*>?) {
//	}
}