package br.com.zup

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "keymanager",
        version = "1.0",
        description = "API Rest para gerenciamento de chaves PIX como desafio da Orange Stack."
    )
)
object Application

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("br.com.zup")
        .start()
}

