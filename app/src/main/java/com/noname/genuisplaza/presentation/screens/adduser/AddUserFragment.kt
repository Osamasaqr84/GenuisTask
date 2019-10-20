package com.noname.genuisplaza.presentation.screens.adduser

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.noname.genuisplaza.R
import com.noname.genuisplaza.model.usecases.showToastBasedOnThrowable
import com.noname.genuisplaza.presentation.screens.usersfragment.UsersViewModel
import com.noname.genuisplaza.presentation.screens.usersfragment.ViewModelFactory
import kotlinx.android.synthetic.main.add_user_fragment.view.*
import kotlinx.android.synthetic.main.add_user_fragment.view.progress

class AddUserFragment : Fragment() {

    val viewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this,getViewModelFactory()).get(UsersViewModel::class.java)
    }


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.add_user_fragment, container, false)

        view.add.setOnClickListener {
            viewModel.adduser(view.username.text.toString(),view.job.text.toString())
        }

        viewModel.errorLivedat.observe(this, Observer {
            showToastBasedOnThrowable(context,it)
        })
        viewModel.loadingLivedat.observe(this,
            Observer { loading ->
                view.progress.setVisibility(if (loading!!) View.VISIBLE else View.GONE) })

        viewModel.errormesege.observe(this, Observer {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        })

        viewModel.addusersdata.observe(this, Observer {
            if (it?.id!! >0) {
                Toast.makeText(context, "User add Successfully", Toast.LENGTH_LONG).show()
                view.username.setText("")
                view.job.setText("")
            }
            else
                Toast.makeText(context,"error Occure,please try again",Toast.LENGTH_LONG).show()


        })
        return view
    }

    fun getViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(
            activity?.application
        )
    }

}
