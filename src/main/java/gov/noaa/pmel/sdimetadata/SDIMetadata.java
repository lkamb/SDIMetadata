package gov.noaa.pmel.sdimetadata;

import gov.noaa.pmel.sdimetadata.instrument.Instrument;
import gov.noaa.pmel.sdimetadata.person.Investigator;
import gov.noaa.pmel.sdimetadata.person.Submitter;
import gov.noaa.pmel.sdimetadata.platform.Platform;
import gov.noaa.pmel.sdimetadata.variable.Variable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class SDIMetadata implements Cloneable {

    protected Submitter submitter;
    protected ArrayList<Investigator> investigators;
    protected Platform platform;
    protected Coverage coverage;
    protected ArrayList<Instrument> instruments;
    protected ArrayList<Variable> variables;
    protected MiscInfo miscInfo;

    public SDIMetadata() {
        submitter = new Submitter();
        investigators = new ArrayList<Investigator>();
        platform = new Platform();
        coverage = new Coverage();
        instruments = new ArrayList<Instrument>();
        variables = new ArrayList<Variable>();
        miscInfo = new MiscInfo();
    }

    /**
     * @return list of field names that are currently invalid
     */
    public HashSet<String> invalidFieldNames() {
        HashSet<String> invalid = new HashSet<String>();

        for (String name : submitter.invalidFieldNames()) {
            invalid.add("submitter." + name);
        }
        for (int k = 0; k < investigators.size(); k++) {
            for (String name : investigators.get(k).invalidFieldNames()) {
                invalid.add("investigators[" + k + "]." + name);
            }
        }
        for (String name : platform.invalidFieldNames()) {
            invalid.add("platform." + name);
        }
        for (String name : coverage.invalidFieldNames()) {
            invalid.add("coverage." + name);
        }
        for (int k = 0; k < instruments.size(); k++) {
            for (String name : instruments.get(k).invalidFieldNames()) {
                invalid.add("instruments[" + k + "]." + name);
            }
        }
        for (int k = 0; k < variables.size(); k++) {
            for (String name : variables.get(k).invalidFieldNames()) {
                invalid.add("variables[" + k + "]." + name);
            }
        }
        for (String name : miscInfo.invalidFieldNames()) {
            invalid.add("miscInfo." + name);
        }

        // check that the data times are all within the specified time range
        // (beginning of start day; end of ending day) for the dataset
        if ( !(invalid.contains("miscInfo.startDatestamp") || invalid.contains("coverage.earliestDataTime")) ) {
            Date start = miscInfo.getStartDatestamp().getEarliestTime();
            if ( start.after(coverage.getEarliestDataTime()) ) {
                invalid.add("miscInfo.startDatestamp");
                invalid.add("coverage.earliestDataTime");
            }
        }
        if ( !(invalid.contains("miscInfo.endDatestamp") || invalid.contains("coverage.latestDataTime")) ) {
            Date end = new Date(miscInfo.getEndDatestamp().getEarliestTime().getTime() + 24L * 60L * 60L * 1000L);
            if ( end.before(coverage.getLatestDataTime()) ) {
                invalid.add("miscInfo.endDatestamp");
                invalid.add("coverage.latestDataTime");
            }
        }

        // TODO: verify researcher and instrument names in variables match some entry in investigators and instruments

        return invalid;
    }

    public Submitter getSubmitter() {
        return submitter.clone();
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = (submitter != null) ? submitter.clone() : new Submitter();
    }

    public ArrayList<Investigator> getInvestigators() {
        ArrayList<Investigator> piList = new ArrayList<Investigator>(investigators.size());
        for (Investigator pi : investigators) {
            piList.add(pi.clone());
        }
        return piList;
    }

    public void setInvestigators(Iterable<Investigator> investigators) throws IllegalArgumentException {
        this.investigators.clear();
        if ( investigators != null ) {
            for (Investigator pi : investigators) {
                if ( null == pi )
                    throw new IllegalArgumentException("null investigator given");
                this.investigators.add(pi.clone());
            }
        }
    }

    public Platform getPlatform() {
        return platform.clone();
    }

    public void setPlatform(Platform platform) {
        this.platform = (platform != null) ? platform.clone() : new Platform();
    }

    public Coverage getCoverage() {
        return coverage.clone();
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = (coverage != null) ? coverage.clone() : new Coverage();
    }

    public ArrayList<Instrument> getInstruments() {
        ArrayList<Instrument> instList = new ArrayList<Instrument>(instruments.size());
        for (Instrument inst : instruments) {
            instList.add(inst.clone());
        }
        return instList;
    }

    public void setInstruments(Iterable<Instrument> instruments) throws IllegalArgumentException {
        this.instruments.clear();
        if ( instruments != null ) {
            for (Instrument inst : instruments) {
                if ( null == inst )
                    throw new IllegalArgumentException("null instrument given");
                this.instruments.add(inst.clone());
            }
        }
    }

    public ArrayList<Variable> getVariables() {
        ArrayList<Variable> varList = new ArrayList<Variable>(variables.size());
        for (Variable var : variables) {
            varList.add(var.clone());
        }
        return varList;
    }

    public void setVariables(Iterable<Variable> variables) throws IllegalArgumentException {
        this.variables.clear();
        if ( variables != null ) {
            for (Variable var : variables) {
                if ( null == var )
                    throw new IllegalArgumentException("null variable given");
                this.variables.add(var.clone());
            }
        }
    }

    public MiscInfo getMiscInfo() {
        return miscInfo.clone();
    }

    public void setMiscInfo(MiscInfo miscInfo) {
        this.miscInfo = (miscInfo != null) ? miscInfo.clone() : new MiscInfo();
    }

    @Override
    public SDIMetadata clone() {
        SDIMetadata dup;
        try {
            dup = (SDIMetadata) super.clone();
        } catch ( CloneNotSupportedException ex ) {
            throw new RuntimeException(ex);
        }
        dup.submitter = submitter.clone();
        dup.investigators = new ArrayList<Investigator>(investigators.size());
        for (Investigator pi : investigators) {
            dup.investigators.add(pi.clone());
        }
        dup.platform = platform.clone();
        dup.coverage = coverage.clone();
        dup.instruments = new ArrayList<Instrument>(instruments.size());
        for (Instrument inst : instruments) {
            dup.instruments.add(inst.clone());
        }
        dup.variables = new ArrayList<Variable>(variables.size());
        for (Variable var : variables) {
            dup.variables.add(var.clone());
        }
        dup.miscInfo = miscInfo.clone();
        return dup;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( null == obj )
            return false;
        if ( !(obj instanceof SDIMetadata) )
            return false;

        SDIMetadata other = (SDIMetadata) obj;

        if ( !submitter.equals(other.submitter) )
            return false;
        if ( !investigators.equals(other.investigators) )
            return false;
        if ( !platform.equals(other.platform) )
            return false;
        if ( !coverage.equals(other.coverage) )
            return false;
        if ( !instruments.equals(other.instruments) )
            return false;
        if ( !variables.equals(other.variables) )
            return false;
        return miscInfo.equals(other.miscInfo);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = submitter.hashCode();
        result = result * prime + investigators.hashCode();
        result = result * prime + platform.hashCode();
        result = result * prime + coverage.hashCode();
        result = result * prime + instruments.hashCode();
        result = result * prime + variables.hashCode();
        result = result * prime + miscInfo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SDIMetadata{" +
                "submitter=" + submitter +
                ", investigators=" + investigators +
                ", platform=" + platform +
                ", coverage=" + coverage +
                ", instruments=" + instruments +
                ", variables=" + variables +
                ", miscInfo=" + miscInfo +
                '}';
    }

}

