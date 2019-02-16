package com.example.producer


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ReservationPojoTest {

	@Test
	fun create() {
		val r = Reservation(reservationName = "Reena")
		Assertions.assertNull(r.id)
		Assertions.assertEquals(r.reservationName,"Reena")
	}
}