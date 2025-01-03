package com.zerock.apitest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zerock.apitest.retrofit.User

class UserAdapter(private val users: List<User>,
                  private val onItemClick: (User) -> Unit,
                  private val onItemLongClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idTextView: TextView = view.findViewById(R.id.tv_id)
        val nameTextView: TextView = view.findViewById(R.id.tv_name)
        val phoneTextView: TextView = view.findViewById(R.id.tv_phone)
        val emailTextView: TextView = view.findViewById(R.id.tv_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.idTextView.text = "Id: ${user.id}"
        holder.nameTextView.text = "Name: ${user.name}"
        holder.phoneTextView.text = "Phone: ${user.phone}"
        holder.emailTextView.text = "Email: ${user.email}"

        // 클릭 리스너
        holder.itemView.setOnClickListener {
            onItemClick(user)
        }

        // 롱클릭 리스너
        holder.itemView.setOnLongClickListener {
            onItemLongClick(user)
            true
        }
    }

    override fun getItemCount(): Int = users.size
}
