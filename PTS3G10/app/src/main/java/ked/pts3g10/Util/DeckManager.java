package ked.pts3g10.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ked.pts3g10.ConnectionActivity;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.LaunchActivity;

/**
 * Created by Mael on 03/01/2018.
 */

public class DeckManager {

    public static boolean save(List<Integer> deck, int user_token) {
        try {
            String url = "http://shyndard.eu/iut/s3/save-deck.php?token="+user_token+"&deck=" + new Gson().toJson(deck);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static List<Card> getPlayerDeck(int user_token) {
        List<Card> user_deck = new ArrayList<>();
        try {
            String url = "http://shyndard.eu/iut/s3/load-deck.php?token="+user_token;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.i("DATA", response.toString());
            for(String id : response.toString().split(",")) {
                Card c = getCardById(Integer.parseInt(id));
                if(c != null) user_deck.add(c);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        if(user_deck.size() < 10) user_deck.clear();
        return user_deck;
    }

    private static Card getCardById(int id) {
        for(Card card : LaunchActivity.cards) {
            if(card.getId() == id) return card;
        }
        return null;
    }
}
