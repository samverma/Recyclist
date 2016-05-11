package comsamverma.github.recyclist;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.provider.SyncStateContract.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;


public class NFService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String RSS_LINK = "http://feeds.sciencedaily.com/sciencedaily/earth_climate/recycling_and_waste?format=xml";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public NFService() {
        super("NFService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.d(Constants.TAG, "Service started");
        List<NFItem> nfItems = null;
        try {
            RssParser parser = new RssParser();
            nfItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) nfItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link){
        try{
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            //Log.w(Constants.TAG, "Exception while retrieving the input steam", e);
            return null;
        }
    }
}
