package com.example.producer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@WebFluxTest
@Import(ReservationRestConfiguration::class)
@ExtendWith(SpringExtension::class)
class ReservationRestTest {

	@MockBean
	lateinit var repository: ReservationRepository

	@Autowired
	lateinit var client: WebTestClient

	@Test
	fun getAllReservations() {

		Mockito.`when`(repository.findAll())
				.thenReturn(Flux.just(Reservation("1", "A"),
						Reservation("2", "B")))

		client.get()
				.uri("http://localhost:8080/reservations")
				.exchange()
				.expectStatus().isOk
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("@.[0].id").isEqualTo("1")
				.jsonPath("@.[0].reservationName").isEqualTo("A")
				.jsonPath("@.[1].id").isEqualTo("2")
				.jsonPath("@.[1].reservationName").isEqualTo("B")
	}


}