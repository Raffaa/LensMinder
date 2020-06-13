package it.raffinato.dev.lensminder.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

import it.raffinato.dev.lensminder.utils.enums.Duration;

import static it.raffinato.dev.lensminder.LensMinderApplication.DBDateTimeFormat;

public class Lens implements Serializable {

    private final DateTime initialDate;
    private final Duration duration;
    private final DateTime expDate;
    private final boolean isActive;

    public Lens(DateTime initialDate, Duration duration) {
        this.initialDate = initialDate;
        this.duration = duration;
        this.expDate = this.initialDate.plusDays(this.duration.getTime());
        this.isActive = true;
    }

    public Lens(DateTime initialDate, Duration duration, boolean isActive) {
        this.initialDate = initialDate;
        this.duration = duration;
        this.expDate = this.initialDate.plusDays(this.duration.getTime());
        this.isActive = isActive;
    }

    public Lens(String initialDate, int duration, boolean isActive) {
        this.initialDate = DateTime.parse(initialDate, DateTimeFormat.forPattern(DBDateTimeFormat));
        this.duration = Duration.fromInt(duration);
        this.expDate = this.initialDate.plusDays(this.duration.getTime());
        this.isActive = isActive;
    }

    DateTime getInitialDate() {
        return initialDate;
    }

    Duration getDuration() {
        return duration;
    }

    DateTime getExpDate() {
        return expDate;
    }

    boolean isActive() {
        return isActive;
    }

    int getRemainingTime() {
        int remainingDays = Days.daysBetween(DateTime.now(), this.expDate.toDateTime().plusDays(1)).getDays();
        return Math.max(remainingDays, 0);
    }
}
