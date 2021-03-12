package br.com.zup.shared.grpc

import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.KeymanagerRegistraChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.health.v1.HealthGrpc
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun registrar() = KeymanagerRegistraChavePixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun remover() = KeymanagerRemoveChavePixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultar() = KeymanagerConsultaChavePixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listar() = KeymanagerListaChavesPixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun health() = HealthGrpc.newBlockingStub(channel)

}