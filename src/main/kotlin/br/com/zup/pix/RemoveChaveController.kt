package br.com.zup.pix

import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.util.*

@Controller("/api/v1/clientes/{idCliente}/pix")
class RemoveChaveController(
    private val removeChaveClient: KeymanagerRemoveChavePixGrpcServiceGrpc.KeymanagerRemoveChavePixGrpcServiceBlockingStub
) {

    @Delete("/{idPix}")
    @Operation(summary = "Remove chaves dos clientes no sistema")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Chave removida com sucesso"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado e/ou chave pix não encontrada"
        )
    )
    fun remover(
        @Parameter(description = "id do cliente") @PathVariable idCliente: UUID,
        @Parameter(description = "id da chave PIX") @PathVariable idPix: UUID
    ): HttpResponse<Any> {
        val removeChavePixRequest = RemoveChavePixRequest.newBuilder()
            .setIdCliente(idCliente.toString())
            .setIdPix(idPix.toString())
            .build()

        removeChaveClient.remover(removeChavePixRequest)

        return HttpResponse.ok()
    }
}