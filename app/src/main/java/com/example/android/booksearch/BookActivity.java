package com.example.android.booksearch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    public static final String LOG_TAG = BookActivity.class.getName();
    private BookListAdapter adapter;
    private TextView emptyTextView;
    private TextView noConnectionTextView;
    private ProgressBar progressBar;

    /** URL for earthquake data from the USGS dataset */
    private static final String GOOGLE_BOOKS_API_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=finance&maxResults=10";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ListView listView = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_list);
        listView.setEmptyView(emptyTextView);
        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if there is a network connection, fetch data
        if(networkInfo != null && networkInfo.isConnected()){
            //get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_connection_message);
        }

        adapter = new BookListAdapter(this, new ArrayList<Book>());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                // Find the current earthquake that was clicked on
                Book currentBook = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentBook.getInfoLink());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        //Log.e("oncreate","Run onCreateLoader");
        return new BookLoader(this, GOOGLE_BOOKS_API_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        adapter.clear();
        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
            Log.e("onloadfinished","Run onLoadFinished");
        }
        emptyTextView.setText(R.string.empty_list);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
        //Log.e("onreset","Run onResetLoader");
    }
}
