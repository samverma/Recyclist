package comsamverma.github.recyclist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.w3c.dom.Text;


public class AboutActivity extends AppCompatActivity {
    String currUserToken;
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        loggedIn=false;
        setTitle(R.string.title_activity_about);
        //getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_about);
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        if(currUserToken!=null)loggedIn=true;
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        TextView tv1 = (TextView) findViewById(R.id.wwa);
        tv1.setTypeface(tf1);

        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        TextView tv2 = (TextView) findViewById(R.id.luhy);
        tv2.setTypeface(tf2);
        TextView tv3 = (TextView) findViewById(R.id.AboutMission);
        tv3.setTypeface(tf1);
        TextView tv4 = (TextView) findViewById(R.id.dg);
        tv4.setTypeface(tf1);
        TextView tv5 = (TextView) findViewById(R.id.wwat);
        tv5.setTypeface(tf2);
        TextView tv6 = (TextView) findViewById(R.id.dgt);
        tv6.setTypeface(tf2);
        TextView tv7 = (TextView) findViewById(R.id.dwb);
        tv7.setTypeface(tf2);
        TextView tv8 = (TextView)findViewById(R.id.AboutMissionText);
        tv8.setTypeface(tf2);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
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
                if(loggedIn){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("currentUserToken",currUserToken);
                    finish();
                    this.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, MainActivity.class);
                    finish();
                    this.startActivity(intent);
                }
                break;
            case R.id.action_search:
                Intent intent2 = new Intent(this,ResListingsActivity.class);
                if(loggedIn)intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_resources:
                Intent intent3 = new Intent(this, CommInfoActivity.class);
                if(loggedIn)intent3.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent3);
                break;
            case R.id.action_news:
                Intent intent5 = new Intent(this, NewsFeedActivity.class);
                if(loggedIn)intent5.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent5);
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
}


