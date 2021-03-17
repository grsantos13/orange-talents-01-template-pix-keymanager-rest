package br.com.zup.healthcheck

import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthGrpc
import io.micronaut.context.annotation.Value
import io.micronaut.health.HealthStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.RxHttpClient
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.management.health.indicator.HealthResult
import io.swagger.v3.oas.annotations.Hidden
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class GrpcServerHealthCheck(
    private val client: RxHttpClient,
) : HealthIndicator {

    @Value("\${healthcheck.keymanager.url}")
    private lateinit var uri: String

    override fun getResult(): Publisher<HealthResult> {
        return client.exchange(uri)
            .map { HealthResult.builder("Keymanager-grpc", HealthStatus.UP).build() }
            .onErrorReturn { HealthResult.builder("Keymanager-grpc", HealthStatus.DOWN).build() }
    }
}

@Hidden
@Controller("/check-grpc-status")
class GrpcHealthCheckController(
    private val healthCheckClient: HealthGrpc.HealthBlockingStub
) {
    @Get
    fun check() {
        healthCheckClient.check(HealthCheckRequest.newBuilder().setService("Keymanager").build())
    }
}