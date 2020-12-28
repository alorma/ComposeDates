package com.alorma.dates.domain

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class TimeDistanceCalculatorTest {

    private val fixedClock = Clock.fixed(
        Instant.parse("2020-12-10T10:00:00Z"),
        ZoneOffset.UTC
    )
    private val time = LocalDateTime.now(fixedClock)
    private val calculator = TimeDistanceCalculator(fixedClock)

    @Test
    fun `test 0`() {
        expectThat(time.toString()).isEqualTo("2020-12-10T10:00")
    }

    @Test
    fun `test calculate equal`() {
        val newTime = time

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Now>()
    }

    @Test
    fun `test calculate before`() {
        val newTime = time
            .minusYears(1)
            .minusMonths(2)
            .minusDays(3)
            .minusHours(5)
            .minusMinutes(6)
            .minusSeconds(10)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.Before>()
    }

    @Test
    fun `test calculate after`() {
        val newTime = time
            .plusYears(1)
            .plusMonths(2)
            .plusDays(3)
            .plusHours(5)
            .plusMinutes(6)
            .plusSeconds(10)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.After>()
    }

    @Test
    fun `test calculate time before distance`() {
        val newTime = time
            .minusHours(5)
            .minusMinutes(6)
            .minusSeconds(10)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.Before>().and {
            get { hours }.isEqualTo(5)
            get { minutes }.isEqualTo(6)
            get { seconds }.isEqualTo(10)
        }
    }

    @Test
    fun `test calculate time after distance`() {
        val newTime = time
            .plusHours(5)
            .plusMinutes(6)
            .plusSeconds(10)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.After>().and {
            get { hours }.isEqualTo(-5)
            get { minutes }.isEqualTo(-6)
            get { seconds }.isEqualTo(-10)
        }
    }

    @Test
    fun `test calculate date before distance`() {
        val newTime = time
            .minusYears(1)
            .minusMonths(2)
            .minusDays(3)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.Before>().and {
            get { years }.isEqualTo(1)
            get { months }.isEqualTo(2)
            get { days }.isEqualTo(3)
        }
    }

    @Test
    fun `test calculate date after distance`() {
        val newTime = time
            .plusYears(1)
            .plusMonths(2)
            .plusDays(3)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.After>().and {
            get { years }.isEqualTo(-1)
            get { months }.isEqualTo(-2)
            get { days }.isEqualTo(-3)
        }
    }

    @Test
    fun `test calculate more than over temporal unit before distance`() {
        val newTime = time.minusMinutes(135)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.Before>().and {
            get { hours }.isEqualTo(2)
            get { minutes }.isEqualTo(15)
        }
    }

    @Test
    fun `test calculate more than over temporal unit after distance`() {
        val newTime = time.plusMinutes(135)

        val result = calculator.calculateTimeDistance(newTime)

        expectThat(result).isA<TimeDistanceCalculator.TimeCalculation.Elapsed.After>().and {
            get { hours }.isEqualTo(-2)
            get { minutes }.isEqualTo(-15)
        }
    }


}