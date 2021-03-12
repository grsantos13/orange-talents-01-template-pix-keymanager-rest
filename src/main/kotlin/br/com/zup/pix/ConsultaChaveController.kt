package br.com.zup.pix

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.ConsultaChavePixRequest.ConsultaPorClienteEIdPix
import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.tracing.annotation.NewSpan
import java.util.*

@Controller("/api/v1/clientes/{idCliente}/pix")
class ConsultaChaveController(
    private val consultaChaveClient: KeymanagerConsultaChavePixGrpcServiceGrpc.KeymanagerConsultaChavePixGrpcServiceBlockingStub
) {

    @Get("/{idPix}")
    @NewSpan
    fun consultar(@PathVariable idCliente: UUID, @PathVariable idPix: UUID): HttpResponse<Any> {
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