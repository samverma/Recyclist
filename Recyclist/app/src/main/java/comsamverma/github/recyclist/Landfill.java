package comsamverma.github.recyclist;

/**
 * Created by sam on 4/5/2016.
 */
public class Landfill {
        String facility_name;
        String facility_street;
        String facility_city;
        String facility_state;
        String facility_zip_code;

        Landfill(){}

        Landfill(String fn, String fst, String fc, String fs, String fz){
            facility_name=fn;
            facility_city=fc;
            facility_state=fs;
            facility_street=fst;
            facility_zip_code=fz;
        }
}
