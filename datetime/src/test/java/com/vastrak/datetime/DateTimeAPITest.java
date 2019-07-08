package com.vastrak.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DateTimeAPITest {

	private static final Log logger = LogFactory.getLog(DateTimeAPITest.class);

	@Test
	public void test001_LocalDate() {

		LocalDate localDate = LocalDate.now(); // 2019-07-04
		LocalDate localDateGMT = LocalDate.now(ZoneId.of("GMT+02:00")); // 2019-07-04
		LocalDate localDateT = LocalDateTime.now().toLocalDate(); // 2019-07-04

		LocalDate todayPlusThreeMonth = localDate.plusMonths(3); // plusYears, plusWeeks, plusDays
		LocalDate todayMinusTwoWeeks = localDate.minusWeeks(2); // minus

		Calendar calendarPM = Calendar.getInstance();
		calendarPM.setTime(new Date());
		calendarPM.add(Calendar.MONTH, 3); // localDate.plusMonths(3);
		Calendar calendarMW = Calendar.getInstance();
		calendarMW.setTime(new Date());
		calendarMW.add(Calendar.DAY_OF_MONTH, -14); // localeDate.minusWeeks(2);

		// LocalDate: 2019-10-04. Calendar: Fri Oct 04 09:46:54 CEST 2019
		logger.info("LocalDate: " + todayPlusThreeMonth + ". Calendar: " + calendarPM.getTime());

		assertThat(localDate).isEqualTo(localDateGMT).isEqualTo(localDateT);
		assertThat(todayPlusThreeMonth.getYear()).isEqualTo(calendarPM.get(Calendar.YEAR));
		assertThat(todayPlusThreeMonth.getMonth().ordinal()).isEqualTo(calendarPM.get(Calendar.MONTH));
		assertThat(todayPlusThreeMonth.getDayOfMonth()).isEqualTo(calendarPM.get(Calendar.DAY_OF_MONTH));
		assertThat(todayMinusTwoWeeks.getYear()).isEqualTo(calendarMW.get(Calendar.YEAR));
		assertThat(todayMinusTwoWeeks.getMonth().ordinal()).isEqualTo(calendarMW.get(Calendar.MONTH));
		assertThat(todayMinusTwoWeeks.getDayOfMonth()).isEqualTo(calendarMW.get(Calendar.DAY_OF_MONTH));

	}

	@Test
	public void test002_LocalDateTimeStamp() {

		LocalDate localDate = LocalDate.parse("1986-10-12");
		// Timestam: wrapper around java.util.Date that allows the JDBC API to identify
		// this as an SQL TIMESTAMP value.
		LocalDateTime dateTime = localDate.atTime(14, 25, 10);
		Timestamp timestamp = Timestamp.valueOf(dateTime);

		assertThat(Timestamp.valueOf("1986-10-12 14:25:10")).isEqualTo(timestamp);

	}

	@Test
	public void test003_formatterZonedDateTime() {

		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SS"); // 04/07/2019 11:53:53.87
		String text = zonedDateTime.format(formatter);
		LocalDateTime localDateTime = LocalDateTime.parse(text, formatter);

		logger.info("formatter : " + text); // 04/07/2019 11:53:53.87

		assertThat(localDateTime.toLocalDate()).isEqualTo(zonedDateTime.toLocalDate());
		assertThat(localDateTime.toLocalTime()).isNotEqualTo(zonedDateTime.toLocalTime()); // <- isNotEqualTo!
		assertThat(localDateTime.getHour()).isEqualTo(zonedDateTime.getHour());
		assertThat(localDateTime.getMinute()).isEqualTo(zonedDateTime.getMinute());
		assertThat(localDateTime.getSecond()).isEqualTo(zonedDateTime.getSecond());

	}

	@Test
	public void test004_LocalDateTime() {

		// A date without an explicitly specified time zone.
		LocalDate localDate = LocalDate.now(); // 2019-07-04
		LocalDate localDateOf = LocalDate.of(1986, Month.JUNE, 21); // 1986-06-21
		LocalDate localDateEpoch = LocalDate.ofEpochDay(localDate.toEpochDay()); // 2019-07-04
		LocalDate localDateParse = LocalDate.parse("1986-06-21"); // 1986-06-21

		// A date-time without a time-zone in the ISO-8601 calendar system,
		// such as 2007-12-03T10:15:30.
		LocalDateTime localDateTime = LocalDateTime.now(); // 2019-07-04T08:46:30.107 <- local time
		LocalDateTime localDateTimeOf = LocalDateTime.of(1986, Month.JUNE, 21, 17, 50); // 1986-06-21T17:50
		LocalDateTime localDateTimeParse = LocalDateTime.parse("1986-06-21T17:50:31"); // 1986-06-21T17:50:31

		// A time without a time-zone in the ISO-8601 calendar system,
		// such as 10:15:30.
		LocalTime localTime = LocalTime.now(); // 08:46:30.108
		LocalTime localTimeOf = LocalTime.of(17, 50, 31); // 17:50:31
		LocalTime localTimeParse = LocalTime.parse("17:50:31");

		// A year in the ISO-8601 calendar system, such as 2007.
		Year year = Year.now(); // 2019
		// A year-month in the ISO-8601 calendar system, such as 2007-12.
		YearMonth yearMonth = YearMonth.now(); // 2019-07

		logger.info("Date " + localDate);
		logger.info("DateOf " + localDateOf);
		logger.info("DateEpoch " + localDateEpoch);
		logger.info("DateParse " + localDateParse);

		logger.info("DateTime " + localDateTime);
		logger.info("DateTimeOf " + localDateTimeOf);
		logger.info("DateTimeParse " + localDateTimeParse);

		logger.info("Time" + localTime);
		logger.info("TimeOf " + localTimeOf);
		logger.info("TimeParse " + localTimeParse);

		logger.info("Year " + year);
		logger.info("YearMonth " + yearMonth);

	}

	@Test
	public void test004_Instant() {

		// This class is immutable and thread-safe.
		// Output format is ISO-8601
		// Parse into an Instant
		Instant instantBorn = Instant.parse("2009-03-17T16:00:00.00Z"); // 2009-03-17T16:00:00Z

		Instant instantPlus = instantBorn.plus(Duration.ofHours(5).plusMinutes(4)); // 2009-03-17T21:04:00Z
		Instant instantMinus = instantBorn.minus(Period.ofWeeks(3).minusDays(2)); // 2009-02-26T16:00:00Z

		Instant calculatedInstantPlus = Instant.parse("2009-03-17T21:04:00Z");
		Instant calculatedInstantMinus = Instant.parse("2009-02-26T16:00:00Z");

		assertThat(instantPlus).isEqualTo(calculatedInstantPlus);
		assertThat(instantMinus).isEqualTo(calculatedInstantMinus);

		logger.info("Instant: " + instantBorn + ": plus 5 hours 4 min. " + instantPlus);
		logger.info("Instant: " + instantBorn + ": minus 3 weeks and 2 days " + instantMinus);

	}

	@Test
	public void test005_DurationBetweenTwoInstants() {

		// A Duration measures an amount of time using time-based values (seconds,
		// nanoseconds).
		// A Period uses date-based values (years, months, days)

		Instant a = Instant.parse("2007-12-03T10:15:30.00Z");
		Instant b = Instant.parse("2007-12-03T10:25:15.00Z"); // a + 9'45"
		Duration gapExpected = Duration.ofSeconds(60 * 9).plus(45, ChronoUnit.SECONDS);
		Duration gap = Duration.between(a, b);

		long minutes = Duration.between(a, b).toMinutes(); // truncate seconds // 9'
		long milli = Duration.between(a, b).toMillis(); // 585000

		assertThat(gap).isEqualByComparingTo(gapExpected);

		logger.info("Gap minutes: " + minutes + ", milliseconds  " + milli);
	}

	@Test
	public void test006_ChronoUnitBetweenTwoInstants() {

		// The ChronoUnit enum, defines the units used to measure time.
		// The ChronoUnit.between method is useful when you want to measure an amount
		// of time in a single unit of time only, such as days or seconds.

		Instant a = Instant.parse("2007-12-03T10:15:30.00Z");
		Instant b = Instant.parse("2007-12-03T10:25:15.00Z"); // a + 9'45"
		Duration gapExpected = Duration.ofSeconds(60 * 9).plus(45, ChronoUnit.SECONDS);
		long gapm = ChronoUnit.MILLIS.between(a, b);
		long gaps = ChronoUnit.SECONDS.between(a, b); // gapm * 1000

		assertThat(gapm).isEqualTo(gapExpected.toMillis()).isEqualTo(gaps * 1000);

	}

	@Test
	public void test008_PeriodBetweenTwoLocalDate() {

		// To define an amount of time with date-based values (years, months, days),
		// use the Period class. The Period class provides various get methods,
		// such as getMonths, getDays, and getYears, so that you can extract the
		// amount of time from the period.

		LocalDate today = LocalDate.parse("2019-07-07");
		LocalDate yesterday = LocalDate.of(1986, Month.APRIL, 3);

		Period p = Period.between(yesterday, today); // 33 years, 3 months, 4 days
		long p2 = ChronoUnit.DAYS.between(yesterday, today); // 12148 days

		assertThat(p.getYears()).isEqualTo(33L);
		assertThat(p.getMonths()).isEqualTo(3L);
		assertThat(p.getDays()).isEqualTo(4L);
		assertThat(p2).isEqualTo(12148L);

	}

	@Test
	public void test009_convertLocalDateTimeToInstant() {

		LocalDateTime dateTime = LocalDateTime.of(2017, Month.MARCH, 07, 10, 55); // 2017/03/07 10:55;
		Instant instant = dateTime.atZone(ZoneId.of("GMT+02:00")).toInstant();

		assertThat(instant.toString()).isEqualTo("2017-03-07T08:55:00Z");
	}

	@Test
	public void test010_convertInstantToLocalDateTime() {

		// 2017/03/07 10:55;
		Instant instant = LocalDateTime.of(2017, Month.MARCH, 07, 10, 55).atZone(ZoneId.of("GMT+02:00")).toInstant();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT+02:00"));

		assertThat(instant.getNano()).isEqualTo(localDateTime.getNano());

	}
	
	/**
	 * Generates a sequence of LOCAL, adding a number of minutes to a LOCAL.
	 * LocalDateTime + minutes[0], LocalDateTime + minutes[1], ... 
	 * 
	 * @param start   LocalDateTime to start adding minutes.
	 * @param minutes Array of Integer with the minutes to add to LocalDateTime
	 * @return Set of LocalDateTime
	 */
	private Set<LocalDateTime> dateTimePlusArray(LocalDateTime start, int[] minutes) {

		if (minutes == null || start == null) {
			return null;
		}
		Set<LocalDateTime> timeSet = null;
		for (int i = 0; i < minutes.length; i++) {
			if (timeSet == null) {
				timeSet = new HashSet<>();
			}
			Instant instant = start.atZone(ZoneId.of("GMT+02:00")).toInstant();
			timeSet.add(LocalDateTime.ofInstant(instant.plus(minutes[i], ChronoUnit.MINUTES), ZoneId.of("GMT+02:00")));
		}

		return timeSet;
	}

	@Test
	public void test011_DateTimePlusArray() {

		// start DateTime
		LocalDateTime localDateTime = LocalDateTime.parse("2019-03-13T16:00:00");
		// minutes to add
		int[] minutes = { 1, 5, 10, 20, 30, 40 };
		Set<LocalDateTime> set = dateTimePlusArray(localDateTime, minutes);

		// expected!
		LocalDateTime next1 = LocalDateTime.parse("2019-03-13T16:01:00");
		LocalDateTime next2 = LocalDateTime.parse("2019-03-13T16:05:00");
		LocalDateTime next3 = LocalDateTime.parse("2019-03-13T16:10:00");
		LocalDateTime next4 = LocalDateTime.parse("2019-03-13T16:20:00");
		LocalDateTime next5 = LocalDateTime.parse("2019-03-13T16:30:00");
		LocalDateTime next6 = LocalDateTime.parse("2019-03-13T16:40:00");

		// check!
		assertThat(set).isNotNull().contains(next1, next2, next3, next4, next5, next6).hasSize(6);
		assertThat(dateTimePlusArray(localDateTime, null)).isNull();
		int[] v = {};
		assertThat(dateTimePlusArray(localDateTime, v)).isNull();

	}

	@Test
	public void test012_DateTimePlusArrayNullCases() {

		LocalDateTime localDateTimeNull = null;
		LocalDateTime localDateTimeNow = LocalDateTime.now();
		int[] minutesEmpty = {};
		int[] minutes = { 1 };

		assertThat(dateTimePlusArray(localDateTimeNull, null)).isNull();
		assertThat(dateTimePlusArray(localDateTimeNull, minutesEmpty)).isNull();
		assertThat(dateTimePlusArray(localDateTimeNull, minutes)).isNull();

		assertThat(dateTimePlusArray(localDateTimeNow, null)).isNull();
		assertThat(dateTimePlusArray(localDateTimeNow, minutesEmpty)).isNull();
	}

}
