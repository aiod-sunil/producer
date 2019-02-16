package com.example.producer

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ReservationRepository :ReactiveMongoRepository<Reservation,String>{
fun findByReservationName(rn:String): Flux<Reservation>
}
