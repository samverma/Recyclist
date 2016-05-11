package comsamverma.github.recyclist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;



public class NFFragment extends Fragment implements OnItemClickListener {

    private ProgressBar progressBar;
    private ListView listView;
    private View view;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(view == null){
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            listView = (ListView)view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            startService();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), NFService.class);
        intent.putExtra(NFService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link NFService} finishes its task, the result is sent to this ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()){
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            List<NFItem> items = (List<NFItem>) resultData.getSerializable(NFService.ITEMS);
            if(items != null){
                NFAdapter adapter = new NFAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occured while downloading the news feed.",Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        };
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NFAdapter adapter = (NFAdapter) parent.getAdapter();
        NFItem item = (NFItem) adapter.getItem(position);
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
