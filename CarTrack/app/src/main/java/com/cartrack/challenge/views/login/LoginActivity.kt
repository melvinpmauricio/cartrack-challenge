package com.cartrack.challenge.views.login

import android.content.Intent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cartrack.challenge.R
import com.cartrack.challenge.base.BaseActivity
import com.cartrack.challenge.models.CarTrackError
import com.cartrack.challenge.utils.StringUtils
import com.cartrack.challenge.utils.Utils
import com.cartrack.challenge.views.userlist.UserListActivity
import kotlinx.android.synthetic.main.view_login.*

class LoginActivity : BaseActivity(), OnCountrySelectedListener {
    private val viewModel by viewModel<LoginViewModel>()
    private var selectedCountry: String? = null

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun bindView() {
        viewModel.initializeDb()
        etPassword.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    login(etUsername.text.toString(), etPassword.text.toString())
                }
            }
            true
        }

        btnLogin.setOnClickListener {
            login(etUsername.text.toString(), etPassword.text.toString())
        }

        tvCountry.setOnClickListener { viewModel.showCountries() }
    }

    override fun bindObservers() {
        viewModel.isLoggedIn.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, UserListActivity::class.java))
            }
        })

        viewModel.error.observe(this, Observer {
            when (it.errorCode) {
                CarTrackError.ERR_DB_EMPTY_RESULT_SET -> Toast.makeText(
                    this,
                    R.string.err_login,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.countryList.observe(this, Observer {
            val countryFragment = CountryFragment.newInstance(ArrayList(it), selectedCountry)
            countryFragment.show(supportFragmentManager, CountryFragment.TAG)
        })
    }

    private fun login(username: String, password: String) {
        val minUsernameCount = resources.getInteger(R.integer.min_username_count)
        val minPasswordCount = resources.getInteger(R.integer.min_password_count)

        Utils.hideKeyboard(this)

        if (username.isEmpty() || username.isBlank()) {
            etUsername.error = getString(R.string.err_username_empty)
            return
        } else if (password.isEmpty() || password.isBlank()) {
            etPassword.error = getString(R.string.err_password_empty)
            return
        } else if (username.length < minUsernameCount) {
            etUsername.error = getString(R.string.err_username_min_length, minUsernameCount)
            return
        } else if (password.length < minPasswordCount) {
            etPassword.error = getString(R.string.err_password_min_length, minPasswordCount)
            return
        } else if (selectedCountry == null) {
            Toast.makeText(this, R.string.err_select_country, Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.loginUser(
            username,
            StringUtils.md5(password)
        )
    }

    override fun onCountrySelected(country: String?) {
        this.selectedCountry = country
        tvCountry.text = ""
        country?.let {
            tvCountry.text = country
        }
    }
}