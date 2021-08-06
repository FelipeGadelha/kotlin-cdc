package br.com.zup.api.v1.dto.response

import br.com.zup.domain.entity.Autor
import java.time.LocalDateTime

data class AutorRs(
    val id: Long?,
    val nome: String,
    val email: String,
    val descricao: String
) {
    constructor(autor: Autor) : this(
        id = autor.id,
        nome = autor.nome,
        email = autor.email,
        descricao = autor.descricao
    )
}
