package br.com.zup.pix

import br.com.zup.ConsultaChavePixResponse
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import br.com.zup.Conta as ContaGrpc
import br.com.zup.Titular as TitularGrpc

data class ChavePixResponse(
    val IdPix: UUID,
    val IdCliente: UUID,
    val tipo: TipoDeChave,
    val chave: String,
    val conta: Conta,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val registradaEm: LocalDateTime
) {

    companion object {
        fun from(chaveResponse: ConsultaChavePixResponse): ChavePixResponse {
            return ChavePixResponse(
                IdPix = UUID.fromString(chaveResponse.idPix),
                IdCliente = UUID.fromString(chaveResponse.idCliente),
                tipo = TipoDeChave.valueOf(chaveResponse.tipoDeChave.name),
                chave = chaveResponse.chave,
                conta = Conta(chaveResponse.conta),
                registradaEm = LocalDateTime.ofEpochSecond(
                    chaveResponse.criadaEm.seconds,
                    chaveResponse.criadaEm.nanos,
                    ZoneOffset.UTC
                )
            )
        }
    }
}

data class Conta(
    val instituicao: String,
    val agencia: String,
    val numeroDaConta: String,
    val titular: Titular,
    val tipoDeConta: TipoDeConta
) {
    constructor(conta: ContaGrpc) : this(
        conta.instituicao,
        conta.agencia,
        conta.numero,
        Titular(conta.titular),
        TipoDeConta.valueOf(conta.tipoDeConta.name)
    )
}

data class Titular(
    val nome: String,
    val cpf: String
) {
    constructor(titular: TitularGrpc) : this(
        titular.nome,
        titular.cpf
    )
}
