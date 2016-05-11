package comsamverma.github.recyclist;

/**
 * Created by sam on 4/17/2016.
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class NewsFeedActivity extends AppCompatActivity {

    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    String currUserToken;
    boolean loggedIn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        loggedIn = false;
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        if(currUserToken!=null)loggedIn = true;

       // new MyTask().execute();
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            addNFFragment();
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_home:
                Intent intent4 = new Intent(this, MainActivity.class);
                if(loggedIn) intent4.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent4);
                break;
            case R.id.action_search:
                Intent intent = new Intent(this,ResListingsActivity.class);
                if(loggedIn) intent.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent);
                break;
            case R.id.action_aboutus:
                Intent intent2 = new Intent(this, AboutActivity.class);
                if(loggedIn)intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_resources:
                Intent intent3 = new Intent(this, CommInfoActivity.class);
                if(loggedIn) intent3.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent3);
                break;
            case R.id.action_tou:
                Intent intent6 = new Intent(this, LegalActivity.class);
                if(loggedIn)intent6.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent6);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void addNFFragment(){
        FragmentManager manager =  getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        NFFragment fragment = new NFFragment();
        transaction.add(R.id.nf_container, fragment);
        transaction.commit();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }
}