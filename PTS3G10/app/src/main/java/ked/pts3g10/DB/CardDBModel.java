package ked.pts3g10.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import ked.pts3g10.Gameplay.CardPackage.Card;

/**
 * Sert a sauvegarder les différentes cartes
 * Pour l'instant les cartes sont sauvegardées en dur
 * TODO: un système de versioning qui se comparerai avec le serveur et updaterai la DB de l'appli en fonction
 */

public final class CardDBModel {

    private CardDBModel() {
    } // Privé car il ne faut pas instancier cette classe (ref: developper.android.com)

    /* Classe interne pour définir la structure */
    public static class CardDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "card";
        public static final String CARD_TYPE = "card_type";
        public static final String CARD_NAME = "card_name";
        public static final String CARD_DESCRIPTION = "card_description";
        public static final String CARD_CRYSTAL_COST = "card_crystal_cost";
        public static final String CARD_AP = "card_ap";
        public static final String CARD_RP = "card_rp";
        public static final String CARD_HP = "card_hp";
        public static final String CARD_MP = "card_mp";
        public static final String CARD_BG = "card_bg";
        public static final String CARD_THMBN = "card_thmbn";
        public static final String CARD_ABILITY_ID = "card_ability_id";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CardDbEntry.TABLE_NAME + " (" +
                    CardDbEntry._ID + " INTEGER PRIMARY KEY," +
                    CardDbEntry.CARD_TYPE + " INTEGER," +
                    CardDbEntry.CARD_NAME + " VARCHAR(25)," +
                    CardDbEntry.CARD_DESCRIPTION + " TEXT," +
                    CardDbEntry.CARD_CRYSTAL_COST + " INTEGER," +
                    CardDbEntry.CARD_AP + " INTEGER," +
                    CardDbEntry.CARD_RP + " INTEGER," +
                    CardDbEntry.CARD_HP + " INTEGER," +
                    CardDbEntry.CARD_MP + " INTEGER," +
                    CardDbEntry.CARD_BG + " INTEGER," +
                    CardDbEntry.CARD_THMBN + " INTEGER," +
                    CardDbEntry.CARD_ABILITY_ID + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CardDbEntry.TABLE_NAME;

}
