package com.example.a366pi

// API Methods via retrofit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Added classes to GET and PUSH custom data
data class CreateUserRequest(val employeeFirstname: String, val employeeLastname: String,val employeeID: Int, val employeeEmail: String)
data class CreateUserResponse(val employeeID: Int, val employeeFirstname: String, val employeeLastname: String,val employeeEmail: String, val createdAt: String)

// API interface
interface ApiService {
    @GET("users?page=2")
    suspend fun getUsers(): UserResponse

    @POST("users")
    suspend fun createUser(@Body request: CreateUserRequest): Response<CreateUserResponse>
}