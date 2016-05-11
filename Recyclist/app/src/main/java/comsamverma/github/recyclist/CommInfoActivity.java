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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sam on 3/22/2016.
 */
public class CommInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    boolean loggedIn;
    String state = "";
    ArrayList<Landfill> statefills = new ArrayList<Landfill>();
    ListView listView;
    LandfillAdapter adapter;
    String currUserToken;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comminfo);
        loggedIn=false;
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        if(currUserToken!=null)loggedIn=true;

        listView=(ListView) findViewById(R.id.filllistView);
        adapter = new LandfillAdapter(getApplicationContext(), statefills);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.states_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setTitle("RECYCLIST");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");




        collapsingToolbar.setCollapsedTitleTypeface(tf1);
        collapsingToolbar.setExpandedTitleTypeface(tf1);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ///state = spinner.getSelectedItem().toString();
       //

    }
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
            case R.id.action_search:
                Intent intent2 = new Intent(this,ResListingsActivity.class);
                if(loggedIn)intent2.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_aboutus:
                Intent intent = new Intent(this, AboutActivity.class);
                if(loggedIn)intent.putExtra("currentUserToken",currUserToken);
                finish();
                this.startActivity(intent);
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
    public void Submit(View v){
        //state = spinner.getSelectedItem().toString();
        Log.d("Selected state", state);

        Ion.with(this)
                .load("http://www.opentrashlab.com/api/landfills")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        //Log.d("Response", jsonObject.toString());
                        JsonArray landfills = jsonObject.getAsJsonArray("landfills");
                        int size = landfills.size();

                        statefills.clear();
                        if(size>0) {
                            for (int i = 0; i < size; i++) {

                                JsonObject currentObj = landfills.get(i).getAsJsonObject();
                                if (currentObj.get("Facility_State").getAsString().equals(state)) {
                                    // Log.d("size",Integer.toString(size));
                                    Landfill fill = new Landfill();
                                    fill.facility_name = currentObj.get("Facility_Name").toString().replaceAll("^\"|\"$", "");
                                    //    Log.d("facname", fill.facility_name);
                                    fill.facility_street = currentObj.get("Facility_Street").toString().replaceAll("^\"|\"$", "");
                                    //   Log.d("facstreet", fill.facility_street);
                                    fill.facility_city = currentObj.get("Facility_City").toString().replaceAll("^\"|\"$", "");
                                    //    Log.d("faccity", fill.facility_city);
                                    fill.facility_state = currentObj.get("Facility_State").toString().replaceAll("^\"|\"$", "");
                                    //    Log.d("facstatee", fill.facility_state);
                                    fill.facility_zip_code = currentObj.get("Facility_ZIP_Code").toString();
                                    //    Log.d("faczip", fill.facility_zip_code);
                                    statefills.add(fill);
                                }

                            }
                        }else{
                            Landfill fill = new Landfill();
                            fill.facility_name = "No facility found.";
                            fill.facility_street ="N/A";
                            fill.facility_city = "N/A";
                            fill.facility_state = "N/A";
                            fill.facility_zip_code = "N/A";
                        }
                        //JsonArray landfills = jsonArray.

                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                });
        for(Landfill l:statefills)
        {
            Log.d("facname",l.facility_name);
        }
        Log.d("check", "got to this point");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
