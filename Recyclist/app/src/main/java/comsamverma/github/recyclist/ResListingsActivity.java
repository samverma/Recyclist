package comsamverma.github.recyclist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sam on 3/22/2016.
 */
public class ResListingsActivity extends AppCompatActivity{
    boolean loggedIn;
    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    private EditText searchfor;
    private Spinner spinner,cpinner;
    ArrayList<Listing> reslistings = new ArrayList<Listing>();
    ListView listview;
    ListingAdapter la;
    //String currUserEmail;
    private AutoCompleteTextView act;
    String currUserToken;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        loggedIn=false;
        //Toast.makeText(this, "Res listings",Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        //currUserEmail = intent.getStringExtra("currentUserEmail");
        currUserToken = intent.getStringExtra("currentUserToken");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        if(currUserToken != null)
        {
            setContentView(R.layout.activity_reslistingsloggedin);
            loggedIn=true;
        }
        else
        {
            setContentView(R.layout.activity_reslistings);
            TextView pllo = (TextView)findViewById(R.id.pllo);
            pllo.setTypeface(tf1);
            TextView loginres = (TextView)findViewById(R.id.loginres);
            loginres.setTypeface(tf1);
        }
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

        TextView publist = (TextView)findViewById(R.id.publist);
        publist.setTypeface(tf1);

        TextView wanttonarrow = (TextView)findViewById(R.id.wanttonarrow);
        wanttonarrow.setTypeface(tf1);
        TextView location = (TextView)findViewById(R.id.location);
        location.setTypeface(tf1);
        TextView cost = (TextView)findViewById(R.id.cost);
        cost.setTypeface(tf1);
        TextView tag = (TextView)findViewById(R.id.tag);
        tag.setTypeface(tf1);
        listview = (ListView)findViewById(R.id.pubreslistingsView);
        la = new ListingAdapter(getApplicationContext(),reslistings);
        searchfor = (EditText)findViewById(R.id.searchfor);

        ArrayAdapter<CharSequence> spadapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.statesfull_spinner);
        spinner.setAdapter(spadapter);
        //spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> cadapter = ArrayAdapter.createFromResource(this,R.array.costs,android.R.layout.simple_spinner_item);
        cadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cpinner = (Spinner)findViewById(R.id.costs_spinner);
        cpinner.setAdapter(cadapter);

        act = (AutoCompleteTextView)findViewById(R.id.autocompletetag);
        String[] tags = getResources().getStringArray(R.array.tags);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tags );
        act.setAdapter(adapter);


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
                    Intent intent3 = new Intent(this, MainActivity.class);
                    intent3.putExtra("currentUserToken",currUserToken);
                    finish();
                    this.startActivity(intent3);
                }
                else {
                    Intent intent3 = new Intent(this, MainActivity.class);
                    finish();
                    this.startActivity(intent3);
                }
            case R.id.action_aboutus:
                Intent intent = new Intent(this, AboutActivity.class);
                if(loggedIn)intent.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent);
                break;
            case R.id.action_resources:
                Intent intent2 = new Intent(this, CommInfoActivity.class);
                if(loggedIn)intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
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
    public void clickLoginRes(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    public void clickSearch(View v){
        String searchterm="";
        if(!searchfor.getText().toString().equals(null))
        {

                searchterm = searchfor.getText().toString();
        }
        String state="";
        if(!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals(null)&&!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals("--Select a State--"))
        {

                state = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        }
        String cost="";
        if(!cpinner.getItemAtPosition(cpinner.getSelectedItemPosition()).toString().equals(null))
        {

                cost = cpinner.getItemAtPosition(cpinner.getSelectedItemPosition()).toString();
        }
        String tag="";
        if(!act.getText().toString().equals(null))
        {

                tag= act.getText().toString();
        }
        JsonObject params = new JsonObject();
        params.addProperty("mobile_token",mobile_token);
        params.addProperty("query",searchterm);
       // Log.d("St",searchterm);
        params.addProperty("cost",cost);
       // Log.d("cost",cost);
        params.addProperty("state",state);
       // Log.d("state",state);
        params.addProperty("tag",tag);
        Log.d("tag",tag);
        Ion.with(this)
                .load("http://recyclist.us/api/listing/search")
                .setJsonObjectBody(params)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(!result.toString().equals(null)) {
                            Log.d("Search post attempt", result.toString());
                            if (result.get("success").getAsString().equals("true"))
                                getResults(result.get("id").getAsString());
                        }else{
                            Log.d("Search attempt","failed");
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            Toast.makeText(ResListingsActivity.this, "Please enter valid terms", Toast.LENGTH_LONG).show();
                           // finish();
                           // startActivity(getIntent());
                        }
                    }
                });
    }
    public void getResults(String id)
    {
        Ion.with(this)
                .load("http://recyclist.us/api/listing/search?mobile_token=we4mluxnRqNfCTZe2ccWEmWpa28OQCwv"+"&count=15&search_id="+id+"&page=1")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Log.d("search results","success");
                        JsonArray results = result;
                        int size = results.size();
                        Log.d("size:", Integer.toString(size));
                        reslistings.clear();
                        if(size>0){
                            for(int i=0;i<size;i++)
                            {
                                JsonObject current = results.get(i).getAsJsonObject();
                                Listing rl = new Listing();
                                rl.id = current.get("id").getAsInt();
                                rl.user_id = current.get("user_id").getAsInt();
                                rl.title = current.get("title").getAsString();
                                rl.content = current.get("content_short").getAsString();
                                rl.cost = current.get("cost").getAsString();
                                rl.state = current.get("state").getAsString();
                                reslistings.add(rl);
                            }
                        }
                        else{
                            Listing rl = new Listing();
                            rl.title = "";
                            rl.content = "No results were found, please try a different search.";
                            rl.id=0;
                            rl.user_id=0;
                            rl.cost = "";
                            rl.state = "";
                            reslistings.add(rl);
                        }
                        listview.setAdapter(la);
                        la.notifyDataSetChanged();
                    }
                });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(loggedIn) {
                    Listing listing = (Listing)parent.getAdapter().getItem(position);
                    Intent intent = new Intent(ResListingsActivity.this,ListingDetailActivity.class);
                    intent.putExtra("id",listing.id);
                    intent.putExtra("currentUserToken", currUserToken);
                    ResListingsActivity.this.startActivity(intent);
                }
            }
        });
    }
    public void clickFullSearch(View view){
        String searchterm="";
        if(!searchfor.getText().toString().equals(null))
        {

            searchterm = searchfor.getText().toString();
        }
        String state="";
        if(!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals(null)&&!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals("--Select a State--"))
        {

            state = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        }
        String cost="";
        if(!cpinner.getItemAtPosition(cpinner.getSelectedItemPosition()).toString().equals(null))
        {

            cost = cpinner.getItemAtPosition(cpinner.getSelectedItemPosition()).toString();
        }
        String tag="";
        if(!act.getText().toString().equals(null))
        {

            tag= act.getText().toString();
        }
        JsonObject params = new JsonObject();
        params.addProperty("mobile_token",mobile_token);
        params.addProperty("query",searchterm);
        // Log.d("St",searchterm);
        params.addProperty("cost",cost);
        // Log.d("cost",cost);
        params.addProperty("state",state);
        // Log.d("state",state);
        params.addProperty("tag",tag);
        Log.d("tag",tag);
        Ion.with(this)
                .load("http://recyclist.us/api/listing/search")
                .setHeader("Authorization", "Bearer" + currUserToken)
                .setJsonObjectBody(params)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(!result.toString().equals(null)) {
                            Log.d("Search post attempt", result.toString());
                            if (result.get("success").getAsString().equals("true"))
                                getResults(result.get("id").getAsString());
                        }else{
                            Log.d("Search attempt","failed");
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            Toast.makeText(ResListingsActivity.this, "Please enter valid terms", Toast.LENGTH_LONG).show();

                            //finish();
                            //startActivity(getIntent());
                        }
                    }
                });
    }

}
