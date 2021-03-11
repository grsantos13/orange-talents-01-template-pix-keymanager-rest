package br.com.zup.pix

import br.com.zup.ConsultaChavePixResponse
import br.com.zup.ListaChavesPixResponse.ChavePix
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import br.com.zup.Conta as ContaGrpc
import br.com.zup.Titular as TitularGrpc

data class DetalhesChavePixResponse(
    val IdPix: UUID,
    val IdCliente: UUID,
    val tipo: TipoDeChave,
    val chave: String,
    val conta: Conta,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val registradaEm: LocalDateTime
) {

    companion object {
        fun from(chaveResponse: ConsultaChavePixResponse): DetalhesChavePixResponse {
            return DetalhesChavePixResponse(
                IdPix = UUID.fromString(chaveResponse.idPix),
                IdCliente = UUID.fromString(chaveResponse.idCliente),
                tipo = TipoDeChave.valueOf(chaveResponse.tipoDeChave.name),
                chave = chaveResponse.chave,
                conta = Conta(chaveResponse.conta),
                registradaEm = chaveResponse.registradaEm.let {
                    LocalDateTime.ofEpochSecond(
                        it.seconds,
                        it.nanos,
                        ZoneOffset.UTC
                    )
                }
            )
        }
    }
}

data class ChavePixResponse(
    val idPix: UUID,
    val chave: String,
    val tipo: TipoDeChave,
    val tipoDeConta: TipoDeConta,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val registradaEm: LocalDateTime
) {
    companion object {
        fun from(chave: ChavePix): ChavePixResponse {
            return ChavePixResponse(
                UUID.fromString(chave.idPix),
                chave.chave,
                TipoDeChave.valueOf(chave.tipoDaChave.name),
                tipoDeConta = TipoDeConta.valueOf(chave.tipoDaConta.name),
                registradaEm = chave.registradaEm.let {
                    LocalDateTime.ofEpochSecond(
                        it.seconds,
                        it.nanos,
                        ZoneOffset.UTC
                    )
                }
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
