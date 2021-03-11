package br.com.zup.pix

import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import java.util.*

@Controller("/api/v1/clientes/{idCliente}/pix")
class ListaChaveController(
    val listaChaveClient: KeymanagerListaChavesPixGrpcServiceGrpc.KeymanagerListaChavesPixGrpcServiceBlockingStub
) {

    @Get
    fun listar(@PathVariable idCliente: UUID): HttpResponse<Any> {
        val request = ListaChavesPixRequest.newBuilder()
            .setIdCliente(idCliente.toString())
            .build()
        val chavesPixResponse = listaChaveClient.listar(request)
        val responseList = chavesPixResponse.chavesList.map { ChavePixResponse.from(it) }
        return HttpResponse.ok(responseList)
    }
}