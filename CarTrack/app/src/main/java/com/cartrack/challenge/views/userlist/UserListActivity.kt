package com.cartrack.challenge.views.userlist

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.cartrack.challenge.R
import com.cartrack.challenge.base.BaseActivity
import com.cartrack.challenge.views.map.UserMapActivity
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.view_progress.*
import kotlinx.android.synthetic.main.view_toolbar.*

class UserListActivity : BaseActivity(), OnSortListener {
    private lateinit var adapter: UserListAdapter
    private val viewModel by viewModel<UserListViewModel>()

    override fun getLayoutRes(): Int {
        return R.layout.activity_user_list
    }

    override fun bindView() {
        setUpToolbar()
        setUpUserList()
        viewModel.getUsers()
    }

    override fun bindObservers() {
        viewModel.users.observe(this, Observer {
            adapter.setUsers(it)
        })

        viewModel.loading.observe(this, Observer {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        ivBack.setOnClickListener { finish() }
        tvTitle.text = getString(R.string.text_users)
    }

    private fun setUpUserList() {
        adapter = UserListAdapter()
        rvUserList.adapter = adapter

        adapter.onUserClick = { user ->
            val intent = Intent(this, UserMapActivity::class.java).apply {
                putExtra(UserMapActivity.EXTRA_LAT, user.address.geo.lat)
                putExtra(UserMapActivity.EXTRA_LNG, user.address.geo.lng)
            }
            startActivity(intent)
        }
    }

    private fun showSortFragment() {
        val sortFragment = SortFragment.newInstance()
        sortFragment.show(supportFragmentManager, SortFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> showSortFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSortUsers(category: String, order: String) {
        viewModel.sortUsers(category, order)
    }
}