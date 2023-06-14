package com.example.demo

import com.example.*
import io.grpc.ManagedChannel
import jakarta.inject.Singleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class DemoServiceClient(private val channel: ManagedChannel) {
    private val stub: DemoServiceGrpcKt.DemoServiceCoroutineStub = DemoServiceGrpcKt.DemoServiceCoroutineStub(channel)

    suspend fun sayHello(name: String): HelloReply {
        val request = HelloRequest.newBuilder()
            .setName(name)
            .build()
        return stub.sayHello(request)
    }

    suspend fun sayHelloAgain(name: String): HelloReply {
        val request = HelloRequest.newBuilder()
            .setName(name)
            .build()
        return stub.sayHelloAgain(request)
    }

    suspend fun saveUser(name: String, lastName: String, document: String): UserResponse {
        val request = SaveUserRequest.newBuilder()
            .setName(name)
            .setLastName(lastName)
            .setDocument(document)
            .build()
        return stub.saveUser(request)
    }

   suspend fun saveUserStream() {
        return stub.saveUserStream(requests).collect { response ->
            println("Response: " + response.id)
        }
    }

    val requests = generateOutgoingRequests()

    private fun generateOutgoingRequests(): Flow<SaveUserRequest> = flow {

        val request1 = SaveUserRequest.newBuilder()
                .setName("Eduardo")
                .setLastName("Silva")
                .setDocument("05262438594")
                .build()

        val request2 = SaveUserRequest.newBuilder()
                .setName("Carol")
                .setLastName("Souza")
                .setDocument("07262438594")
                .build()

        val request3 = SaveUserRequest.newBuilder()
                .setName("Murilo")
                .setLastName("Oliveira")
                .setDocument("09262438594")
                .build()

        val requests = listOf(request1, request2, request3)

        for (request in requests) {
            println("Request: " + request.name)
            emit(request)
            delay(1000)
        }
    }

    fun shutdown() {
        channel.shutdown()
    }
}