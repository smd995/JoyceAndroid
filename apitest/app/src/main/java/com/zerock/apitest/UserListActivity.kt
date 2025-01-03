package com.zerock.apitest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zerock.apitest.retrofit.RetrofitClient
import com.zerock.apitest.retrofit.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var addButton: Button
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton = findViewById(R.id.button)

        adapter = UserAdapter(users,
            onItemClick = { user ->
                val intent = Intent(this, UserEditActivity::class.java)
                intent.putExtra("USER_ID", user.id)
                intent.putExtra("USER_NAME", user.name)
                intent.putExtra("USER_PHONE", user.phone)
                intent.putExtra("USER_EMAIL", user.email)
                startActivity(intent)
            },
            onItemLongClick = { user ->
                deleteUser(user)
            }
        )

        recyclerView.adapter = adapter
        fetchUsers()

        // ADD 버튼 클릭 시 RegisterActivity로 이동
        addButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchUsers() {
        RetrofitClient.instance.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    users.clear()
                    users.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@UserListActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(user: User) {
        user.id?.let {
            RetrofitClient.instance.deleteUser(it).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UserListActivity, "User deleted", Toast.LENGTH_SHORT).show()
                        users.remove(user)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@UserListActivity, "Failed to delete user", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@UserListActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
