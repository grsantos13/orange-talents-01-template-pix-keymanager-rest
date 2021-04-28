package br.com.zup.pix

import br.com.zup.KeymanagerRegistraChavePixGrpcServiceGrpc
import br.com.zup.RegistraChavePixResponse
import br.com.zup.shared.grpc.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class NovaChaveControllerTest() {
    @field:Inject
    lateinit var grpcClient: KeymanagerRegistraChavePixGrpcServiceGrpc.KeymanagerRegistraChavePixGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve criar nova chave pix`() {

        val idCliente = UUID.randomUUID().toString()
        val idPix = UUID.randomUUID().toString()

        val respostaGrpc = RegistraChavePixResponse.newBuilder()
            .setIdCliente(idCliente)
            .setIdPix(idPix)
            .build()

        given(grpcClient.registrar(Mockito.any())).willReturn(respostaGrpc)


        val novaChavePix = NovaChaveRequest(
            tipoDeConta = TipoDeConta.CONTA_CORRENTE,
            chave = "teste@teste.com.br",
            tipo = TipoDeChave.EMAIL
        )

        val request = HttpRequest.POST("/api/v1/clientes/$idCliente/pix", novaChavePix)
        val response = client.toBlocking().exchange(request, NovaChaveRequest::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(idPix))
    }

    @Test
    internal fun `nao deve criar chave com parametros invalidos`() {

        val idCliente = UUID.randomUUID().toString()
        val idPix = UUID.randomUUID().toString()

        val novaChavePix = NovaChaveRequest(
            tipoDeConta = null,
            chave = null,
            tipo = null
        )

        val request = HttpRequest.POST("/api/v1/clientes/$idCliente/pix", novaChavePix)
        val response = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(request, NovaChaveRequest::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class GrpcFactory {

        @Singleton
        fun stub() =
            Mockito.mock(KeymanagerRegistraChavePixGrpcServiceGrpc.KeymanagerRegistraChavePixGrpcServiceBlockingStub::class.java)
    }
}