package br.com.zup.pix

import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import java.util.*

@Controller("/api/v1/clientes/{idCliente}/pix")
class RemoveChaveController(
    private val removeChaveClient: KeymanagerRemoveChavePixGrpcServiceGrpc.KeymanagerRemoveChavePixGrpcServiceBlockingStub
) {

    @Delete("/{idPix}")
    fun remover(@PathVariable idCliente: UUID, @PathVariable idPix: UUID): HttpResponse<Any> {
        val removeChavePixRequest = RemoveChavePixRequest.newBuilder()
            .setIdCliente(idCliente.toString())
            .setIdPix(idPix.toString())
            .build()

        removeChaveClient.remover(removeChavePixRequest)

        return HttpResponse.ok()
    }
}