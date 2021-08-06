package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.request.NovoAutorRq
import br.com.zup.api.v1.dto.response.EnderecoRs
import br.com.zup.domain.repository.AutorRepository
import br.com.zup.infra.client.EnderecoClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastroAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient,
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRq): HttpResponse<Any> {
        val httpResponse = enderecoClient.consulta(request.cep)
//        if (httpResponse.code() == 400) return HttpResponse.badRequest()
        return request.paraAutor(httpResponse.body()).let { autor ->
            autorRepository.save(autor)
            val uri = UriBuilder
                .of("/autores/{id}")
                .expand(mutableMapOf(Pair("id", autor.id)))
            HttpResponse.created(uri)
        }
    }
}