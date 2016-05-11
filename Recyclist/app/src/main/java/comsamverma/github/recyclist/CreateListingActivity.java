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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by sam on 4/4/2016.
 */
public class CreateListingActivity extends AppCompatActivity {
    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    String currUserToken;
    private Spinner spinner;
    private EditText listingtitle, listingcontent, listingcost;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlisting);
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        Log.d("currentUserToken", currUserToken);

        listingtitle = (EditText) findViewById(R.id.listingtitle);
        listingcontent = (EditText) findViewById(R.id.listingcontent);
        listingcost = (EditText) findViewById(R.id.listingcost);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<CharSequence> spadapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.clstatesfull_spinner);
        spinner.setAdapter(spadapter);

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
                this.startActivity(intent5);
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

    public void CreateListing(View v){

        String ltitle = listingtitle.getText().toString();
        String lcontent = listingcontent.getText().toString();
        String lcost = listingcost.getText().toString();
        String state="";
        if(!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals(null)&&!spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().equals("--Select a State--"))
        {

            state = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        }

        JsonObject params = new JsonObject();
        //  params.addProperty("Authorization","Bearer "+currentUserToken);
        params.addProperty("mobile_token", mobile_token);
        params.addProperty("title",ltitle);
        params.addProperty("content", lcontent);
        params.addProperty("cost",lcost);
        params.addProperty("state",state);

        Ion.with(this)
                // .load("http://recyclist.us/api/listing/create")
                //any get request send parameters with ?
                .load("http://recyclist.us/api/listing/create")
                .setHeader("Authorization", "Bearer" + currUserToken)
                        // .setBodyParameter("mobile_token", mobile_token)
                        //.asString()
                .setJsonObjectBody(params)
                .asJsonObject()
                        //
                        // .setBodyParameter("mobile_token",mobile_token)
                        //    .asString()
                        // .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        //if(jsonObject.isJsonNull())     Toast.makeText(RegisterActivity.this,"json object is null",Toast.LENGTH_LONG).show();
                        Log.d("Create attempt", jsonObject.toString());
                        // Log.d("Reg attempt","test print");
                        if(jsonObject.has("error")) {
                            Log.d("Create failure", jsonObject.get("error").toString());
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            Toast.makeText(CreateListingActivity.this, jsonObject.get("error").toString(), Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        }else{
                           // Log.d("Account created", jsonObject.toString());
                            //String currentUserEmail = jsonObject.get("email").getAsString();
                            //String currentUserToken = jsonObject.get("token").getAsString();
                            Intent i = new Intent(CreateListingActivity.this, YourListingsActivity.class);
                            //i.putExtra("currentUser",currUser);
                            //i.putExtra("newAccountEmail", email);
                            i.putExtra("currentUserToken", currUserToken);
                            finish();
                            startActivity(i);
                        }
                }

        });

    }
}
