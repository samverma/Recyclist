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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by sam on 4/18/2016.
 */
public class EditAccountActivity extends AppCompatActivity {
    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    String currUserToken;
    private EditText eafn, ealn, eacn, eapn, eaaddr, eacity, eastate, eazip, eapurp, ealicnum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currUserToken = intent.getStringExtra("currentUserToken");
        setContentView(R.layout.activity_editaccount);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
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

        eafn = (EditText)findViewById(R.id.eafn);
        ealn = (EditText)findViewById(R.id.ealn);
        eacn = (EditText)findViewById(R.id.eacn);
        eapn = (EditText)findViewById(R.id.eapn);
        eaaddr = (EditText)findViewById(R.id.eaaddr);
        eacity = (EditText)findViewById(R.id.eacity);
        eastate = (EditText)findViewById(R.id.eastate);
        eazip = (EditText)findViewById(R.id.eazip);
        eapurp = (EditText)findViewById(R.id.eapurp);
        ealicnum = (EditText)findViewById(R.id.ealicnum);

        Ion.with(this)
                .load("http://recyclist.us/api/user/me?mobile_token=we4mluxnRqNfCTZe2ccWEmWpa28OQCwv")
                .setHeader("Authorization", "Bearer" + currUserToken)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("Current user",result.toString());
                        eafn.setText(result.get("first_name").getAsString(), TextView.BufferType.EDITABLE);
                        ealn.setText(result.get("last_name").getAsString(), TextView.BufferType.EDITABLE);
                        eacn.setText(result.get("company_name").getAsString(), TextView.BufferType.EDITABLE);
                        eapn.setText(result.get("company_phone_number").getAsString(), TextView.BufferType.EDITABLE);
                        eaaddr.setText(result.get("company_address").getAsString(), TextView.BufferType.EDITABLE);
                        eacity.setText(result.get("company_city").getAsString(), TextView.BufferType.EDITABLE);
                        eastate.setText(result.get("company_state").getAsString(), TextView.BufferType.EDITABLE);
                        eazip.setText(result.get("company_zip_code").getAsString(), TextView.BufferType.EDITABLE);
                        eapurp.setText(result.get("company_purpose").getAsString(), TextView.BufferType.EDITABLE);
                        ealicnum.setText(result.get("company_license_number").getAsString(), TextView.BufferType.EDITABLE);
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
    public void clickSaveAccount(View view){
        JsonObject params = new JsonObject();
        params.addProperty("mobile_token", mobile_token);
        params.addProperty("first_name",eafn.getText().toString());
        params.addProperty("last_name",ealn.getText().toString());
        params.addProperty("company_name",eacn.getText().toString());
        params.addProperty("company_phone_number",eapn.getText().toString().replaceAll("\\D", ""));
        params.addProperty("company_address",eaaddr.getText().toString());
        params.addProperty("company_city",eacity.getText().toString());
        params.addProperty("company_state",eastate.getText().toString());
        params.addProperty("company_zip_code",eazip.getText().toString());
        params.addProperty("company_purpose",eapurp.getText().toString());
        params.addProperty("company_license_number",ealicnum.getText().toString());
        Ion.with(this)
                .load("http://recyclist.us/api/user/update")
                .setHeader("Authorization", "Bearer" + currUserToken)
                .setJsonObjectBody(params)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        Log.d("Update attempt", jsonObject.toString());
                        if(jsonObject.has("error")) {
                            Log.d("Update failure", jsonObject.get("error").toString());
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            Toast.makeText(EditAccountActivity.this, jsonObject.get("error").toString(), Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            intent.putExtra("currentUserToken", currUserToken);
                            finish();
                            startActivity(getIntent());
                        }
                        else{
                            Intent i = new Intent(EditAccountActivity.this, MainActivity.class);
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
