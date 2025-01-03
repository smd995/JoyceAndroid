package com.zerock.calculator

import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

// RetrofitClient와 Interface는 주어진 내용 그대로 사용합니다.
object RetroClient {
    val REST_BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    val retroInterface: RetroInterface = Retrofit.Builder()
        .baseUrl(REST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetroInterface::class.java)
}

interface RetroInterface {
    @GET("todos/")
    fun getTodos(): Call<List<Todo>>
}

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

// 단위 테스트 클래스
class RetroClientTest {

    @Test
    fun testGetTodos() {
        // Given: Retrofit 인터페이스 생성
        val service = RetroClient.retroInterface

        // When: getTodos API 호출
        val call: Call<List<Todo>> = service.getTodos()
        val response: Response<List<Todo>> = call.execute() // 동기 호출

        // Then: 결과 검증
        assertNotNull(response.body(), "Response body should not be null")
        assertEquals(200, response.code(), "Response code should be 200")

        val todos = response.body()
        assertNotNull(todos, "Todos list should not be null")
        assert(todos!!.isNotEmpty()) { "Todos list should not be empty" }

        // 첫 번째 항목 확인 (예: id, title 등)
        val firstTodo = todos[0]
        assertNotNull(firstTodo.title, "First todo's title should not be null")
        println("First Todo: $firstTodo")
    }
}