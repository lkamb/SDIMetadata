package gov.noaa.pmel.sdimetadata.apps;

import gov.noaa.pmel.sdimetadata.SDIMetadata;
import gov.noaa.pmel.sdimetadata.xml.CdiacReader;
import gov.noaa.pmel.sdimetadata.xml.CdiacReader.VarType;
import gov.noaa.pmel.sdimetadata.xml.OcadsWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Properties;

public class CdiacToOcadsConverter {

    /**
     * Converts, to the extent possible, the metadata content from CDIAC XML to OCADS XML.
     * All content in the CDIAC XML should be present somewhere in the OCADS XML, although
     * not guaranteed to be in the most appropriate place.
     *
     * @param args
     *         -- CDIAC XML filename - read CDIAC XML from the file with this name
     *         -- OCADS XML filename - write OCADS XML to the file with this name
     *         -- Keys-to-Types properties filename (optional) - read supplemental mappings
     *         of column name keys (non-alphanumeric removed, lower-cased) to type names
     *         from the properties file (in format key=type) with this name
     */
    public static void main(String[] args) {
        if ( (args.length < 2) || (args.length > 3) ) {
            System.err.println();
            System.err.println("Arguments: CDIAC_input.xml OCADS_output.xml [ Addn_ColNameKeys_to_Types.properties ]");
            System.err.println();
            System.err.println("Converts, to the extent possible, the metadata content from CDIAC XML to OCADS XML.");
            System.err.println("All content in the CDIAC XML should be present somewhere in the OCADS XML, although");
            System.err.println("not guaranteed to be in the most appropriate place.  The optional third file named");
            System.err.println("is a properties file containing supplemental mappings of column name keys");
            System.err.println("(non-alphanumeric removed, lower-cased) to one of the type names (case insensitive):");
            System.err.println("FCO2_WATER_EQU, FCO2_WATER_SST, PCO2_WATER_EQU, PCO2_WATER_SST, XCO2_WATER_EQU,");
            System.err.println("XCO2_WATER_SST, FCO2_ATM_ACTUAL, FCO2_ATM_INTERP, PCO2_ATM_ACTUAL, PCO2_ATM_INTERP,");
            System.err.println("XCO2_ATM_ACTUAL, XCO2_ATM_INTERP, SEA_SURFACE_TEMPERATURE, EQUILIBRATOR_TEMPERATURE,");
            System.err.println("SEA_LEVEL_PRESSURE, EQUILIBRATOR_PRESSURE, SALINITY, WOCE_CO2_WATER, WOCE_CO2_ATM,");
            System.err.println("or OTHER");
            System.err.println();
            System.exit(1);
        }

        HashMap<String,CdiacReader.VarType> addnMap = null;
        if ( args.length == 3 ) {
            addnMap = new HashMap<String,VarType>();
            try {
                Properties props = new Properties();
                props.load(new FileReader(args[2]));
                for (String key : props.stringPropertyNames()) {
                    String value = props.getProperty(key);
                    VarType type = VarType.valueOf(value.trim().toUpperCase());
                    addnMap.put(key.trim().toLowerCase(), type);
                    addnMap.put(key, type);
                }
            } catch ( Exception ex ) {
                System.err
                        .println("Problems reading the column name keys to types properties file: " + ex.getMessage());
                System.exit(1);
            }
        }

        SDIMetadata metadata = null;
        try {
            FileReader xmlReader = new FileReader(args[0]);
            CdiacReader cdiacReader = new CdiacReader(xmlReader, addnMap);
            xmlReader.close();
            metadata = cdiacReader.createSDIMetadata();
        } catch ( Exception ex ) {
            System.err.println("Problems reading the CDIAC XML file '" + args[0] + "':");
            String msg = ex.getMessage();
            if ( (msg != null) && !msg.trim().isEmpty() )
                System.err.println("\t" + msg);
            else
                ex.printStackTrace();
            System.exit(1);
        }

        try {
            FileWriter xmlWriter = new FileWriter(args[1]);
            OcadsWriter ocadsWriter = new OcadsWriter();
            ocadsWriter.writeSDIMetadata(metadata, xmlWriter);
            xmlWriter.close();
        } catch ( Exception ex ) {
            System.err.println("Problems writing the OCADS XML file '" + args[1] + "':");
            String msg = ex.getMessage();
            if ( (msg != null) && !msg.trim().isEmpty() )
                System.err.println("\t" + msg);
            else
                ex.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

}

