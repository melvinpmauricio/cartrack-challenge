package com.cartrack.challenge.views.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RadioButton
import com.cartrack.challenge.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_country.*

class CountryFragment : BottomSheetDialogFragment() {
    private var currentState: Int = 0
    private var dialog: BottomSheetDialog? = null
    private lateinit var countrySelectListener: OnCountrySelectedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCountrySelectedListener) {
            countrySelectListener = context
        } else {
            throw IllegalStateException("Activity must implement OnCountrySelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        setUpCountries()
        setUpButton()
    }

    private fun setUpCountries() {
        val countries = arguments?.getStringArrayList(ARGS_COUNTRIES)
        val selectedCountry = arguments?.getString(ARGS_SELECTED)

        countries?.sort()
        countries?.forEach {
            val rdo = View.inflate(activity, R.layout.view_country, null) as RadioButton
            rdo.text = it
            rdgCountries.addView(rdo)
            if (it == selectedCountry) {
                rdgCountries.check(rdo.id)
            }
        }
    }

    private fun setUpButton() {
        btnDone.setOnClickListener {
            val selected = rdgCountries.checkedRadioButtonId
            if (selected != -1) {
                val rdo = rdgCountries.findViewById(selected) as RadioButton
                countrySelectListener.onCountrySelected(rdo.text.toString())
            } else {
                countrySelectListener.onCountrySelected(null)
            }
            dialog?.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior: BottomSheetBehavior<View>? = BottomSheetBehavior.from(bottomSheet!!)
            val layoutParams = bottomSheet.layoutParams
            val windowHeight = getWindowHeight()
            layoutParams?.let {
                it.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (currentState != BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state =
                            if (slideOffset > 0) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_HIDDEN
                    }
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    currentState = newState
                    if (currentState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                    }
                }
            })
            behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog!!
    }

    private fun getWindowHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        // Calculate window height for fullscreen use minus status bar height
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels - (statusBarHeight + 300)
    }

    companion object {
        fun newInstance(countries: ArrayList<String>, selectedCountry: String?) =
            CountryFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARGS_COUNTRIES, countries)
                    putString(ARGS_SELECTED, selectedCountry)
                }
            }

        const val TAG = "CountryFragment"
        private const val ARGS_COUNTRIES = "args_countries"
        private const val ARGS_SELECTED = "args_selected"
    }
}