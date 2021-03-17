package br.com.zup.healthcheck

import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthGrpc
import io.micronaut.context.annotation.Value
import io.micronaut.health.HealthStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.management.health.indicator.HealthResult
import org.reactivestreams.Publisher
import javax.inject.Singleton

@Singleton
class GrpcServerHealthCheck(
    private val client: RxHttpClient,
    private val healthCheckClient: HealthGrpc.HealthBlockingStub
) : HealthIndicator {

    @Value("\${healthcheck.keymanager.url}")
    private lateinit var uri: String

    /*
    * Código mantido somente por fins de conhecimento, uma vez que esse healthcheck faz
    * um check em um sistema externo (keymanager-grpc) e ele está fora do nosso controle,
    * e também por questões de segurança.
    */
    override fun getResult(): Publisher<HealthResult> {
        return Publisher<HealthResult> {
            try {
                healthCheckClient.check(HealthCheckRequest.newBuilder().setService("Keymanager").build())
                it.onNext(HealthResult.builder("Keymanager-grpc", HealthStatus.UP).build())
                it.onComplete()
            } catch (e: Exception) {
                it.onNext(HealthResult.builder("Keymanager-grpc", HealthStatus.DOWN).build())
                it.onComplete()
            }
        }
    }
}