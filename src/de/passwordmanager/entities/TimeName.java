package de.passwordmanager.entities;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * Enum for storing the names and number of seconds of different timeunits.
 * @author Moritz Schneider
 *
 */
public enum TimeName {

    /**
     * Enum for storing seconds. One second equals one second.
     */
    SECONDS("Sekunden", BigDecimal.ONE),
    /**
     * Enum for storing minutes. One minute equals 60 seconds.
     */
    MINUTES("Minuten", new BigDecimal("60")),
    /**
     * Enum for storing hours. One hour equals 3600 seconds.
     */
    HOURS("Stunden", new BigDecimal("3600")),
    /**
     * Enum for storing days. One day equals 86400 seconds.
     */
    DAYS("Tage", new BigDecimal("86400")),
    /**
     * Enum for storing weeks. One week equals 604800 seconds.
     */
    WEEKS("Wochen", new BigDecimal("604800")),
    /**
     * Enum for storing months. One month equals 18144000 seconds.
     */
    MONTHS("Monate", new BigDecimal("18144000")),
    /**
     * Enum for storing years. One year equals 217728000 seconds.
     */
    YEARS("Jahre", new BigDecimal("217728000")),
    /**
     * Enum for storing centuries. On century equals 21772800000 seconds.
     */
    CENTURIES("Jahrhunderte", new BigDecimal("21772800000")),
    /**
     * Enum for storing millenials. On millenial equals 217728000000 seconds.
     */
    MILLENIALS("Jahrtausende", new BigDecimal("217728000000")),
    /**
     * Enum for storing megannums. On century equals 217728000000000 seconds.
     */
    MEGANNUMS("Jahrmillionen", new BigDecimal("217728000000000")),
    /**
     * Enum for storing aeons. On century equals 217728000000000000 seconds.
     */
    AEONS("\u00c4onen", new BigDecimal("217728000000000000"));

    
    /**
     * The name of the timeunit.
     */
    private String name;
    /**
     * The number of seconds contained in the timeunit.
     */
    private BigDecimal numOfSeconds;
    
    /**
     * Map for associating names to enums.
     */
    public static final Map<String, TimeName> TIMENAME_MAP = new HashMap<>();
    /**
     * Map for associating the number of contained seconds to enums.
     */
    public static final Map<BigDecimal, TimeName> TIMESECONDS_MAP = new HashMap<>();
    
    /**
     * Class constructor
     * @param name the name of the timeunit.
     * @param numOfSeconds the number of seconds contained in the timeunit.
     */
    TimeName(final String name, final BigDecimal numOfSeconds) {
        this.name = name;
        this.numOfSeconds = numOfSeconds;
    }
    
    static {
        for(final TimeName timename : EnumSet.allOf(TimeName.class)) {
            TIMENAME_MAP.put(timename.getName(), timename);
            TIMESECONDS_MAP.put(timename.getNumOfSeconds(), timename);
        }
    }
    
    /**
     * Gets the enum-object for the inputname.
     * @param name the name of the timeunit.
     * @return the associated enum-object.
     */
    public TimeName getForName(final String name) {
        return TIMENAME_MAP.get(name);
    }
    
    /**
     * Gets the enum-object for the number of contained seconds in the timeunit.
     * @param seconds the number of seconds contained in the timeunit.
     * @return the associated enum-object.
     */
    public TimeName getForNumOfSeconds(final BigDecimal seconds) {
        return TIMESECONDS_MAP.get(seconds);
    }

    /**
     * Gets the name.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the numOfSeconds.
     * @return the numOfSeconds.
     */
    public BigDecimal getNumOfSeconds() {
        return numOfSeconds;
    }
    
    
}
