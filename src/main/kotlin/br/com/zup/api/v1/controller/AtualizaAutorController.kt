package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.response.AutorRs
import br.com.zup.domain.repository.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {
        return autorRepository.findById(id). let { optional ->
            if (optional.isEmpty) return HttpResponse.notFound()
            val autor = optional.get()
            autor.descricao = descricao
//            autorRepository.update(autor)
            HttpResponse.ok(AutorRs(autor))
        }
    }
}