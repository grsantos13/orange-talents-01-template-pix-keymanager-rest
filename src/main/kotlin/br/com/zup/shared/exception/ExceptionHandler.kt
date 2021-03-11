package br.com.zup.shared.exception

import br.com.zup.shared.grpc.StatusConversion
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        val status = StatusConversion.valueOf(exception.status.code.name)
        val message = exception.status.description ?: status.defaultMessage

        return HttpResponse.status<JsonError>(status.httpStatus)
            .body(JsonError(message))
    }
}