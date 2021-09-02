package com.tawkto.ui.views

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.greenlight.di.Injection
import com.squareup.picasso.Picasso
import com.tawkto.R
import com.tawkto.data.local.entities.UserDetails
import com.tawkto.ui.viewmodel.UserDetailsActivityViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UserDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: UserDetailsActivityViewModel
    private lateinit var login: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        login = intent.getStringExtra("login").toString()

        viewModel = ViewModelProviders.of(
            this, Injection.provideUserDetailsActivityViewModelFactory(
                this
            )
        )
            .get(UserDetailsActivityViewModel::class.java)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        GlobalScope.launch(Dispatchers.Main) {
            getUserDetails()
        }


    }

    private fun fetchUsers(login: String) {
        viewModel.getRemoteRecord(login)

    }

    private suspend fun getUserDetails() {
        viewModel.getUserDetails(login)?.observe(this, Observer<UserDetails> {
            if (it == null) {
                fetchUsers(login)
            } else {
                // setup view
                initView(it)
//                toast(it.name!!)
            }
        })
    }

    fun initView(userDetails: UserDetails) {

        Picasso.get()
            .load(userDetails.avatar_url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(iv_main);
        tv_follower.text = "follower: " + userDetails.followers
        tv_following.text = "following: " + userDetails.following
        tv_name.text = "name: " + userDetails.name
        tv_company.text = "company: " + userDetails.company
        val blogUrl = "blog: <a href='${userDetails.blog}'>${userDetails.blog}</a>"
        tv_blog.setText(Html.fromHtml(blogUrl, Html.FROM_HTML_MODE_COMPACT))
        val htmlURL = "url: <a href='${userDetails.html_url}'>${userDetails.html_url}</a>"
        tv_url.setText(Html.fromHtml(htmlURL, Html.FROM_HTML_MODE_COMPACT))

        if (userDetails.notes != null){
            et_notes.setText(userDetails.notes)
        }

        bt_save.setOnClickListener {
            updateNotes(userDetails)
        }

    }

    fun updateNotes(userDetails: UserDetails) {
        val etNote = et_notes.text
        if (etNote == null)
            return

        if (!etNote.equals(userDetails.notes)) {
            userDetails.notes = etNote.toString()
            viewModel.getUserDetailsUpdate(userDetails)
            toast("saved!")
        }
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}