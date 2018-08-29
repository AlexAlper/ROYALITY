package com.evgeniy.royality

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.evgeniy.royality.Data.Repository
import javax.inject.Inject

class BonusFragment : Fragment() {

    lateinit var bill1: TextView
    lateinit var bill2: TextView
    lateinit var progressBar: ProgressBar
    lateinit var rootView: LinearLayout

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.bonus_fragment, container, false)
        App.component.inject(this)

        (activity as AppCompatActivity).supportActionBar?.title = arguments?.getString(TITLE)

        bill1 = view.findViewById(R.id.cor_bill_1)
        bill2 = view.findViewById(R.id.cor_bill_2)
        progressBar = view.findViewById(R.id.progress_bar)
        rootView = view.findViewById(R.id.root_view)

        setLoadingProgressVisibility(true)
        repository.getBill(sharedPreferences.getString(NAME, ""))
                .subscribe(
                        {
                            bill1.text = it.first
                            bill2.text = it.second
                        },
                        {
                            showMessage(getString(R.string.error))
                        },
                        {
                            setLoadingProgressVisibility(false)
                        }
                )

        return view
    }

    private fun showMessage(msg: String) {
        Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT).show()
    }

    private fun setLoadingProgressVisibility(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
        rootView.visibility = if (visibility) View.GONE else View.VISIBLE
    }
}