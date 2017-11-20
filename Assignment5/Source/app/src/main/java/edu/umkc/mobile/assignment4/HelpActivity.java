package edu.umkc.mobile.assignment4;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String sharedText;
    private String[] mThumbIds;
    private Context mContext;

    String API_URL = "http://content.guardianapis.com/search?q=debates&api-key=test&show-fields=thumbnail";

    public HelpActivity(){
        super();

    }
    public HelpActivity(Context context){

        mContext = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mContext = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

    }

    void handleSendText(Intent intent) {
        /*sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header=navigationView.getHeaderView(0);
*//*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*//*
            TextView username = (TextView)header.findViewById(R.id.username);
            TextView email = (TextView)header.findViewById(R.id.email);

            //TextView welcometext = (TextView)findViewById(R.id.welcometext);

           *//* if(sharedText!=null) {
                username.setText(sharedText.split(",")[0]);
                email.setText(sharedText.split(",")[1]);
                welcometext.setText("HELP Activity Page");
            }*//*
        }*/

        GuardianAsyncTaskRunner runnerTask = new GuardianAsyncTaskRunner();

        runnerTask.execute("getURL",API_URL);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent sendIntent = new Intent(this,IndexActivity.class);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            sendIntent.setType("text/plain");


            if(sendIntent.resolveActivity(getPackageManager())!=null){
                startActivity(sendIntent);
            }
        } else if (id == R.id.nav_gallery) {
            Intent sendIntent = new Intent(this,HelpActivity.class);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            sendIntent.setType("text/plain");


            if(sendIntent.resolveActivity(getPackageManager())!=null){
                startActivity(sendIntent);
            }

        }  else if (id == R.id.nav_manage) {
            Intent sendIntent = new Intent(this,SettingsActivity.class);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sharedText);
            sendIntent.setType("text/plain");


            if(sendIntent.resolveActivity(getPackageManager())!=null){
                startActivity(sendIntent);
            }

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GuardianAsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressBar spinner;

        @Override
        protected String doInBackground(String... strings) {

            InputStream inputStream = null;
            String result = "";
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(strings[1]));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    try {
                        if(result!=null && !"".equalsIgnoreCase(result)){

                            JSONObject jsonResponse = new JSONObject(result);
                            JSONObject jsonResults = jsonResponse.getJSONObject("response");
                            JSONArray resultsArray = jsonResults.getJSONArray("results");
                            List<String> imgList = new ArrayList<String>();
                            for (int i = 0; i <=7; i++) {

                                JSONObject item = resultsArray.getJSONObject(i);

                                String img = "";

                                if(item.has("fields")) {
                                    JSONObject fieldsInfo = item.getJSONObject("fields");
                                    if(fieldsInfo.has("thumbnail"))
                                        img = fieldsInfo.getString("thumbnail");
                                }

                                imgList.add(img);
                            }
                            if(imgList.size()>0)
                                mThumbIds = new String[]{
                                        imgList.get(0), imgList.get(1),
                                        imgList.get(2), imgList.get(3),
                                        imgList.get(4), imgList.get(5),
                                        imgList.get(6), imgList.get(7)
                                };

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    GridView gridview = (GridView) findViewById(R.id.gridview);
                                    gridview.setAdapter(new GridAdapter(mContext, mThumbIds));

                                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> parent, View v,
                                                                int position, long id) {
                                            Toast.makeText(HelpActivity.this, "" + position,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }
                            });

                        }

                    }catch (Exception e){
                        Log.d("Async",e.getMessage());
                    }
                }
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return result;
        }

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}
