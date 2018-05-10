package acedemy.learningprogramming.newsforu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listApps;
    private String feedUrl = "http://zeenews.india.com/rss/india-national-news.xml";
    private int feedLimit=10;
    private String feedCashedUrl = "INVALIDATED";
    public static final String STATE_URL = "feedUrl";
    public static final String STATE_LIMIT = "feedLimit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps =(ListView)findViewById(R.id.xmlListView);
        if(savedInstanceState!=null){
            feedUrl=savedInstanceState.getString(STATE_URL);
            feedLimit=savedInstanceState.getInt(STATE_LIMIT);
        }
        downloadUrl(String.format(feedUrl,feedLimit));
        Log.d(TAG, "onCreate: doneeeeeeeee");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        switch (id) {

            case R.id.economics:

                feedUrl = "https://economictimes.indiatimes.com/rssfeedstopstories.cms";
                break;
            case R.id.india:

                feedUrl ="http://zeenews.india.com/rss/india-national-news.xml";
                break;
            case R.id.world:

                feedUrl = "http://zeenews.india.com/rss/world-news.xml";
                break;
            case R.id.buisness:

                feedUrl = "http://zeenews.india.com/rss/business.xml";
                break;
            case R.id.sports:

                feedUrl = "http://zeenews.india.com/rss/sports-news.xml";
                break;
            case R.id.tech:

                feedUrl = "http://zeenews.india.com/rss/technology-news.xml";
                break;
            case R.id.entertainment:

                feedUrl = "http://zeenews.india.com/rss/entertainment-news.xml";
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        downloadUrl(String.format(feedUrl,feedLimit));

        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_URL,feedUrl);
        outState.putInt(STATE_LIMIT,feedLimit);
        super.onSaveInstanceState(outState);
    }

    private void downloadUrl(String feedUrl){
        if(!feedUrl.equalsIgnoreCase(feedCashedUrl)){
            DownloadData downloadData= new DownloadData();
            downloadData.execute(feedUrl);
            feedCashedUrl=feedUrl;
        }else
        {
            Log.d(TAG, "downloadUrl: url not changed");
        }

    }
    private class DownloadData extends AsyncTask<String, Void, String>
    {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

            FeedAdapter feedAdapter = new FeedAdapter
                    (MainActivity.this,R.layout.list_record,parseApplications.getApplications());
            listApps.setAdapter(feedAdapter);
        }
        
        @Override
        protected String doInBackground(String... strings) {
            //Log.d(TAG, "doInBackground: starts with "+ strings[0]);
            String rssFeed = downloadXML(strings[0]);
//            if(rssFeed == null)
//            {
//                Log.e(TAG, "doInBackground: Error downloading");
//            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                 int response = connection.getResponseCode();
              //  Log.d(TAG, "downloadXML: The responce code is " + response);
//              InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500];
                while(true)
                {
                    charsRead = reader.read(inputBuffer);
                    if(charsRead<0){
                       break;
                    }
                    if(charsRead>0){
                        xmlResult.append(String.copyValueOf(inputBuffer,0,charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: INVALID URL "+ e.getMessage() );
            } catch(IOException e){
                Log.e(TAG, "downloadXML: IO Exception reading data : " + e.getMessage() );
            }catch(SecurityException e){
                Log.e(TAG, "downloadXML: Security exception needs permision? " + e.getMessage());
                //e.printStackTrace();
            }
            return null;
         }
    }
}
