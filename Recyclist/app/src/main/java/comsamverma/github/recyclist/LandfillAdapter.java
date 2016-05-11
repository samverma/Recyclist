package comsamverma.github.recyclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sam on 4/5/2016.
 */
public class LandfillAdapter extends ArrayAdapter<Landfill> {
    public LandfillAdapter(Context context, ArrayList<Landfill> fills){
        super(context, 0, fills);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Landfill fill = getItem(position);
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_landfill, parent, false);
        }
            TextView facility_name = (TextView) convertView.findViewById(R.id.facilityName);
            TextView facility_street = (TextView) convertView.findViewById(R.id.facilityStreet);
            TextView facility_city = (TextView) convertView.findViewById(R.id.facilityCity);
            TextView facility_state = (TextView) convertView.findViewById(R.id.facilityState);
            TextView facility_zip = (TextView) convertView.findViewById(R.id.facilityZip);

            facility_name.setText(fill.facility_name);
            facility_street.setText(fill.facility_street);
            facility_city.setText(fill.facility_city);
            facility_state.setText(fill.facility_state);
            facility_zip.setText(fill.facility_zip_code);

            return convertView;
        }
}


