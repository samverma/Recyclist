package comsamverma.github.recyclist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by sam on 4/20/2016.
 */
public class ListingDetailActivity extends AppCompatActivity {
    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    boolean loggedIn;
    String currUserToken;
    int id;
    private TextView ldtitle, ldcost, ldcontent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listingdetail);
        loggedIn = false;
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        if (currUserToken != null) loggedIn = true;
        id = intent.getIntExtra("id", 0);
        ldtitle = (TextView) findViewById(R.id.ldtitle);
        ldcost = (TextView) findViewById(R.id.ldcost);
        ldcontent = (TextView) findViewById(R.id.ldcontent);

        Ion.with(this)
                .load("http://recyclist.us/api/listing/full?mobile_token=we4mluxnRqNfCTZe2ccWEmWpa28OQCwv&id=" + id)
                .setHeader("Authorization", "Bearer" + currUserToken)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("full listing", result.toString());
                        ldtitle.setText(result.get("title").getAsString(),TextView.BufferType.NORMAL);
                        ldcost.setText(result.get("cost").getAsString(),TextView.BufferType.NORMAL);
                        ldcontent.setText(result.get("content").getAsString(),TextView.BufferType.NORMAL);

                    }
                });


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent4 = new Intent(this, MainActivity.class);
                if (loggedIn) intent4.putExtra("currentUserToken", currUserToken);
                finish();
                this.startActivity(intent4);
                break;
            case R.id.action_search:
                Intent intent = new Intent(this, ResListingsActivity.class);
                if (loggedIn) intent.putExtra("currentUserToken", currUserToken);
                finish();
                this.startActivity(intent);
                break;
            case R.id.action_aboutus:
                Intent intent2 = new Intent(this, AboutActivity.class);
                if (loggedIn) intent2.putExtra("currentUserToken", currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_resources:
                Intent intent3 = new Intent(this, CommInfoActivity.class);
                if (loggedIn) intent3.putExtra("currentUserToken", currUserToken);
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