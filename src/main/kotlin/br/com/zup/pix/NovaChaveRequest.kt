package br.com.zup.pix

import br.com.zup.RegistraChavePixRequest
import br.com.zup.shared.validation.ChavePixValida
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import br.com.zup.TipoDeChave as TipoDeChaveGrpc
import br.com.zup.TipoDeConta as TipoDeContaGrpc

@ChavePixValida
@Introspected
data class NovaChaveRequest(
    @field:Size(max = 77)
    val chave: String?,

    @field:NotNull
    val tipo: TipoDeChave?,

    @field:NotNull
    val tipoDeConta: TipoDeConta?
) {
    fun toGrpcRequest(idCliente: UUID): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setChave(chave ?: "")
            .setIdCliente(idCliente.toString())
            .setTipoDeChave(TipoDeChaveGrpc.valueOf(tipo!!.name))
            .setTipoDeConta(TipoDeContaGrpc.valueOf(tipoDeConta!!.name))
            .build()
    }
}


