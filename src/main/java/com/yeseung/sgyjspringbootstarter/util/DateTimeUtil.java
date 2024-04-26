package com.separtner.sctekportal.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class DateTimeUtil {

    public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private DateTimeUtil () {
        throw new AssertionError();
    }

    public static LocalDate formatDateTime ( String date ) {
        return LocalDate.parse( splitDate( date ), DateTimeFormatter.ofPattern( PATTERN ) );
    }

    public static LocalDateTime formatLocalDateTime ( String date ) {
        if ( !StringUtils.hasText( date ) ) {
            return null;
        }
        return LocalDateTime.parse( splitDate( date ), DateTimeFormatter.ofPattern( PATTERN ) );
    }

    public static LocalDateTime startLocalDateTime ( String date ) {
        return formatLocalDateTime( date ).with( LocalTime.MIN );
    }

    public static LocalDateTime endLocalDateTime ( String date ) {
        return formatLocalDateTime( date ).with( LocalTime.MAX );
    }

    public static LocalDateTime formatLocalDateTimePlusDay ( String date, long days ) {
        if ( !StringUtils.hasText( date ) ) {
            return null;
        }
        return LocalDateTime.parse( splitDate( date ), DateTimeFormatter.ofPattern( PATTERN ) ).plusDays( days );
    }

    public static LocalDateTime from ( String date ) {
        return LocalDateTime.parse( date, DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) );
    }

    public static LocalDateTime fromString ( String date ) {
        if ( !StringUtils.hasText( date ) ) {
            return null;
        }
        return LocalDateTime.parse( date, DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSSSSSS" ) );
    }

    public static String splitDate ( String creationDate ) {
        if ( creationDate.contains( "." ) ) {
            creationDate = creationDate.split( "\\." )[0];
        } else if ( creationDate.lastIndexOf( "Z" ) != -1 ) {
            creationDate = creationDate.substring( 0, creationDate.length() - 1 );
        }
        return creationDate;
    }

    public static String formatLocalDateTimeToString ( LocalDateTime localDateTime ) {
        return formatLocalDateTimeToString( localDateTime, "yyyyMMdd" );
    }

    public static String formatLocalDateTimeToString ( LocalDateTime localDateTime, String pattern ) {
        return localDateTime.format( DateTimeFormatter.ofPattern( pattern ) );
    }

    public static String getTodayYmd () {
        return LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) );
    }

    public static String getTodayYm () {
        return LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyyMM" ) );
    }

    public static LocalDateTime convertStringToLocalDateTime ( String datetime, String pattern ) {
        return LocalDateTime.parse( datetime, DateTimeFormatter.ofPattern( pattern ) );
    }

    public static String convertFullDateToYmd ( String creationDate ) {
        String str = splitDate( creationDate );
        int idx = str.indexOf( "T" );
        return str.substring( 0, idx ).replace( "-", "" );
    }

    public static String convertDateToYmdWithDash ( LocalDateTime dateTime ) {
        return dateTime.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) );
    }

    public static LocalDate convertDateTimeToDate ( LocalDateTime dateTime ) {
        return LocalDate.parse( splitDate( convertDateToYmdWithDash( dateTime ) ) );
    }

    public static LocalDate convertStringToLocalDate ( String orderDate ) {
        if ( !StringUtils.hasText( orderDate ) ) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( PATTERN );
        return LocalDate.parse( splitDate( orderDate ), formatter );
    }

    public static long dateDiff ( LocalDate startDate, LocalDate endDate ) {
        return ChronoUnit.DAYS.between( startDate, endDate );
    }

    public static String getSearchDate ( int days ) {
        if ( days == 0 ) {
            return LocalDateTime.now().format( DateTimeFormatter.ofPattern( PATTERN ) );
        }
        return LocalDateTime.now().plusDays( days ).format( DateTimeFormatter.ofPattern( PATTERN ) );
    }

    public static String getSearchDate () {
        return getSearchDate( 0 );
    }

    public static String getAfterMonth ( String closeYm ) {
        return getAfterMonth( closeYm, "yyyyMM" );
    }

    public static String getAfterMonth ( String closeYm, String format ) {
        int year = Integer.parseInt( closeYm.substring( 0, 4 ) );
        int day = Integer.parseInt( closeYm.substring( 4, 6 ) );
        LocalDate date = LocalDate.of( year, day, 1 );
        LocalDate localDate = date.plusMonths( 1 );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( format );
        return localDate.format( formatter );
    }

    public static String getBeforeMonth () {
        return getBeforeMonth( getTodayYmd(), 1, "yyyyMM" );
    }

    public static String getBeforeMonth ( String ymd, int duration, String format ) {
        if ( !StringUtils.hasText( ymd ) ) {
            ymd = getTodayYmd();
        }
        if ( duration == 0 ) {
            duration = 1;
        }
        int year = Integer.parseInt( ymd.substring( 0, 4 ) );
        int day = Integer.parseInt( ymd.substring( 4, 6 ) );
        LocalDate date = LocalDate.of( year, day, 1 );
        LocalDate localDate = date.minusMonths( duration );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( format );
        return localDate.format( formatter );
    }

    public static LocalDateTime getStartDateByTargetDate ( String targetDate ) {
        return getStartDateByTargetDate( targetDate, "yyyy-MM-dd" );
    }

    public static LocalDateTime getStartDateByTargetDate ( String targetDate, String pattern ) {
        LocalDate dateTime = LocalDate.parse( targetDate, DateTimeFormatter.ofPattern( pattern ) );
        return dateTime.atStartOfDay();
    }

    public static LocalDateTime getEndDateByTargetDate ( String targetDate ) {
        return getEndDateByTargetDate( targetDate, "yyyy-MM-dd" );
    }

    public static LocalDateTime getEndDateByTargetDate ( String targetDate, String pattern ) {
        LocalDate dateTime = LocalDate.parse( targetDate, DateTimeFormatter.ofPattern( pattern ) );
        return LocalDateTime.of( dateTime, LocalTime.of( 23, 59, 59 ) );
    }

}
