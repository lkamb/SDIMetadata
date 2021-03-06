package gov.noaa.pmel.sdimetadata.variable;

import gov.noaa.pmel.sdimetadata.util.NumericString;

/**
 * Information about a temperature measurement.
 * The default unit is set to degrees Celsius.
 */
public class Temperature extends DataVar implements Cloneable {

    public static final String DEGREES_CELSIUS_UNIT = "deg C";

    /**
     * Create with all fields empty, except for units which are set to degrees Celsius.
     */
    public Temperature() {
        super();
        varUnit = DEGREES_CELSIUS_UNIT;
        accuracy.setUnitString(DEGREES_CELSIUS_UNIT);
        precision.setUnitString(DEGREES_CELSIUS_UNIT);
    }

    /**
     * Create using values in the given variable. If a DataVar is given, all DataVar fields are copied.
     * If a Temperature is given, all Temperature fields are copied.
     */
    public Temperature(Variable var) {
        super(var);
        if ( (var != null) && ( var instanceof Temperature) ) {
            Temperature temp = (Temperature) var;
            varUnit = temp.varUnit;
            accuracy = temp.accuracy.clone();
            precision = temp.precision.clone();
        }
        else {
            varUnit = DEGREES_CELSIUS_UNIT;
            accuracy.setUnitString(DEGREES_CELSIUS_UNIT);
            precision.setUnitString(DEGREES_CELSIUS_UNIT);
        }
    }

    /**
     * @param varUnit
     *         assign as the unit for values of this variable; if null or blank, degrees Celsius is assigned
     */
    @Override
    public void setVarUnit(String varUnit) {
        this.varUnit = (varUnit != null) ? varUnit.trim() : DEGREES_CELSIUS_UNIT;
        if ( this.varUnit.isEmpty() )
            this.varUnit = DEGREES_CELSIUS_UNIT;
    }

    /**
     * @param accuracy
     *         assign as the accuracy (uncertainty) in values of this variable;
     *         if null, an NumericString with an empty numeric value but units of degrees Celsius is assigned
     *
     * @throws IllegalArgumentException
     *         if a numeric string is given but is not a finite positive number
     */
    @Override
    public void setAccuracy(NumericString accuracy) throws IllegalArgumentException {
        if ( accuracy != null ) {
            super.setAccuracy(accuracy);
        }
        else
            this.accuracy = new NumericString(null, DEGREES_CELSIUS_UNIT);
    }

    /**
     * @param precision
     *         assign as the precision (resolution) in values of this variable;
     *         if null, an NumericString with an empty numeric value but units of degrees Celsius is assigned
     *
     * @throws IllegalArgumentException
     *         if a numeric string is given but is not a finite positive number
     */
    @Override
    public void setPrecision(NumericString precision) throws IllegalArgumentException {
        if ( precision != null ) {
            super.setPrecision(precision);
        }
        else
            this.precision = new NumericString(null, DEGREES_CELSIUS_UNIT);
    }

    @Override
    public Temperature clone() {
        return (Temperature) super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( null == obj )
            return false;
        if ( !(obj instanceof Temperature) )
            return false;
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("DataVar", "Temperature");
    }

}

