package br.com.zup.infra.client

import br.com.zup.api.v1.dto.response.EnderecoRs
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client(value = "https://viacep.com.br")
interface EnderecoClient {

    @Get(value = "/ws/{cep}/xml/", consumes = [MediaType.APPLICATION_XML])
    fun consulta(@QueryValue cep: String): HttpResponse<EnderecoRs>

}