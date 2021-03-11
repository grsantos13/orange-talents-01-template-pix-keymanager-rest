package br.com.zup.pix

import br.com.zup.ConsultaChavePixResponse
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

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
                conta = Conta(
                    instituicao = chaveResponse.conta.instituicao,
                    agencia = chaveResponse.conta.agencia,
                    numeroDaConta = chaveResponse.conta.numero,
                    titular = Titular(
                        nome = chaveResponse.conta.titular.nome,
                        cpf = chaveResponse.conta.titular.cpf
                    ),
                    tipoDeConta = TipoDeConta.valueOf(chaveResponse.conta.tipoDeConta.name)
                ),
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
)

data class Titular(
    val nome: String,
    val cpf: String
)
