package evans.jon.hudlu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import evans.jon.hudlu.evans.jon.hudlu.models.MashableNews;
import evans.jon.hudlu.evans.jon.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements OnAdapterInteractionListener {

    private MashableNews mashableNews;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private final List<MashableNewsItem> myDataset = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        mAdapter = new RecyclerViewAdapter(this,myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("HudlU","Settings menu item clicked.");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Snackbar.make(view, myDataset.get(position).author, Snackbar.LENGTH_SHORT).show();
    }

    public void fetchLatestNews() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            mashableNews = new MashableNews();
            RequestQueue queue = Volley.newRequestQueue(this);
            Toast.makeText(this,"Just running to the shops to get the news...",Toast.LENGTH_SHORT).show();
            StringRequest request = new StringRequest(Request.Method.GET, "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mashableNews = new Gson().fromJson(response, MashableNews.class);
                            myDataset.addAll(mashableNews.newsItems);
                            mAdapter.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error","Error Occured fetching news");
                        }
                    });
            queue.add(request);
        }else {
            Toast.makeText(this,"No Internet - Bummer Bro",Toast.LENGTH_LONG).show();
        }
    }
}
