package com.irurueta.units;

import java.text.ParseException;
import java.util.Locale;

/**
 * Formats and parses temperature value and unit.
 */
public class TemperatureFormatter extends MeasureFormatter<Temperature, TemperatureUnit>
        implements Cloneable {

    /**
     * Celsius symbol.
     */
    public static final String CELSIUS = "ºC";

    /**
     * Fahrenheit symbol.
     */
    public static final String FAHRENHEIT = "ºF";

    /**
     * Kelvin symbol.
     */
    public static final String KELVIN = "K";

    /**
     * Constructor.
     */
    public TemperatureFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public TemperatureFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public TemperatureFormatter(final TemperatureFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two temperature formatters are equal by comparing all of their
     * internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof TemperatureFormatter) && equals;
    }

    /**
     * Gets unit system for detected unit into provided string representation
     * of a measurement.
     *
     * @param source a measurement string representation to be checked.
     * @return a unit system (either metric or imperial) or null if unit
     * cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(final String source) {
        final TemperatureUnit unit = findUnit(source);
        return unit != null ? TemperatureUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine a temperature value and unit.
     *
     * @param source a string to be parsed.
     * @return a temperature containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Temperature parse(final String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Temperature());
    }

    /**
     * Attempts to determine a temperature unit within a measurement string
     * representation.
     *
     * @param source a measurement string representation.
     * @return a temperature unit, or null if none can be determined.
     */
    @Override
    public TemperatureUnit findUnit(final String source) {
        if (source.contains(CELSIUS + " ") || source.endsWith(CELSIUS)) {
            return TemperatureUnit.CELSIUS;
        }
        if (source.contains(FAHRENHEIT + " ") || source.endsWith(FAHRENHEIT)) {
            return TemperatureUnit.FAHRENHEIT;
        }
        if (source.contains(KELVIN + " ") || source.endsWith(KELVIN)) {
            return TemperatureUnit.KELVIN;
        }
        return null;
    }

    /**
     * Formats and converts provided temperature value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     * <p>
     * This implementation just formats provided value using provided unit,
     * ignoring provided unit system.
     *
     * @param value  a temperature value.
     * @param unit   a temperature unit.
     * @param system system unit to convert temperature to.
     * @return a string representation of temperature value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final TemperatureUnit unit,
            final UnitSystem system) {
        return format(value, unit);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a temperature unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final TemperatureUnit unit) {
        switch (unit) {
            case FAHRENHEIT:
                return FAHRENHEIT;
            case CELSIUS:
                return CELSIUS;
            case KELVIN:
            default:
                return KELVIN;
        }
    }

    /**
     * Clones this temperature formatter.
     *
     * @return a copy of this temperature formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final TemperatureFormatter copy = (TemperatureFormatter) super.clone();
        return internalClone(copy);
    }
}
