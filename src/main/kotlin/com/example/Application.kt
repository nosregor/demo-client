package com.example

import com.example.demo.DemoServiceClient
import io.grpc.ManagedChannelBuilder
//import io.micronaut.runtime.Micronaut.build
import kotlinx.coroutines.runBlocking

suspend fun main(args: Array<String>) {
	val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
		.usePlaintext()
		.build()
	val client = DemoServiceClient(channel)


//	val reply = runBlocking { client.sayHello("John") }
	val reply = client.sayHello("John")
	println(reply.message)

	val replyAgain = client.sayHelloAgain("John")
	println(replyAgain.message)

//	val response = runBlocking { client.saveUser("John", "Doe", "123456") }
	val response = client.saveUser("John", "Doe", "123456")
	println(response.id)
	println(response.name)
	println(response.lastName)

	val responseStream = client.saveUserStream()
	println(responseStream)


	client.shutdown()

//	build()
//	.args(*args)
//	.packages("com.example")
//	.start()
}

