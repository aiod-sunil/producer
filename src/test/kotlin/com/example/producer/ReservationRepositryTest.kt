package com.example.producer

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@DataMongoTest
@ExtendWith(SpringExtension::class)
class ReservationRepositryTest{

	@Autowired
	lateinit var reservationRepository: ReservationRepository

	@Test
	fun findAllByReservationName(){
     val flux=reservationRepository.deleteAll()
		     .thenMany(Flux.just("A","B","C","C"))
		     .flatMap { name->reservationRepository.save(Reservation(reservationName = name)) }
		     .thenMany(reservationRepository.findByReservationName("C"))

		StepVerifier.create(flux).expectNextCount(2).verifyComplete()

	}
}