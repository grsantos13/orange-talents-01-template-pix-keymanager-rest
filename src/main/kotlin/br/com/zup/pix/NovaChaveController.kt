package br.com.zup.pix

import br.com.zup.KeymanagerRegistraChavePixGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{idCliente}/pix")
class NovaChaveController(
    private val registraChaveClient: KeymanagerRegistraChavePixGrpcServiceGrpc.KeymanagerRegistraChavePixGrpcServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post
    @Operation(summary = "Registra chaves dos clientes no sistema")
    @ApiResponses(
        ApiResponse(
            headers = [Header(
                name = "location",
                description = "Location contendo a URI para obtenção dos dados do token"
            )],
            responseCode = "201",
            description = "Chave registrada com sucesso"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Erro de validação"
        )
    )
    @Status(HttpStatus.CREATED)
    fun registrar(
        @Parameter(description = "id do cliente") @PathVariable idCliente: UUID,
        @Body @Valid request: NovaChaveRequest
    ): HttpResponse<Any> {
        logger.info("Cliente [$idCliente]: Registrando chave pix $request")

        val grpcRequest = request.toGrpcRequest(idCliente)
        val chavePixResponse = registraChaveClient.registrar(grpcRequest)

        logger.info("Cliente [$idCliente]: Chave pix registrada com sucesso [${chavePixResponse.idPix}]")
        val uri = UriBuilder.of("/api/v1/clientes/{idCliente}/pix/{idPix}")
            .expand(mutableMapOf("idCliente" to idCliente, "idPix" to chavePixResponse.idPix))
        return HttpResponse.created(uri)
    }
}