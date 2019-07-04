package com.vastrak.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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

}
