package br.com.zup.pix

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.ConsultaChavePixRequest.ConsultaPorClienteEIdPix
import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
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
class ConsultaChaveController(
    private val consultaChaveClient: KeymanagerConsultaChavePixGrpcServiceGrpc.KeymanagerConsultaChavePixGrpcServiceBlockingStub
) {

    @Get("/{idPix}")
    @Operation(summary = "Remove chaves dos clientes no sistema")
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
            description = "Cliente não encontrado e/ou chave pix não encontrada"
        )
    )
    fun consultar(
        @Parameter(description = "id do cliente") @PathVariable idCliente: UUID,
        @Parameter(description = "id da chave PIX") @PathVariable idPix: UUID
    ): HttpResponse<Any> {
        val porClienteEIdPix = ConsultaPorClienteEIdPix.newBuilder()
            .setIdCliente(idCliente.toString())
            .setIdPix(idPix.toString())
            .build()
        val request = ConsultaChavePixRequest.newBuilder()
            .setIdPixEIdCliente(porClienteEIdPix)
            .build()
        val chavePixResponse = consultaChaveClient.consultar(request)
        return HttpResponse.ok(DetalhesChavePixResponse.from(chavePixResponse))
    }
}