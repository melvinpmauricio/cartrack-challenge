package com.cartrack.challenge.views.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.cartrack.challenge.R
import com.cartrack.challenge.models.User
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserListAdapter :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private var users = mutableListOf<User>()
    var onUserClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUsers(userList: MutableList<User>?) {
        userList?.let {
            val start: Int = users.size + 1
            users.addAll(it)
            notifyItemRangeInserted(start, users.size)
        }
    }

    fun setUsers(users: MutableList<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun clearUsers() {
        users.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var user: User? = null

        init {
            itemView.setOnClickListener {
                user?.let {
                    onUserClick?.invoke(it)
                }
            }
        }

        fun bind(user: User?) {
            this.user = user
            user?.let {
                itemView.tvName.text = user.name
                itemView.tvCompany.text = user.company.name
                itemView.tvEmail.text = user.email
                itemView.tvWebsite.text = user.website

                val drawableInitials = buildNameInitials(user.name)
                itemView.ivImage.setImageDrawable(drawableInitials)
            }
        }

        private fun buildNameInitials(name: String): TextDrawable {
            val nameInitialBuilder = StringBuilder()
            val nameToken = name.split(" ")
            nameToken.forEach {
                if (it.isNotBlank()) {
                    nameInitialBuilder.append(it[0])
                }
            }

            return TextDrawable.builder()
                .beginConfig()
                .width(80)  // width in px
                .height(80) // height in px
                .textColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
                .fontSize(24)
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(
                    nameInitialBuilder.toString(),
                    ContextCompat.getColor(itemView.context, R.color.colorAccent)
                )
        }
    }
}