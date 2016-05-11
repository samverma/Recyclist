package comsamverma.github.recyclist;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity  {

    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    private EditText emailin, pass;
    private Button mSignIn,mRegister;
    String currUserEmail;
    String currUserToken;
    String newAccountEmail;
    String newAccountPass;

    // testing on Emulator:
    private static final String LOGIN_URL = "http://recyclist.us/api/authenticate";

    // testing from a real server:
    // private static final String LOGIN_URL =
    // "http://www.mybringback.com/webservice/login.php";

    // JSON element ids from repsonse of php script:
   // private static final String TAG_SUCCESS = "success";
    //private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //currUserEmail = intent.getStringExtra("currentUserEmail");
        currUserToken = intent.getStringExtra("currentUserToken");
        newAccountEmail = intent.getStringExtra("newAccountEmail");
        newAccountPass = intent.getStringExtra("newAccountPass");
        Log.d("New user info", newAccountEmail + " " + newAccountPass);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
        //if(currUserEmail!=null && currUserToken!=null) {

        //}
        // else {
        setContentView(R.layout.activity_login);
        // setup input fields
        emailin = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
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
        if (newAccountEmail != null && newAccountPass != null) {
            Ion.with(this)
                    .load("http://recyclist.us/api/authenticate")
                    .setBodyParameter("mobile_token", mobile_token)
                    .setBodyParameter("function", "getUser")
                    .setBodyParameter("email", newAccountEmail)
                    .setBodyParameter("password", newAccountPass)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject jsonObject) {
                            if (jsonObject.has("error")) {
                                Log.d("Login failure", jsonObject.get("error").getAsString());
                                //TextView tv = (TextView)findViewById(R.id.Test);
                                //tv.setText(jsonObject.toString());
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                Toast.makeText(LoginActivity.this, jsonObject.get("error").getAsString(), Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(intent);
                            } else {
                                Log.d("Login success", jsonObject.toString());
                                //String currentUserEmail = jsonObject.get("email").toString();
                                String currentUserToken = jsonObject.get("token").toString();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                //i.putExtra("currentUser",currUser);
                                //i.putExtra("currentUserEmail", currentUserEmail);
                                i.putExtra("currentUserToken", currentUserToken);
                                finish();
                                startActivity(i);
                            }
                        }
                    });
        }
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
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_search:
                Intent intent3 = new Intent(this,ResListingsActivity.class);
                finish();
                this.startActivity(intent3);
                break;
            case R.id.action_aboutus:
                Intent intent = new Intent(this, AboutActivity.class);
                finish();
                this.startActivity(intent);
                break;
            case R.id.action_resources:
                Intent intent2 = new Intent(this, CommInfoActivity.class);
                finish();
                this.startActivity(intent2);
                break;
            case R.id.action_news:
                Intent intent5 = new Intent(this, NewsFeedActivity.class);
                finish();
                this.startActivity(intent5);
                break;
            case R.id.action_tou:
                Intent intent6 = new Intent(this, LegalActivity.class);
                finish();
                this.startActivity(intent6);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void SignIn(View v) {
        // TODO Auto-generated method stub

        String email = emailin.getText().toString();
        String passw = pass.getText().toString();
        Ion.with(this)
                .load("http://recyclist.us/api/authenticate")
                .setBodyParameter("mobile_token", mobile_token)
                .setBodyParameter("function", "getUser")
                .setBodyParameter("email", email)
                .setBodyParameter("password", passw)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        if (jsonObject.has("error")) {
                            Log.d("Login failure", jsonObject.get("error").getAsString());
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            Toast.makeText(LoginActivity.this, jsonObject.get("error").getAsString(), Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(intent);

                        } else {
                            Log.d("Login success", jsonObject.toString());
                            //String currentUserEmail = jsonObject.get("email").toString();
                            String currentUserToken = jsonObject.get("token").toString();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            //i.putExtra("currentUser",currUser);
                            //i.putExtra("currentUserEmail", currentUserEmail);
                            i.putExtra("currentUserToken", currentUserToken);
                            finish();
                            startActivity(i);
                        }


                    }
                });
    }



    public void Register(View v){
        Intent i = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(i);
    }
}

