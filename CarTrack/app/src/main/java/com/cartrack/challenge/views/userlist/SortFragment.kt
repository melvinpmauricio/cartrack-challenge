package com.cartrack.challenge.views.userlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cartrack.challenge.R
import com.cartrack.challenge.models.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_sort.*

class SortFragment : BottomSheetDialogFragment() {
    private lateinit var sortListener: OnSortListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSortListener) {
            sortListener = context
        } else {
            throw IllegalStateException("Activity must implement OnSortListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        btnApply.setOnClickListener {
            val rdoSortCategoryId = rdgSortCategory.checkedRadioButtonId
            val rdoSortById = rdgSortBy.checkedRadioButtonId


            val category = when (rdoSortCategoryId) {
                R.id.rdoDefault -> User.ID
                R.id.rdoEmail -> User.EMAIL
                R.id.rdoName -> User.NAME
                else -> User.WEBSITE
            }

            val order = when (rdoSortById) {
                R.id.rdoAsc -> "ASC"
                else -> "DESC"
            }

            sortListener.onSortUsers(category, order)
            dismiss()
        }
    }

    companion object {
        // Incase we do have argument(s), apply
        fun newInstance() = SortFragment().apply {
            arguments = Bundle().apply {

            }
        }

        const val TAG = "SortFragment"
    }
}