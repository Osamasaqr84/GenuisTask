package com.noname.genuisplaza.presentation.screens.usersfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noname.genuisplaza.R
import com.noname.genuisplaza.model.entities.User
import com.noname.genuisplaza.model.usecases.showToastBasedOnThrowable
import com.noname.genuisplaza.presentation.screens.adduser.AddUserFragment
import kotlinx.android.synthetic.main.users_fragment.*
import kotlinx.android.synthetic.main.users_fragment.view.*
import java.util.*

class UsersFragment : Fragment() {

    val viewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this,getViewModelFactory()).get(UsersViewModel::class.java)
    }
    lateinit var UsersAdapter: UsersAdapter
    internal lateinit var reposList: ArrayList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.users_fragment, container, false)
        viewModel.usersdata.observe(this, Observer {
                reposList = it?.data as ArrayList<User>
                if (it?.data.size>0) {
                    UsersAdapter =UsersAdapter( activity!!,it.data )
                    view.users.adapter = UsersAdapter
                }
                else
                    notfound.visibility=View.VISIBLE
        })

        viewModel.errorLivedat.observe(this, Observer {
            showToastBasedOnThrowable(context,it)
        })
        viewModel.loadingLivedat.observe(this,
            Observer { loading ->
                view.progress.setVisibility(if (loading!!) View.VISIBLE else View.GONE) })

        view.add_user.setOnClickListener(View.OnClickListener {
            (context as FragmentActivity).supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fram,AddUserFragment())?.addToBackStack(null)?.commit()
        })

        viewModel.userslocaldata.observe(this, Observer {
            if (it?.size!!>0) {
                UsersAdapter =UsersAdapter( activity!!,it )
                view.users.adapter = UsersAdapter
            }
            else
                notfound.visibility=View.VISIBLE
        })
        return view
    }

    fun getViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(
            activity?.application
        )
    }

}
