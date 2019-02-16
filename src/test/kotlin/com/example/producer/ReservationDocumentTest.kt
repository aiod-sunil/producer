package com.example.producer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.commons.util.StringUtils.isNotBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Predicate


@ExtendWith(SpringExtension::class)
@DataMongoTest
class ReservationDocumentTest {

	@Autowired
	private lateinit var reactiveMongoTemplate: ReactiveMongoTemplate


	@Test
	fun persist() {

		val saved = Flux.just(Reservation(reservationName = "Sunil"),
				Reservation(reservationName = "Reena"))
				.flatMap { r -> reactiveMongoTemplate.save(r) }


		val interaction = reactiveMongoTemplate
				.dropCollection(Reservation::class)
				.thenMany(saved)
				.thenMany(reactiveMongoTemplate.findAll(Reservation::class.java))


		val p = Predicate<Reservation> { (id, reservationName) ->
			isNotBlank(id) && (
					reservationName.equals("Sunil")
							|| reservationName.equals("Reena"))
		}

		StepVerifier
				.create(interaction)
				.expectNextMatches(p)
				.expectNextMatches(p)
				.verifyComplete()
	}


}