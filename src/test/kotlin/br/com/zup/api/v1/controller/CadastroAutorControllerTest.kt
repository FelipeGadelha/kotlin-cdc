package br.com.zup.api.v1.controller

import br.com.zup.api.v1.dto.request.NovoAutorRq
import br.com.zup.api.v1.dto.response.EnderecoRs
import br.com.zup.domain.entity.Autor
import br.com.zup.domain.entity.Endereco
import br.com.zup.domain.repository.AutorRepository
import br.com.zup.infra.client.EnderecoClient
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest(
//    rollback = false,
//    transactionMode = TransactionMode.SINGLE_TRANSACTION,
//    transactional = false
)
internal class CadastroAutorControllerTest {

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve cadastrar um novo autor`() {

        val autorRq = NovoAutorRq(
            nome = "felipe",
            email = "felipe@email.com",
            descricao = "desenvolvedor Java/Kotlin",
            cep = "01310921",
            numero = "1000"
        )

        val enderecoRs = EnderecoRs(logradouro = "Avenida Paulista 1776", localidade = "São Paulo", uf = "SP")

        Mockito.`when`(enderecoClient.consulta(autorRq.cep)).thenReturn(HttpResponse.ok(enderecoRs))
        val request = HttpRequest.POST("/autores", autorRq)
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}