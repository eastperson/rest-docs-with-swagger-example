package com.ep.example.sample

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sample")
class SampleApi {

    @GetMapping("/string")
    fun str(): String = "hello!!"

    @GetMapping("/response")
    fun response(): ResponseEntity<Good> = ResponseEntity.ok(Good("sample"))

    data class Good(
        val name: String
    )

    @GetMapping
    fun get(): ResponseEntity<Response> {
        val sample = Sample(name = "sample", price = 5000, type = HttpMethodType.GET)
        val response = Response(
            status = HttpStatus.OK,
            code = HttpStatus.OK.value(),
            message = "success",
            data = sample
        )

        return ResponseEntity.ok().body(response)
    }

    @PostMapping
    fun post(@RequestBody request: Request): ResponseEntity<Response> {
        return ResponseEntity.ok(
            Response(
                status = HttpStatus.OK,
                code = HttpStatus.OK.value(),
                message = "success",
                data = Sample(name = request.name, price = request.price, type = HttpMethodType.POST)
            )
        )
    }

    @PatchMapping
    fun patch(@RequestBody request: Request): ResponseEntity<Response> {
        return ResponseEntity.ok(
            Response(
                status = HttpStatus.OK,
                code = HttpStatus.OK.value(),
                message = "success",
                data = Sample(name = request.name, price = request.price, type = HttpMethodType.PATCH)
            )
        )
    }

    @PutMapping
    fun put(@RequestBody request: Request): ResponseEntity<Response> {
        return ResponseEntity.ok(
            Response(
                status = HttpStatus.OK,
                code = HttpStatus.OK.value(),
                message = "success",
                data = Sample(name = request.name, price = request.price, type = HttpMethodType.PUT)
            )
        )
    }

    @DeleteMapping
    fun delete(): ResponseEntity<Response> {
        val sample = Sample(name = "sample", price = 5000, HttpMethodType.DELETE)

        return ResponseEntity.ok(
            Response(
                status = HttpStatus.OK,
                code = HttpStatus.OK.value(),
                message = "success",
                data = sample
            )
        )
    }

    data class Sample(
        val name: String,
        val price: Int,
        val type: HttpMethodType
    )

    data class Request(
        val name: String,
        val price: Int
    )

    data class Response(
        val status: HttpStatus,
        val code: Int,
        val message: String,
        val data: Any
    )

    enum class HttpMethodType {
        GET, POST, PATCH, PUT, DELETE
    }
}
