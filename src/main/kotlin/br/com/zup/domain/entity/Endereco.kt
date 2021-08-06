package br.com.zup.domain.entity

import br.com.zup.api.v1.dto.response.EnderecoRs
import javax.persistence.Embeddable

@Embeddable
class Endereco(
    val rua: String,
    val cidade: String,
    val estado: String,
    val numero: String
) {
    constructor(enderecoRs: EnderecoRs, numero: String) :
            this(
                rua = enderecoRs.logradouro,
                cidade = enderecoRs.localidade,
                estado = enderecoRs.uf,
                numero = numero
            )

}
