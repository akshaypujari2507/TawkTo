package com.tawkto.ui.views

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.greenlight.di.Injection
import com.tawkto.R
import com.tawkto.data.local.entities.Users
import com.tawkto.ui.adapter.UsersAdapter
import com.tawkto.ui.interfaces.OnUserClicked
import com.tawkto.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), OnUserClicked {

    private lateinit var viewModel: MainActivityViewModel
    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    private lateinit var mGridLayoutManager: GridLayoutManager
    lateinit var mAdapter: UsersAdapter

    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, Injection.provideMainActivityViewModelFactory(this))
            .get(MainActivityViewModel::class.java)


        initView()
        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {
            getUserList()
        }


    }

    private fun fetchUsers(scope: Int) {
        viewModel.getRemoteRecord(scope)

    }

    private suspend fun getUserList() {
        viewModel.getUsers()?.observe(this, Observer<List<Users>> {
            setUpAdapter(it)
        })
    }

    private fun setUpAdapter(users: List<Users>) {
        if (users.size > 0) {
            mAdapter.users = users
            tv_noRecord.visibility = View.GONE
            recycleView.visibility = View.VISIBLE
        } else {
            fetchUsers(users.size)
            tv_noRecord.visibility = View.VISIBLE
            recycleView.visibility = View.GONE
            tv_noRecord.setText(resources.getString(R.string.noRecords))
        }
        shimmerFrameLayout.visibility = View.GONE
//        isLoading = false
    }


    private fun initView() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mAdapter.filter.filter(charSequence.toString())
                shimmerFrameLayout.visibility = View.GONE
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun initRecyclerView() {
        configureRecyclerAdapter(resources.configuration.orientation)

        recycleView.setHasFixedSize(true);
        mAdapter = UsersAdapter(this@MainActivity)
        recycleView.adapter = mAdapter

    }

    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(
            this,
            if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE
        )
        recycleView.setLayoutManager(mGridLayoutManager)

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = mGridLayoutManager.childCount
                val totalItemCount = mGridLayoutManager.itemCount
                val firstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition()
                println("outside totalItemCount=$totalItemCount, visibleItemCount=$visibleItemCount, firstVisibleItemPosition=$firstVisibleItemPosition")
//                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
//                    println("inside first totalItemCount=$totalItemCount, visibleItemCount=$visibleItemCount, firstVisibleItemPosition=$firstVisibleItemPosition")
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && !isLoading) {
                        isLoading = true
                        println("inside totalItemCount=$totalItemCount, visibleItemCount=$visibleItemCount, firstVisibleItemPosition=$firstVisibleItemPosition")
                        fetchUsers(totalItemCount+16)
                        shimmerFrameLayout.visibility = View.VISIBLE
                    }//                    && totalItemCount >= ClothesFragment.itemsCount
//                }
            }
        })


    }


    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(user: Users) {
//        TODO("Not yet implemented")
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("login", user.login)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
}