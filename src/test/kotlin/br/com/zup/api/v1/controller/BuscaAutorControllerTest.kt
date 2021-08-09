package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.response.AutorRs
import br.com.zup.domain.entity.Autor
import br.com.zup.domain.entity.Endereco
import br.com.zup.domain.repository.AutorRepository
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaAutorControllerTest{

    @field:Inject
    lateinit var autorRepository: AutorRepository
    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private lateinit var autor: Autor

    @BeforeEach
    internal fun setup() {
        val endereco = Endereco(
            rua = "rua 1",
            cidade = "SAO PAULO",
            estado = "SP",
            numero = "122"
        )
        autor = Autor(
            nome = "felipe",
            email = "felipe@email.com",
            descricao = "desenvolvedor Java/Kotlin",
            endereco = endereco
        )
        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {

        var response = client.toBlocking().exchange("/autores?email=${autor.email}", AutorRs::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())

        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)
    }
}