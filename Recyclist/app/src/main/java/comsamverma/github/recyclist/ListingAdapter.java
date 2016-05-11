package comsamverma.github.recyclist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sam on 4/10/2016.
 */
public class ListingAdapter extends ArrayAdapter<Listing> {
    public ListingAdapter(Context context, ArrayList<Listing> listings){
        super(context, 0, listings);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Listing listing = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listing, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.listingitemtitle);
        TextView content = (TextView) convertView.findViewById(R.id.listingitemcontent);
        TextView cost = (TextView) convertView.findViewById(R.id.listingitemcost);
        TextView state = (TextView) convertView.findViewById(R.id.listingitemstate);

        title.setText(listing.title);
        content.setText(listing.content);
        cost.setText(listing.cost);
        state.setText(listing.state);

        return convertView;
    }
}
