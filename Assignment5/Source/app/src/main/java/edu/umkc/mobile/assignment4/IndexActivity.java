package edu.umkc.mobile.assignment4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    String sharedText;

    RecyclerView listView;
    private ListViewAdapter bookAdapter;
    EditText search;

    String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    ArrayList<Book> bookList = new ArrayList<Book>();

    public IndexActivity(){
        super();
    }

    public IndexActivity(Context context){
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
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
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null && !"".equalsIgnoreCase(sharedText)) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View header = navigationView.getHeaderView(0);

        }
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
            startActivity(sendIntent);


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

    private class BookSearchAsyncTaskRunner extends AsyncTask<String, String, String> {

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

                            JSONObject object = new JSONObject(result);
                            JSONArray array = object.getJSONArray("items");
                            for (int i = 0; i <=5; i++) {

                                JSONObject item = array.getJSONObject(i);

                                String title = "";
                                String subtitle = "";
                                String author = "";
                                String imageLink = "";
                                String publishedDate="";

                                if(item.has("volumeInfo")) {
                                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                                    if(volumeInfo.has("title"))
                                        title = volumeInfo.getString("title");
                                    if(volumeInfo.has("subtitle"))
                                        subtitle = volumeInfo.getString("subtitle");
                                    if(volumeInfo.has("authors")) {
                                        JSONArray authors = volumeInfo.getJSONArray("authors");
                                        author = authors.getString(0);
                                    }
                                    if(volumeInfo.has("imageLinks")) {
                                        JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                        if(imageLinks.has("smallThumbnail"))
                                            imageLink = imageLinks.getString("smallThumbnail");
                                    }
                                }

                                if(item.has("publishedDate"))
                                    publishedDate = item.getString("publishedDate");

                                Book book = new Book(title,subtitle,author,publishedDate,imageLink);

                                bookList.add(book);
                            }

                            /*spinner = (ProgressBar)findViewById(R.id.progressBar1);
                            spinner.setVisibility(View.VISIBLE);*/

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    listView = (RecyclerView) findViewById(R.id.recyclerview);
                                    bookAdapter = new ListViewAdapter(bookList, getApplication());
                                    listView.setAdapter(bookAdapter);
                                    listView.setLayoutManager(new LinearLayoutManager(mContext));

                                }
                            });



                           /* if(spinner!=null){

                                spinner.setVisibility(View.GONE);
                            }*/

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public void searchBooks(View view){

        search = (EditText) findViewById(R.id.search);
        //String query = editable.toString().replace(" ", "+");
        String query = "harry+potter";

        String getBookURL = API_URL+query;

        bookList = new ArrayList<Book>();


        BookSearchAsyncTaskRunner runnerTask = new BookSearchAsyncTaskRunner();

        runnerTask.execute("getURL",getBookURL);
    }
}
