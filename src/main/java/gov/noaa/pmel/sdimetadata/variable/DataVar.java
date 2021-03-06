package gov.noaa.pmel.sdimetadata.variable;

import gov.noaa.pmel.sdimetadata.person.Person;

import java.util.HashSet;

/**
 * Information about a generic data variable in a dataset.
 */
public class DataVar extends Variable implements Cloneable {

    protected String observeType;
    protected MethodType measureMethod;
    protected String methodDescription;
    protected String methodReference;
    protected String manipulationDescription;
    protected String samplingLocation;
    protected String samplingElevation;
    protected String storageMethod;
    protected String duration;
    protected String analysisTemperature;
    protected String replication;
    protected Person researcher;
    protected HashSet<String> instrumentNames;

    /**
     * Create with all fields empty.
     */
    public DataVar() {
        super();
        observeType = "";
        measureMethod = MethodType.UNSPECIFIED;
        methodDescription = "";
        methodReference = "";
        manipulationDescription = "";
        samplingLocation = "";
        samplingElevation = "";
        storageMethod = "";
        duration = "";
        analysisTemperature = "";
        replication = "";
        researcher = new Person();
        instrumentNames = new HashSet<String>();
    }

    /**
     * Create using values in the given variable. If a DataVar is given, all DataVar fields are copied.
     */
    public DataVar(Variable var) {
        super(var);
        if ( (var != null) && (var instanceof DataVar) ) {
            DataVar other = (DataVar) var;
            observeType = other.observeType;
            measureMethod = other.measureMethod;
            methodDescription = other.methodDescription;
            methodReference = other.methodReference;
            manipulationDescription = other.manipulationDescription;
            samplingLocation = other.samplingLocation;
            samplingElevation = other.samplingElevation;
            storageMethod = other.storageMethod;
            duration = other.duration;
            analysisTemperature = other.analysisTemperature;
            replication = other.replication;
            researcher = other.researcher.clone();
            instrumentNames = new HashSet<String>(other.instrumentNames);
        }
        else {
            observeType = "";
            measureMethod = MethodType.UNSPECIFIED;
            methodDescription = "";
            methodReference = "";
            manipulationDescription = "";
            samplingLocation = "";
            samplingElevation = "";
            storageMethod = "";
            duration = "";
            analysisTemperature = "";
            replication = "";
            researcher = new Person();
            instrumentNames = new HashSet<String>();
        }
    }

    @Override
    public HashSet<String> invalidFieldNames() {
        HashSet<String> invalid = new HashSet<String>();
        if ( colName.isEmpty() )
            invalid.add("colName");
        if ( fullName.isEmpty() )
            invalid.add("fullName");
        if ( observeType.isEmpty() )
            invalid.add("observeType");
        if ( !accuracy.isValid() )
            invalid.add("accuracy");
        switch ( measureMethod ) {
            case UNSPECIFIED:
                invalid.add("measureMethod");
                break;
            case COMPUTED:
                if ( methodDescription.isEmpty() )
                    invalid.add("methodDescription");
                break;
            case MANIPULATION:
                if ( methodDescription.isEmpty() && manipulationDescription.isEmpty() )
                    invalid.add("manipulationDescription");
                break;
            default:
                if ( instrumentNames.isEmpty() )
                    invalid.add("instrumentNames");
        }
        return invalid;
    }


    /**
     * @return the observation type of this variable; never null but may be empty
     */
    public String getObserveType() {
        return observeType;
    }

    /**
     * @param observeType
     *         assign as the observation type of this variable; if null, an empty string is assigned
     */
    public void setObserveType(String observeType) {
        this.observeType = (observeType != null) ? observeType.trim() : "";
    }

    /**
     * @return the method of measuring this variable; never null but may be {@link MethodType#UNSPECIFIED}
     */
    public MethodType getMeasureMethod() {
        return measureMethod;
    }

    /**
     * @param measureMethod
     *         assign as the method of measuring this variable; if null, {@link MethodType#UNSPECIFIED} is assigned
     */
    public void setMeasureMethod(MethodType measureMethod) {
        this.measureMethod = (measureMethod != null) ? measureMethod : MethodType.UNSPECIFIED;
    }

    /**
     * @return description of the method for computing this variable; never null but may be empty
     */
    public String getMethodDescription() {
        return methodDescription;
    }

    /**
     * @param methodDescription
     *         assign as the method for computing this variable; if null, an empty string is assigned
     */
    public void setMethodDescription(String methodDescription) {
        this.methodDescription = (methodDescription != null) ? methodDescription.trim() : "";
    }

    /**
     * @return the reference for the method used to obtain the values of this variable; never null but may be empty
     */
    public String getMethodReference() {
        return methodReference;
    }

    /**
     * @param methodReference
     *         assign as the reference for the method used to obtain the values of this variable;
     *         if null, an empty string is assigned
     */
    public void setMethodReference(String methodReference) {
        this.methodReference = (methodReference != null) ? methodReference.trim() : "";
    }

    /**
     * @return description of the manipulation described by this variable; never null but may be empty
     */
    public String getManipulationDescription() {
        return manipulationDescription;
    }

    /**
     * @param manipulationDescription
     *         assign as the description of the manipulation described by this variable;
     *         if null, an empty string is assigned
     */
    public void setManipulationDescription(String manipulationDescription) {
        this.manipulationDescription = (manipulationDescription != null) ? manipulationDescription.trim() : "";
    }

    /**
     * @return sampling location for this variable; never null but may be an empty string
     */
    public String getSamplingLocation() {
        return samplingLocation;
    }

    /**
     * @param samplingLocation
     *         assign as the sampling location for this variable; if null, an empty string is assigned
     */
    public void setSamplingLocation(String samplingLocation) {
        this.samplingLocation = (samplingLocation != null) ? samplingLocation.trim() : "";
    }

    /**
     * @return sampling height / depth for this variable; never null but may be an empty string
     */
    public String getSamplingElevation() {
        return samplingElevation;
    }

    /**
     * @param samplingElevation
     *         assign as the sampling height / depth for this variable; if null, an empty string is assigned
     */
    public void setSamplingElevation(String samplingElevation) {
        this.samplingElevation = (samplingElevation != null) ? samplingElevation.trim() : "";
    }

    /**
     * @return information about sample storage prior to measuring this variable; never null but may be empty
     */
    public String getStorageMethod() {
        return storageMethod;
    }

    /**
     * @param storageMethod
     *         assign as information about sample storage prior to measuring this variable;
     *         if null, an empty string is assigned
     */
    public void setStorageMethod(String storageMethod) {
        this.storageMethod = (storageMethod != null) ? storageMethod.trim() : "";
    }

    /**
     * @return duration for settlement, colonization, or experiment studies; never null but may be empty
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     *         assign as the duration for settlement, colonization, or experiment studies;
     *         if null, an empty string is assigned
     */
    public void setDuration(String duration) {
        this.duration = (duration != null) ? duration.trim() : "";
    }

    /**
     * @return the water temperature at which the gas concentration was measured;
     *         never null but may be empty
     */
    public String getAnalysisTemperature() {
        return analysisTemperature;
    }

    /**
     * @param analysisTemperature
     *         assign as the water temperature at which the gas concentration was measured;
     *         if null or blank, an empty string is assigned
     */
    public void setAnalysisTemperature(String analysisTemperature) {
        this.analysisTemperature = (analysisTemperature != null) ? analysisTemperature.trim() : "";
    }

    /**
     * @return replication information about this variable; never null but may be empty
     */
    public String getReplication() {
        return replication;
    }

    /**
     * @param replication
     *         assign as replication information about this variable; if null, an empty string is assigned
     */
    public void setReplication(String replication) {
        this.replication = (replication != null) ? replication.trim() : "";
    }

    /**
     * @return reference to the investigator responsible for obtaining this variable;
     *         never null but may not be a valid investigator reference
     */
    public Person getResearcher() {
        return researcher.clone();
    }

    /**
     * @param researcher
     *         assign as the reference to the investigator responsible for obtaining this variable;
     *         if null, an invalid reference (a Person with all-empty fields) is assigned
     */
    public void setResearcher(Person researcher) {
        this.researcher = (researcher != null) ? researcher.clone() : new Person();
    }

    /**
     * @return the list of names of instruments used to sample or analyze this variable; never null but may be empty.
     *         Any strings given are guaranteed to be strings with content (not blank).
     */
    public HashSet<String> getInstrumentNames() {
        return new HashSet<String>(instrumentNames);
    }

    /**
     * @param instrumentNames
     *         assign as the list of names of instruments used to sample or analyze this variable;
     *         if null, an empty list is assigned
     *
     * @throws IllegalArgumentException
     *         if any analyzer name given is null or blank
     */
    public void setInstrumentNames(Iterable<String> instrumentNames) throws IllegalArgumentException {
        this.instrumentNames.clear();
        if ( instrumentNames != null ) {
            for (String name : instrumentNames) {
                if ( name == null )
                    throw new IllegalArgumentException("null instrument name given");
                name = name.trim();
                if ( name.isEmpty() )
                    throw new IllegalArgumentException("blank instrument name given");
                this.instrumentNames.add(name);
            }
        }
    }

    @Override
    public DataVar clone() {
        DataVar dup = (DataVar) super.clone();
        dup.observeType = observeType;
        dup.measureMethod = measureMethod;
        dup.methodDescription = methodDescription;
        dup.methodReference = methodReference;
        dup.manipulationDescription = manipulationDescription;
        dup.samplingLocation = samplingLocation;
        dup.samplingElevation = samplingElevation;
        dup.storageMethod = storageMethod;
        dup.duration = duration;
        dup.analysisTemperature = analysisTemperature;
        dup.replication = replication;
        dup.researcher = researcher.clone();
        dup.instrumentNames = new HashSet<String>(instrumentNames);
        return dup;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( null == obj )
            return false;
        if ( !(obj instanceof DataVar) )
            return false;
        if ( !super.equals(obj) )
            return false;

        DataVar dataVar = (DataVar) obj;

        if ( !observeType.equals(dataVar.observeType) )
            return false;
        if ( measureMethod != dataVar.measureMethod )
            return false;
        if ( !methodDescription.equals(dataVar.methodDescription) )
            return false;
        if ( !methodReference.equals(dataVar.methodReference) )
            return false;
        if ( !manipulationDescription.equals(dataVar.manipulationDescription) )
            return false;
        if ( !samplingLocation.equals(dataVar.samplingLocation) )
            return false;
        if ( !samplingElevation.equals(dataVar.samplingElevation) )
            return false;
        if ( !storageMethod.equals(dataVar.storageMethod) )
            return false;
        if ( !duration.equals(dataVar.duration) )
            return false;
        if ( !analysisTemperature.equals(dataVar.analysisTemperature) )
            return false;
        if ( !replication.equals(dataVar.replication) )
            return false;
        if ( !researcher.equals(dataVar.researcher) )
            return false;
        if ( !instrumentNames.equals(dataVar.instrumentNames) )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = super.hashCode();
        result = result * prime + observeType.hashCode();
        result = result * prime + measureMethod.hashCode();
        result = result * prime + methodDescription.hashCode();
        result = result * prime + methodReference.hashCode();
        result = result * prime + manipulationDescription.hashCode();
        result = result * prime + samplingLocation.hashCode();
        result = result * prime + samplingElevation.hashCode();
        result = result * prime + storageMethod.hashCode();
        result = result * prime + duration.hashCode();
        result = result * prime + analysisTemperature.hashCode();
        result = result * prime + replication.hashCode();
        result = result * prime + researcher.hashCode();
        result = result * prime + instrumentNames.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String repr = super.toString().replaceFirst("Variable", "DataVar");
        return repr.substring(0, repr.length() - 1) +
                ", observeType='" + observeType + '\'' +
                ", measureMethod=" + measureMethod +
                ", methodDescription='" + methodDescription + '\'' +
                ", methodReference='" + methodReference + '\'' +
                ", manipulationDescription='" + manipulationDescription + '\'' +
                ", samplingLocation='" + samplingLocation + '\'' +
                ", samplingElevation='" + samplingElevation + '\'' +
                ", storageMethod='" + storageMethod + '\'' +
                ", duration='" + duration + '\'' +
                ", analysisTemperature='" + analysisTemperature + '\'' +
                ", replication='" + replication + '\'' +
                ", researcher=" + researcher +
                ", instrumentNames=" + instrumentNames +
                '}';
    }
}

