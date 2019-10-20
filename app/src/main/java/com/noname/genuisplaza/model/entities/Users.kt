package com.noname.genuisplaza.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.*

data class Users(
    val `data`: List<User>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

@Entity(tableName = "User")
data class User(
    val avatar: String,
    val email: String,
    val first_name: String,
    @PrimaryKey
    val id: Int,
    val last_name: String
)

data class AddUser(
    val createdAt: String,
    val id: Int
)