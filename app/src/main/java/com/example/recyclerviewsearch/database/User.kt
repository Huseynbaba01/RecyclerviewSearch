package com.example.recyclerviewsearch.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class User(
	@Id var id: Long? = 0,
	var name: String? = "Undefined",
	var age: Int? = 0,
	var gender: String? = "undefined"
)
