package ked.pts3g10.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import ked.pts3g10.Gameplay.AbilityPackage.SpecialAbility;
import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;

/**
 * Utilisation du CardDBModel.
 */

public class CardDB {

    private CardDBHelper cardDBHelper;
    private SQLiteDatabase db;
    private Context context;

    public CardDB(Context context){
        cardDBHelper  = new CardDBHelper(context);
        db = cardDBHelper.getReadableDatabase(); // Assez coûteux en ressources, ne pas abuser de cet appel
        this.context = context;
    }

    public void disconnect(){
        cardDBHelper.disconnect();
    }

    public void insert(Card c) {
        // Gets the data repository in write mode
        SQLiteDatabase db = cardDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CardDBModel.CardDbEntry.CARD_NAME, c.getName());
        values.put(CardDBModel.CardDbEntry.CARD_DESCRIPTION, c.getDescription());
        values.put(CardDBModel.CardDbEntry.CARD_CRYSTAL_COST, c.getCrystalCost());
        values.put(CardDBModel.CardDbEntry.CARD_AP, c.getAttactPoints());
        values.put(CardDBModel.CardDbEntry.CARD_RP, c.getRangePoints());
        values.put(CardDBModel.CardDbEntry.CARD_BG, c.getBackground().getTag().toString());
        values.put(CardDBModel.CardDbEntry.CARD_THMBN, c.getThumbnail().getTag().toString());

        if(c instanceof Army){
            values.put(CardDBModel.CardDbEntry.CARD_TYPE, 1);
            values.put(CardDBModel.CardDbEntry.CARD_HP, ((Army) c).getHealthPoints());
            values.put(CardDBModel.CardDbEntry.CARD_MP, ((Army) c).getMovementPoints());

        }else if(c instanceof Hero){
            values.put(CardDBModel.CardDbEntry.CARD_TYPE, 2);
            values.put(CardDBModel.CardDbEntry.CARD_ABILITY_ID, ((Hero) c).getAbility().getId());
            values.put(CardDBModel.CardDbEntry.CARD_HP, ((Hero) c).getHealthPoints());
            values.put(CardDBModel.CardDbEntry.CARD_MP, ((Army) c).getMovementPoints());
        }else if(c instanceof Spell) {
            values.put(CardDBModel.CardDbEntry.CARD_TYPE, 3);
            values.put(CardDBModel.CardDbEntry.CARD_ABILITY_ID, ((Spell) c).getAbility().getId());
        }

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(CardDBModel.CardDbEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Card> select(){

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CardDBModel.CardDbEntry._ID,
                CardDBModel.CardDbEntry.CARD_TYPE,
                CardDBModel.CardDbEntry.CARD_NAME,
                CardDBModel.CardDbEntry.CARD_DESCRIPTION,
                CardDBModel.CardDbEntry.CARD_CRYSTAL_COST,
                CardDBModel.CardDbEntry.CARD_AP,
                CardDBModel.CardDbEntry.CARD_RP,
                CardDBModel.CardDbEntry.CARD_HP,
                CardDBModel.CardDbEntry.CARD_MP,
                CardDBModel.CardDbEntry.CARD_BG,
                CardDBModel.CardDbEntry.CARD_THMBN,
                CardDBModel.CardDbEntry.CARD_ABILITY_ID
        };

// Filter results WHERE "title" = 'My Title'
       /* String selection = CardDBModel.CardDbEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };*/

// How you want the results sorted in the resulting Cursor
       /* String sortOrder =
                CardDBModel.CardDbEntry.COLUMN_NAME_SUBTITLE + " DESC";
*/
        Cursor cursor = db.query(
                CardDBModel.CardDbEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        ArrayList<Card> cards = new ArrayList<Card>();
        while(cursor.moveToNext()) {
            int cardType = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_TYPE));
            Card c;

            String name = cursor.getString(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_NAME));
            String description = cursor.getString(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_DESCRIPTION));
            int crystalCost = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_CRYSTAL_COST));
            int ap = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_AP));
            int rp = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_RP));
            ImageView bg = new ImageView(context);
            bg.setBackgroundResource(cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_BG)));
            bg.setTag(cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_BG)));
            ImageView thmbn = new ImageView(context);
            thmbn.setBackgroundResource(cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_THMBN)));
            thmbn.setTag(cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_THMBN)));

            if(cardType == 1){
                //String name,String description,int crystalCost, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn
                int hp = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_HP));
                int mp = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_MP));

                c = new Army(name,description,crystalCost,ap,rp,hp,mp,bg,thmbn);
                cards.add(c);

            }else if(cardType == 2){
                int hp = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_HP));
                int mp = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_MP));
                int abilityId = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_ABILITY_ID));

                c = new Hero(name, description, crystalCost, ap, rp, hp, mp, bg, thmbn,
                        new SpecialAbility(abilityId,"Ability placeholder","placeholder description"));
                cards.add(c);

            }else if(cardType == 3){
                int abilityId = cursor.getInt(cursor.getColumnIndex(CardDBModel.CardDbEntry.CARD_ABILITY_ID));
                c = new Spell(name, description, crystalCost, ap, rp, bg, thmbn,
                        new SpecialAbility(abilityId,"Ability placeholder","placeholder description"));
                cards.add(c);
            }
        }
        cursor.close();
        return cards;
    }
}
