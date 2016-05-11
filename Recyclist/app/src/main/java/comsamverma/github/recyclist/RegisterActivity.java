package comsamverma.github.recyclist;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {

    private String mobile_token = "we4mluxnRqNfCTZe2ccWEmWpa28OQCwv";
    private EditText emailin, pass, conf, firstn, lastn, compn, compi, compp, compph, compadd, compc, comps, compz, compln;
    private CheckBox notif;
    //private Button mRegister,mSignIn;

    // Progress Dialog
   // private ProgressDialog pDialog;



    // php login script location:

    // localhost :
    // testing on your device
    // put your local ip instead, on windows, run CMD > ipconfig
    // or in mac's terminal type ifconfig and look for the ip under en0 or en1
    // private static final String LOGIN_URL =
    // "http://xxx.xxx.x.x:1234/webservice/login.php";

    // testing on Emulator:
    private static final String REGISTER_URL = "http://recyclist.us/api/create";

    // testing from a real server:
    // private static final String LOGIN_URL =
    // "http://www.mybringback.com/webservice/login.php";

    // JSON element ids from repsonse of php script:
    //private static final String TAG_SUCCESS = "success";
    //private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // setup input fields
        emailin = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        conf = (EditText) findViewById(R.id.confirm);
        firstn = (EditText) findViewById(R.id.firstname);
        lastn = (EditText) findViewById(R.id.lastname);
        compn = (EditText) findViewById(R.id.compname);
        compi = (EditText) findViewById(R.id.compinfo);
        compp = (EditText) findViewById(R.id.comppurp);
        compph = (EditText) findViewById(R.id.compphone);
        compadd = (EditText) findViewById(R.id.compaddress);
        compc = (EditText) findViewById(R.id.compcity);
        comps = (EditText) findViewById(R.id.compstate);
        compz = (EditText) findViewById(R.id.compzip);
        compln = (EditText) findViewById(R.id.complicnum);
        notif = (CheckBox) findViewById(R.id.emailnotify);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-SemiBold.otf");
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
                Intent intent7 = new Intent(this,ResListingsActivity.class);
                finish();
                this.startActivity(intent7);
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
    public void Register(View v) {
        // TODO Auto-generated method stub

        //String[] fields = new String[9];
        final String email = emailin.getText().toString();
        //fields[0]=email;
        final String passw = pass.getText().toString();
        String confirm = conf.getText().toString();
        //fields[1]=passw;
        String firstname = firstn.getText().toString();
        //fields[2]=firstname;
        String lastname = lastn.getText().toString();
        //fields[3]=lastname;
        String compname = compn.getText().toString();
        //fields[4]=compname;
        String compinfo = compi.getText().toString();
        //fields[5]=compinfo;
        String comppurp = compp.getText().toString();
        String compphone = compph.getText().toString();
        String compaddr = compadd.getText().toString();
        String compcity = compc.getText().toString();
        String compstate = comps.getText().toString();
        String compzip = compz.getText().toString();
        //fields[6]=comppurp;
        String complicnum = compln.getText().toString();
        //fields[7]=complicnum;
        String emailnotif ="";
        if(notif.isChecked()) emailnotif="True"; else emailnotif="False";
        //fields[8]=emailnotif;
        //new AttemptLogin().execute(email,passw,firstname,lastname,compname,compinfo,comppurp,complicnum,emailnotif);
       // break;
            //case R.id.go_sign_in:

            //    break;
        JsonObject params = new JsonObject();
        params.addProperty("mobile_token",mobile_token);
        params.addProperty("function","createAccount");
        params.addProperty("email",email);
        params.addProperty("password",passw);
        params.addProperty("confirmation",confirm);
        params.addProperty("first_name",firstname);
        params.addProperty("last_name",lastname);
        params.addProperty("company_name",compname);
        params.addProperty("company_information",compinfo);
        params.addProperty("company_purpose",comppurp);
        params.addProperty("company_phone_number",compphone);
        params.addProperty("company_address",compaddr);
        params.addProperty("company_city",compcity);
        params.addProperty("company_state",compstate);
        params.addProperty("company_zip_code",compzip);
        params.addProperty("company_license_num",complicnum);
        params.addProperty("notify_by_email",emailnotif);
        Ion.with(this)
                .load("http://recyclist.us/api/user/create")
                        .setJsonObjectBody(params)
                                .asJsonObject()
               /* .setBodyParameter("mobile_token",mobile_token)
                .setBodyParameter("function","createAccount")
                .setBodyParameter("email",email)
                .setBodyParameter("password",passw)
                .setBodyParameter("firstname",firstname)
                .setBodyParameter("lastname",lastname)
                .setBodyParameter("company_name",compname)
                .setBodyParameter("company_information",compinfo)
                .setBodyParameter("company_purpose",comppurp)
                .setBodyParameter("company_license_num",complicnum)
                .setBodyParameter("notify_by_email",emailnotif)*/
                //.asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        //if(jsonObject.isJsonNull())     Toast.makeText(RegisterActivity.this,"json object is null",Toast.LENGTH_LONG).show();
                        Log.d("Reg attempt", jsonObject.toString());
                        // Log.d("Reg attempt","test print");
                        if(jsonObject.has("error")){
                            Log.d("Login failure",jsonObject.get("error").toString());
                            //TextView tv = (TextView)findViewById(R.id.Test);
                            //tv.setText(jsonObject.toString());
                            //Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            Toast.makeText(RegisterActivity.this, jsonObject.get("error").toString(), Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());

                        } else {
                            Log.d("Account created", jsonObject.toString());
                            //String currentUserEmail = jsonObject.get("email").getAsString();
                            //String currentUserToken = jsonObject.get("token").getAsString();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            //i.putExtra("currentUser",currUser);
                            i.putExtra("newAccountEmail", email);
                            i.putExtra("newAccountPass", passw);
                            finish();
                            startActivity(i);
                        }


                    }
                });

    }
    public void clickLogIn(View v){
        Intent i = new Intent(this, LoginActivity.class);
        finish();
        startActivity(i);
    }

}

