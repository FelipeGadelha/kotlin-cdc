package br.com.zup.api.v1.dto.request

import br.com.zup.domain.entity.Autor
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRq(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field:Size(max = 400) val descricao: String
    ) {
    fun paraAutor(): Autor {
        return Autor(nome = nome, email = email, descricao = descricao)
    }
}
