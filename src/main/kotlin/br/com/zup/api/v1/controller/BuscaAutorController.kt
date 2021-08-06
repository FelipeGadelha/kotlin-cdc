package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.response.AutorRs
import br.com.zup.domain.repository.AutorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional


@Controller("/autores")
class BuscaAutorController(val autorRepository: AutorRepository) {

    @Get
    @Transactional
    fun buscaTodos(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {
        return with(email) {
            if (this.isBlank()) return HttpResponse.ok(autorRepository.findAll().map { a -> AutorRs(a) })
            autorRepository.findByEmail(email).let { optional ->
                if (optional.isEmpty) return HttpResponse.notFound()
                return HttpResponse.ok(AutorRs(optional.get()))
            }
        }
    }
}