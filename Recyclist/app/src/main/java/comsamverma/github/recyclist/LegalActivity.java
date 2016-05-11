package comsamverma.github.recyclist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LegalActivity extends AppCompatActivity {
    boolean loggedIn;
    String currUserToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        loggedIn = false;
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        if(currUserToken!=null)loggedIn=true;
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        //TextView tv1 = (TextView) findViewById(R.id.LegalText);
        //TextView tv2 = (TextView) findViewById(R.id.Home);
        //tv1.setTypeface(tf1);
       // tv2.setTypeface(tf1);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            case R.id.action_aboutus:
                Intent intent2 = new Intent(this, AboutActivity.class);
                if(loggedIn) intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_search:
                Intent intent = new Intent(this,ResListingsActivity.class);
                if(loggedIn) intent.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent);
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}