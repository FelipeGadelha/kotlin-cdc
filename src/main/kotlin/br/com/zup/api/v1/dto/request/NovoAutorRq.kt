package br.com.zup.api.v1.dto.request

import br.com.zup.api.v1.dto.response.EnderecoRs
import br.com.zup.api.validation.Unique
import br.com.zup.domain.entity.Autor
import br.com.zup.domain.entity.Endereco
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class NovoAutorRq(
    @field:NotBlank val nome: String,

    @field:NotBlank @field:Email @field:Unique(domainClass = Autor::class, fieldName = "email") val email: String,

    @field:NotBlank @field:Size(max = 400) val descricao: String,
    @field:NotBlank val cep: String,
    @field:NotBlank val numero: String
    ) {
    fun paraAutor(enderecoRs: EnderecoRs): Autor {
        val endereco = Endereco(enderecoRs, numero)
        return Autor(
            nome = nome,
            email = email,
            descricao = descricao,
            endereco = endereco
        )
    }
}
