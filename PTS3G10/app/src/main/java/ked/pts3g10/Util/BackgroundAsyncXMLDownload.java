package ked.pts3g10.Util;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.LaunchActivity;

/**
 * Pour éxécuter le téléchargement du XML en tâche de fond
 */

public class BackgroundAsyncXMLDownload extends AsyncTask<String, Void, String> {

    private XMLParser xmlParser;
    private ArrayList<Card> cards;
    private int version;
    private LaunchActivity context;

    public BackgroundAsyncXMLDownload(XMLParser parser, LaunchActivity context){
        cards = new ArrayList<>();
        version = 0;
        xmlParser = parser;
        this.context = context;
    }

    @Override
    protected String doInBackground(String ...params) {
        URL url = null;
        String returnedResult = "";
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            Log.e("Parser", Log.getStackTraceString(e));
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            cards = xmlParser.parse(is);
            version = xmlParser.getVersion();
            returnedResult = "ok";
        } catch (IOException e) {
            Log.e("Parser", Log.getStackTraceString(e));
        } catch (XmlPullParserException e) {
            Log.e("Parser", Log.getStackTraceString(e));
        }
        return returnedResult;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("ok")){
            context.initCards(version,cards);
        }else Log.i("Parser","Error parsing distant file");
    }
}
