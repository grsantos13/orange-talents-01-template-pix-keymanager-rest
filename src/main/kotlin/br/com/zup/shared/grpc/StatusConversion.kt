package br.com.zup.shared.grpc

import io.micronaut.http.HttpStatus

enum class StatusConversion(
    val httpStatus: HttpStatus,
    val defaultMessage: String
) {
    OK(HttpStatus.OK, ""),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado ao processar a requisição"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "Os dados enviados na requisição estão inválidos"),
    NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    ALREADY_EXISTS(HttpStatus.UNPROCESSABLE_ENTITY, ""),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "Você não possui privilégios para acessar esse recurso"),
    FAILED_PRECONDITION(HttpStatus.PRECONDITION_FAILED, ""),
    UNIMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, ""),
    INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado ao processar a requisição"),
    UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "O serviço não está disponível"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "É necessário estar logado para acessar o recurso solicitado");
}