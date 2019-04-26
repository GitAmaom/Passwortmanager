package de.passwordmanager.helpers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import de.passwordmanager.entities.TimeName;
/**
 * Class for converting seconds an other timeunit.
 * @author Moritz Schneider
 *
 */
public class TimeCalculator {
    
    /**
     * The number of seconds.
     */
    private BigDecimal seconds;
    /**
     * The number of mintues.
     */
    private BigDecimal minutes;
    /**
     * The number of hours.
     */
    private BigDecimal hours;
    /**
     * The number of days.
     */
    private BigDecimal days;
    /**
     * The number of weeks.
     */
    private BigDecimal weeks;
    /**
     * The number of months.
     */
    private BigDecimal months;
    /**
     * The number of years.
     */
    private BigDecimal years;
    /**
     * The number of centuries.
     */
    private BigDecimal centuries;
    /**
     * The number of millenials.
     */
    private BigDecimal millenials;
    /**
     * The number of megannums.
     */
    private BigDecimal megannums;
    /**
     * The number of aeons.
     */
    private BigDecimal aeons;
    
    /**
     * One minutes equals 60 seconds.
     */
    private static final int MINUTE_FACTOR = 60;
    /**
     * One hour equals 60 minutes.
     */
    private static final int HOUR_FACTOR = 60;
    /**
     * One day equals 24 hours.
     */
    private static final int DAY_FACTOR = 24;
    /**
     * One week equals 7 days.
     */
    private static final int WEEK_FACTOR = 7;
    /**
     * One month equals 4.3 weeks.
     */
    private static final double MONTH_FACTOR = 4.3;
    /**
     * One year equals 12 months.
     */
    private static final int YEAR_FACTOR = 12;
    /**
     * One century equals 100 years.
     */
    private static final int CENTURY_FACTOR = 100;
    /**
     * One millenial equals 10 centuries.
     */
    private static final int MILLENIAL_FACTOR = 10;
    /**
     * One megannum equals 1000 millenials.
     */
    private static final int MEGANNUM_FACTOR = 1000;
    /**
     * One aeon equals 100 megannums.
     */
    private static final int AEON_FACTOR = 100;
    
    /**
     * Initializes the class by converting the seconds into the timeunits.
     * @param seconds the number of seconds.
     */
    public TimeCalculator(BigDecimal seconds) {
        this.seconds = seconds;
        this.minutes = new BigDecimal("0");
        this.hours = new BigDecimal("0");
        this.days = new BigDecimal("0");
        this.weeks = new BigDecimal("0");
        this.months = new BigDecimal("0");
        this.years = new BigDecimal("0");
        this.centuries = new BigDecimal("0");
        this.millenials = new BigDecimal("0");
        this.megannums = new BigDecimal("0");
        this.aeons = new BigDecimal("0");
        initTime();
    }
    
    /**
     * Initializes the time.
     */
    public void initTime() {
        BigDecimal divide = new BigDecimal(Integer.toString(MINUTE_FACTOR));
        this.minutes = this.seconds.divideToIntegralValue(divide);
        this.seconds = this.seconds.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(HOUR_FACTOR));
        this.hours = this.minutes.divideToIntegralValue(divide);
        this.minutes = this.minutes.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(DAY_FACTOR));
        this.days = this.hours.divideToIntegralValue(divide);
        this.hours = this.hours.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(WEEK_FACTOR));
        this.weeks = this.days.divideToIntegralValue(divide);
        this.days = this.days.remainder(divide);
        
        divide = new BigDecimal(Double.toString(MONTH_FACTOR));
        this.months = this.weeks.divideToIntegralValue(divide);
        this.weeks = this.weeks.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(YEAR_FACTOR));
        this.years = this.months.divideToIntegralValue(divide);
        this.months = this.months.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(CENTURY_FACTOR));
        this.centuries = this.years.divideToIntegralValue(divide);
        this.years = this.years.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(MILLENIAL_FACTOR));
        this.millenials = this.centuries.divideToIntegralValue(divide);
        this.centuries = this.centuries.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(MEGANNUM_FACTOR));
        this.megannums = this.millenials.divideToIntegralValue(divide);
        this.millenials = this.millenials.remainder(divide);
        
        divide = new BigDecimal(Integer.toString(AEON_FACTOR));
        this.aeons = this.megannums.divideToIntegralValue(divide);
        this.megannums = this.megannums.remainder(divide);
        
    }
    
    /**
     * Converts seconds to another time unit.
     * @param timename the unit of time.
     * @param seconds the number of seconds.
     * @return the count of the specified time unit.
     */
    public static BigDecimal convertSecondsTo(TimeName timename, BigDecimal seconds) {
        switch(timename) {
        default: return seconds;
        case MINUTES: return secondsToMinutes(seconds).setScale(0, RoundingMode.HALF_UP);
        case HOURS: return secondsToHours(seconds).setScale(0, RoundingMode.HALF_UP);
        case DAYS: return secondsToDays(seconds).setScale(0, RoundingMode.HALF_UP);
        case WEEKS: return secondsToWeeks(seconds).setScale(0, RoundingMode.HALF_UP);
        case MONTHS: return secondsToMonths(seconds).setScale(0, RoundingMode.HALF_UP);
        case YEARS: return secondsToYears(seconds).setScale(0, RoundingMode.HALF_UP);
        case CENTURIES: return secondsToCenturies(seconds).setScale(0, RoundingMode.HALF_UP);
        case MILLENIALS: return secondsToMillenials(seconds).setScale(0, RoundingMode.HALF_UP);
        case MEGANNUMS: return secondsToMegannum(seconds).setScale(0, RoundingMode.HALF_UP);
        case AEONS: return secondsToAeon(seconds).setScale(0, RoundingMode.HALF_UP);
        }
    }

    /**
     * Converts seconds to minutes.
     * @param seconds the number of seconds.
     * @return minutes.
     */
    public static BigDecimal secondsToMinutes(BigDecimal seconds) {
        return seconds.divide(new BigDecimal(MINUTE_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to hours.
     * @param seconds the number of seconds.
     * @return hours.
     */
    public static BigDecimal secondsToHours(BigDecimal seconds) {
       return secondsToMinutes(seconds).divide(new BigDecimal(HOUR_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to days.
     * @param seconds the number of seconds.
     * @return days.
     */
    public static BigDecimal secondsToDays(BigDecimal seconds) {
        return secondsToHours(seconds).divide(new BigDecimal(DAY_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to weeks.
     * @param seconds the number of seconds.
     * @return weeks.
     */
    public static BigDecimal secondsToWeeks(BigDecimal seconds) {
        return secondsToDays(seconds).divide(new BigDecimal(WEEK_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to months.
     * @param seconds the number of seconds.
     * @return months.
     */
    public static BigDecimal secondsToMonths(BigDecimal seconds) {
        return secondsToWeeks(seconds).divide(new BigDecimal(MONTH_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to years.
     * @param seconds the number of seconds.
     * @return years.
     */
    public static BigDecimal secondsToYears(BigDecimal seconds) {
        return secondsToMonths(seconds).divide(new BigDecimal(YEAR_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to centuries.
     * @param seconds the number of seconds.
     * @return centuries.
     */
    public static BigDecimal secondsToCenturies(BigDecimal seconds) {
        return secondsToYears(seconds).divide(new BigDecimal(CENTURY_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to millenials.
     * @param seconds the number of seconds.
     * @return millenials.
     */
    public static BigDecimal secondsToMillenials(BigDecimal seconds) {
        return secondsToCenturies(seconds).divide(new BigDecimal(MILLENIAL_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to megannums.
     * @param seconds the number of seconds.
     * @return megannums.
     */
    public static BigDecimal secondsToMegannum(BigDecimal seconds) {
        return secondsToMillenials(seconds).divide(new BigDecimal(MEGANNUM_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Converts seconds to aeon.
     * @param seconds the number of seconds.
     * @return aeon.
     */
    public static BigDecimal secondsToAeon(BigDecimal seconds) {
        return secondsToMegannum(seconds).divide(new BigDecimal(AEON_FACTOR), MathContext.DECIMAL128);
    }
    
    /**
     * Returns a string representation of the class.
     */
    @Override
    public String toString() {
        String str = "Sekunden: " + this.seconds + "\n";
        str += "Minuten: " + this.minutes + "\n";
        str += "Stunden: " + this.hours + "\n";
        str += "Tage: " + this.days + "\n";
        str += "Wochen: " + this.weeks + "\n";
        str += "Monate: " + this.months + "\n";
        str += "Jahre: " + this.years + "\n";
        str += "Jahrhunderte: " + this.centuries + "\n";
        str += "Jahrtausende: " + this.millenials + "\n";
        str += "Megannume: " + this.megannums + "\n";
        str += "Äonen: " + this.aeons + "\n";
        return str;
    }

    /**
     * Gets the seconds.
     * @return the seconds.
     */
    public BigDecimal getSeconds() {
        return seconds;
    }

    /**
     * Gets the minutes.
     * @return the minutes.
     */
    public BigDecimal getMinutes() {
        return minutes;
    }

    /**
     * Gets the hours.
     * @return the hours.
     */
    public BigDecimal getHours() {
        return hours;
    }

    /**
     * Gets the days.
     * @return the days.
     */
    public BigDecimal getDays() {
        return days;
    }

    /**
     * Gets the weeks.
     * @return the weeks.
     */
    public BigDecimal getWeeks() {
        return weeks;
    }

    /**
     * Gets the months.
     * @return the months.
     */
    public BigDecimal getMonths() {
        return months;
    }

    /**
     * Gets the years.
     * @return the years.
     */
    public BigDecimal getYears() {
        return years;
    }

    /**
     * Gets the centuries.
     * @return the centuries.
     */
    public BigDecimal getCenturies() {
        return centuries;
    }

    /**
     * Gets the millenials.
     * @return the millenials.
     */
    public BigDecimal getMillenials() {
        return millenials;
    }

    /**
     * Gets the megannums.
     * @return the megannums.
     */
    public BigDecimal getMegannums() {
        return megannums;
    }

    /**
     * Gets the aeons.
     * @return the aeons.
     */
    public BigDecimal getAeons() {
        return aeons;
    }
    
    
}
