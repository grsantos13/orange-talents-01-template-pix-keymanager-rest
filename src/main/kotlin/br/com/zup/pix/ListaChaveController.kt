package br.com.zup.pix

import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*

@Controller("/api/v1/clientes/{idCliente}/pix")
class ListaChaveController(
    val listaChaveClient: KeymanagerListaChavesPixGrpcServiceGrpc.KeymanagerListaChavesPixGrpcServiceBlockingStub
) {

    @Get
    @Operation(summary = "Lista todas as chaves de um cliente")
    @ApiResponses(
        ApiResponse(
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = DetalhesChavePixResponse::class)
            )],
            responseCode = "200",
            description = "Busca realizada com sucesso"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        )
    )
    fun listar(@Parameter(description = "Id do cliente no sistema") @PathVariable idCliente: UUID): HttpResponse<Any> {
        val request = ListaChavesPixRequest.newBuilder()
            .setIdCliente(idCliente.toString())
            .build()
        val chavesPixResponse = listaChaveClient.listar(request)
        val responseList = chavesPixResponse.chavesList.map { ChavePixResponse.from(it) }
        return HttpResponse.ok(responseList)
    }
}