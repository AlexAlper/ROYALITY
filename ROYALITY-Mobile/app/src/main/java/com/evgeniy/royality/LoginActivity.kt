package com.evgeniy.royality

import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.evgeniy.royality.Data.Repository
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        App.component.inject(this)

        if (isLogin()) login()

        btn_login.setOnClickListener {
            if (et_number.text.toString().isEmpty() ||
                    et_pass.text.toString().isEmpty()) {
                showMessage(getString(R.string.empty_fields))
                return@setOnClickListener
            }

            setLoadingProgressVisibility(true)
            repository.login(et_number.text.toString())
                    .subscribe(
                            {
                                sharedPrefs.edit()
                                        .putString(NAME, it.corName)
                                        .putString(CITY, it.city)
                                        .putBoolean(LOGIN, true)
                                        .apply()
                            },
                            {
                                setLoadingProgressVisibility(false)
                                showMessage(getString(R.string.error))
                            },
                            {
                                if (isLogin()) {
                                    login()
                                } else{
                                    showMessage(getString(R.string.user_not_found))
                                    setLoadingProgressVisibility(false)
                                }
                            }
                    )
        }
    }

    private fun showMessage(msg: String) {
        Toast.makeText(
                this,
                msg,
                Toast.LENGTH_SHORT).show()
    }

    private fun setLoadingProgressVisibility(visibility: Boolean) {
        progress_bar.visibility = if (visibility) View.VISIBLE else View.GONE
        root_view.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    private fun login() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun isLogin(): Boolean = sharedPrefs.getBoolean(LOGIN, false)
}