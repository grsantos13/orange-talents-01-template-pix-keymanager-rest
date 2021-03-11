package br.com.zup.shared.grpc

import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.KeymanagerRegistraChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keymanager") val channel: ManagedChannel) {

    @Singleton
    fun registrar(): KeymanagerRegistraChavePixGrpcServiceGrpc.KeymanagerRegistraChavePixGrpcServiceBlockingStub? {
        return KeymanagerRegistraChavePixGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun remover(): KeymanagerRemoveChavePixGrpcServiceGrpc.KeymanagerRemoveChavePixGrpcServiceBlockingStub? {
        return KeymanagerRemoveChavePixGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun consultar(): KeymanagerConsultaChavePixGrpcServiceGrpc.KeymanagerConsultaChavePixGrpcServiceBlockingStub? {
        return KeymanagerConsultaChavePixGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Singleton
    fun listar(): KeymanagerListaChavesPixGrpcServiceGrpc.KeymanagerListaChavesPixGrpcServiceBlockingStub? {
        return KeymanagerListaChavesPixGrpcServiceGrpc.newBlockingStub(channel)
    }

}