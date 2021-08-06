package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.response.AutorRs
import br.com.zup.domain.repository.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import javax.transaction.Transactional

@Controller("/autores/{id}")
class DeletarAutorController(val autorRepository: AutorRepository) {

    @Delete
    @Transactional
    fun deleta(@PathVariable id: Long): HttpResponse<Any> {
        return autorRepository.findById(id).let { optional ->
            if (optional.isEmpty) return HttpResponse.notFound()
            autorRepository.deleteById(id)
            HttpResponse.noContent()
        }
    }
}