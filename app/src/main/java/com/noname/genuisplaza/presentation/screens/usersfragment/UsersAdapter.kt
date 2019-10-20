package com.noname.genuisplaza.presentation.screens.usersfragment

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noname.genuisplaza.R
import com.noname.genuisplaza.model.entities.User
import com.noname.genuisplaza.model.usecases.loudImage
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(activity: FragmentActivity, users: List<User>) :
    RecyclerView.Adapter<UsersAdapter.CustomView>() {

    private val context: Context
    private var usersData: List<User> = users

    init {
        this.context = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomView {
        return CustomView(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false))

    }

    override fun onBindViewHolder(holder: CustomView, position: Int) {
        loudImage(context,holder.img,usersData[position].avatar)
     holder.fname.text = usersData[position].first_name
     holder.lname.text = usersData[position].last_name
    }

    override fun getItemCount(): Int {
        return usersData.size
    }
    class CustomView (val mView: View) : RecyclerView.ViewHolder(mView) {
        val img = mView.user_avatar
        val fname = mView.firstname
        val lname = mView.lastname
    }
}
