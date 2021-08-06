package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.request.NovoAutorRq
import br.com.zup.domain.repository.AutorRepository
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
class CadastroAutorController(val autorRepository: AutorRepository) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRq): HttpResponse<Any> {
        return request.paraAutor().let { autor ->
            autorRepository.save(autor)
            val uri = UriBuilder
                .of("/autores/{id}")
                .expand(mutableMapOf(Pair("id", autor.id)))
            HttpResponse.created(uri)
        }
    }
}