package comsamverma.github.recyclist;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by sam on 3/22/2016.
 */
public class YourListingsActivity extends AppCompatActivity {

    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    String currUserToken;
    ArrayList<Listing> userlistings = new ArrayList<Listing>();
    ListView listView;
    ListingAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourlistings);
        listView = (ListView) findViewById(R.id.yourlistingsView);
        adapter = new ListingAdapter(getApplicationContext(), userlistings);
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Ion.with(this)
                .load("http://recyclist.us/api/listing/me?mobile_token=we4mluxnRqNfCTZe2ccWEmWpa28OQCwv")
                .setHeader("Authorization", "Bearer" + currUserToken)
                        // .setBodyParameter("mobile_token", mobile_token)
                        //.asString()
                        //.setJsonObjectBody(params)
               // .asJsonObject()
                        //
                        // .setBodyParameter("mobile_token",mobile_token)
                        //   .asString()
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray jsonElements) {
                        Log.d("Get user listings", jsonElements.toString());
                        JsonArray listings = jsonElements;
                        int size = listings.size();
                        userlistings.clear();
                        if(size>0) {
                            for (int i = 0; i < size; i++) {
                                JsonObject current = listings.get(i).getAsJsonObject();
                                Listing ul = new Listing();
                                ul.id = current.get("id").getAsInt();
                                ul.user_id = current.get("user_id").getAsInt();
                                ul.title = current.get("title").getAsString();
                                ul.content = current.get("content_short").getAsString();
                                ul.cost = current.get("cost").getAsString();
                                //if(current.get("state").getAsString() == null) ul.state = "";
                                ul.state = current.get("state").getAsString();
                                userlistings.add(ul);

                            }
                        }
                        else{
                            Listing ul = new Listing();
                            ul.id=0;
                            ul.user_id=0;
                            ul.title = "";
                            ul.content = "No user listings found.";
                            ul.cost = "";
                            ul.state = "";
                            userlistings.add(ul);
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }


                  /*  @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        try{
                        Log.d("Get user listings", jsonObject.getAsJsonArray().toString());
                        //JsonArray listings = jsonObject.;
                        }catch(NullPointerException ex)
                        {
                           Log.d("No jsonobject","found");
                        }
                    }*/
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Listing listing = (Listing)parent.getAdapter().getItem(position);
                Intent intent = new Intent(YourListingsActivity.this,EditListingActivity.class);
                intent.putExtra("id",listing.id);
                intent.putExtra("currentUserToken",currUserToken);
                finish();
                YourListingsActivity.this.startActivity(intent);
            }
        });
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
                intent4.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent4);
                break;
            case R.id.action_search:
                Intent intent = new Intent(this,ResListingsActivity.class);
                intent.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent);
                break;
            case R.id.action_aboutus:
                Intent intent2 = new Intent(this, AboutActivity.class);
                intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_resources:
                Intent intent3 = new Intent(this, CommInfoActivity.class);
                intent3.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent3);
                break;
            case R.id.action_news:
                Intent intent5 = new Intent(this, NewsFeedActivity.class);
                intent5.putExtra("currentUserToken",currUserToken);
                finish();
                startActivity(intent5);
                break;
            case R.id.action_tou:
                Intent intent6 = new Intent(this, LegalActivity.class);
                intent6.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent6);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    //pull user listings and fill listview with them
    //listings have title and content fields
    public void clickCreate(View v){
        Intent intent = new Intent(this, CreateListingActivity.class);
        intent.putExtra("currentUserToken",currUserToken);
        startActivity(intent);
    }


}
