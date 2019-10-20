package com.noname.genuisplaza.presentation.screens.mainactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.noname.genuisplaza.R
import com.noname.genuisplaza.presentation.screens.usersfragment.UsersFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.fram,
            UsersFragment(), "Main").commit()

    }
}
