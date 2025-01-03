package com.zerock.apitest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zerock.apitest.retrofit.RetrofitClient
import com.zerock.apitest.retrofit.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserEditActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnUpdate: Button
    private var userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_edit)

        etName = findViewById(R.id.et_name_edit)
        etPhone = findViewById(R.id.et_phone_edit)
        etEmail = findViewById(R.id.et_email_edit)
        btnUpdate = findViewById(R.id.btn_update)

        // Intent로 받은 데이터 설정
        userId = intent.getLongExtra("USER_ID", 0)
        etName.setText(intent.getStringExtra("USER_NAME"))
        etPhone.setText(intent.getStringExtra("USER_PHONE"))
        etEmail.setText(intent.getStringExtra("USER_EMAIL"))

        // 업데이트 버튼 클릭 리스너
        btnUpdate.setOnClickListener {
            val updatedUser = User(
                id = userId,
                name = etName.text.toString(),
                phone = etPhone.text.toString(),
                email = etEmail.text.toString()
            )
            updateUser(updatedUser)
        }
    }

    private fun updateUser(user: User) {
        user.id?.let {
            RetrofitClient.instance.updateUser(it, user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UserEditActivity, "User updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@UserEditActivity, "Failed to update user", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@UserEditActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
