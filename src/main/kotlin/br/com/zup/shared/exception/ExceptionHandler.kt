package br.com.zup.shared.exception

import br.com.zup.shared.grpc.StatusConversion
import io.grpc.StatusRuntimeException
import io.jaegertracing.internal.JaegerTracer
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ExceptionHandler(
    private val tracer: JaegerTracer
) : ExceptionHandler<StatusRuntimeException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: StatusRuntimeException): HttpResponse<Any> {
        val status = StatusConversion.valueOf(exception.status.code.name)
        val message = exception.status.description ?: status.defaultMessage

        val activeSpan = tracer.activeSpan()
        activeSpan.log("Ocorreu um erro status ${status.httpStatus.name} com a mensagem $message")
        activeSpan.finish()

        return HttpResponse.status<JsonError>(status.httpStatus)
            .body(JsonError(message))
    }
}