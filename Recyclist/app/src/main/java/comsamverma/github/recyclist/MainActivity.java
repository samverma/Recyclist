package comsamverma.github.recyclist;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {//android.support.v4.app.FragmentActivity{

    boolean loggedIn;
    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    String currUserEmail;
    String currUserToken;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        loggedIn=false;
        Intent intent = getIntent();
        //currUserEmail = intent.getStringExtra("currentUserEmail");
        currUserToken = intent.getStringExtra("currentUserToken");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        if(currUserToken!=null) //&& currUserEmail!=null
        {
            setContentView(R.layout.activity_main_loggedin);
            loggedIn = true;
           // User currUser = new User(currUserEmail,currUserToken);
           // TextView tv3 = (TextView) findViewById(R.id.LogOut);
           // TextView tv7 = (TextView) findViewById(R.id.ResList);
           // TextView tv8 = (TextView) findViewById(R.id.YourListings);
          //  tv3.setTypeface(tf1);
          //  tv7.setTypeface(tf1);
          //  tv8.setTypeface(tf1);
            Button as = (Button)findViewById(R.id.AccountSettings);
            as.setTypeface(tf1);
            Button lo = (Button) findViewById(R.id.LogOut);
            lo.setTypeface(tf1);

        }
        else{
            setContentView(R.layout.activity_main);
            TextView tv4 = (TextView) findViewById(R.id.LogIn);
            //tv4.setTypeface(tf1);
            Button li = (Button)findViewById(R.id.LogIn);
            li.setTypeface(tf1);
        }

        TextView tv1 = (TextView) findViewById(R.id.BECOMEA);
        tv1.setTypeface(tf1);
        TextView tv2 = (TextView) findViewById(R.id.MEMBER);
        tv2.setTypeface(tf1);
        TextView tv3 = (TextView) findViewById(R.id.becomememtext);
        tv3.setTypeface(tf2);


       /* if(loggedIn == true){
            setContentView(R.layout.activity_main_loggedin);
        }
        else{
            setContentView(R.layout.activity_main);
        }*/
       // TextView tv1 = (TextView) findViewById(R.id.About);
      //  TextView tv2 = (TextView) findViewById(R.id.Legal);
       // TextView tv5 = (TextView) findViewById(R.id.ServProv);
       // TextView tv6 = (TextView) findViewById(R.id.CommInfo);


      //  tv1.setTypeface(tf1);
      //  tv2.setTypeface(tf1);
      //  tv5.setTypeface(tf1);
      //  tv6.setTypeface(tf1);
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

    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
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
            case R.id.action_search:
                Intent intent = new Intent(this,ResListingsActivity.class);
                if(loggedIn) intent.putExtra("currentUserToken",currUserToken);
                this.startActivity(intent);
                break;
            case R.id.action_aboutus:
                Intent intent2 = new Intent(this, AboutActivity.class);
                if(loggedIn) intent2.putExtra("currentUserToken",currUserToken);
                this.startActivity(intent2);
                break;
            case R.id.action_resources:
                Intent intent3 = new Intent(this, CommInfoActivity.class);
                if(loggedIn)intent3.putExtra("currentUserToken",currUserToken);
                this.startActivity(intent3);
                break;
            case R.id.action_news:
                Intent intent5 = new Intent(this, NewsFeedActivity.class);
                if(loggedIn)intent5.putExtra("currentUserToken",currUserToken);
                startActivity(intent5);
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



    public void clickAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
       // finish();
        startActivity(intent);

    }
    public void clickHome(View view) {
        finish();
        startActivity(getIntent());
    }


    public void clickResListings(View view) {
        Intent intent = new Intent(this, ResListingsActivity.class);
        //intent.putExtra("currentUserEmail",currUserEmail);
        if(loggedIn) intent.putExtra("currentUserToken",currUserToken);
       // finish();
        startActivity(intent);
    }


    public void clickYourListings(View view) {
        if(loggedIn){
        Intent intent = new Intent(this, YourListingsActivity.class);
        //intent.putExtra("currentUserEmail",currUserEmail);
        intent.putExtra("currentUserToken",currUserToken);
       // finish();
        startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void clickAccountSettings(View v){
        Intent intent = new Intent(this, EditAccountActivity.class);
        intent.putExtra("currentUserToken",currUserToken);
        startActivity(intent);
    }


    public void clickCommInfo(View view) {
        Intent intent = new Intent(this, CommInfoActivity.class);
       // finish();
        startActivity(intent);
    }

    public void clickLogIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
       // finish();
        startActivity(intent);
    }
    public void clickSignUp(View view){
        if(loggedIn){
            Toast.makeText(MainActivity.this, "You are currently logged in.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
    public void clickLogOut(View v){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }


}
