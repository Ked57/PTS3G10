package ked.pts3g10.Util;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import ked.pts3g10.Gameplay.AbilityPackage.Ability;
import ked.pts3g10.Gameplay.CardPackage.Army;
import ked.pts3g10.Gameplay.CardPackage.Card;
import ked.pts3g10.Gameplay.CardPackage.Hero;
import ked.pts3g10.Gameplay.CardPackage.Spell;
import ked.pts3g10.LaunchActivity;

/**
 * Classe utilisée pour pour lire et écrire un fichier XML contenant les informations sur les cartes
 * Crédit : Iza Marfisi
 */

public class XMLParser {

    public int version = 0;

    /**
     * Cette classe représente une "entry" dans le fichier XML
     * elle contient les informations sur les attributs "nom" et "message"
     */
    public class Entry {

        // Attributs de la classe Entry - String name,String description,int crystalCost, int ap, int rp, int hp, int mp, ImageView bg, ImageView thmbn, boolean adversary
        public final String name,description,adversary,type;
        public final int crystalCost,ap,rp,hp,mp,abilityId,bg,thmbn;



        private Entry(String name,String description, String type,int crystalCost, int ap, int rp, int hp, int mp,int abilityId, int bg, int thmbn, String adversary) {
            this.name = name;
            this.description = description;
            this.adversary = adversary;
            this.crystalCost = crystalCost;
            this.type = type;
            this.ap = ap;
            this.rp = rp;
            this.hp = hp;
            this.mp = mp;
            this.abilityId = abilityId;
            this.bg = bg;
            this.thmbn = thmbn;
        }
    }


    /**
     * Cette fonction parse le contenu d'un fichier et retourne le résultat
     *
     * @param in le fichier contenant le XML
     * @return une liste d'objet de type Entry
     */
    public ArrayList<Card> parse(InputStream in,LaunchActivity context) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            ArrayList<Card> cards = new ArrayList<>();
            for(Entry e : readData(parser)){
                switch(e.type){
                    case "army":
                        boolean adversary = e.adversary.equals("true");
                        int bg = ImagesEnum.getDrawableIdWithImageId(e.bg);
                        int thmbn = ImagesEnum.getDrawableIdWithImageId(e.thmbn);
                        cards.add(new Army(e.name,e.description,e.crystalCost,e.ap,e.rp,e.hp,e.mp,bg,thmbn,adversary));
                        break;
                    case "spell":
                        boolean adversary2 = e.adversary.equals("true");
                        int bg2 = ImagesEnum.getDrawableIdWithImageId(e.bg);
                        int thmbn2 = ImagesEnum.getDrawableIdWithImageId(e.thmbn);
                        Ability ability = context.getAbilityById(e.abilityId);
                        if(ability.equals(context.emptyAbility)){
                            Log.e("Parser","Ability is null");
                            break;
                        }
                        cards.add(new Spell(e.name,e.description,e.crystalCost,e.ap,e.rp,bg2,thmbn2,ability,adversary2));
                        break;
                    //TODO: Hero cards
                }
            }
            return cards;
        } finally {
            in.close();
        }
    }


    /**
     * Cette fonction parse le contenu d'une balise <entry> et retourne le résultat
     *
     * @param parser le bout de XML
     * @return une liste d'objet de type Entry
     */
    private List<Entry> readData(XmlPullParser parser) throws XmlPullParserException, IOException {
        // création d'une nouvelle liste d'objets de type Entry
        // que l'obn va remplir avec le contenu du XML
        List<Entry> entries = new ArrayList<>();

        // le XML doit commencer par "<data>"
        parser.require(XmlPullParser.START_TAG, null, "data");

        // tand que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if(name.equals("version")){
                version = Integer.parseInt(readTag(parser,"version"));
            }

            // si cette balise est un <entry>, extraire le contenu de cette balise avec readEntry()
            // et ajouter le nouvel object Entry dans la liste entries
            else if (name.equals("entry")) {
                entries.add(readEntry(parser));
                // sinon, sauter la balise
            } else {
                skip(parser);
            }
        }
        // retourner toute la liste des Entry, qui viennent d'être remplis
        return entries;
    }


    /**
     * Cette fonction parse le contenu d'une balise <entry>
     * Si elle rencontre une balise connue, elle fait appel à readTag() pour extraire le contenu de la balise
     * Sinon, elle saute la balise avec skip()
     *
     * @param parser le bout de XML
     * @return un object de type Entry qui contient les données extrait du XML
     */
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        // le XML doit commencer par "<entry>"
        parser.require(XmlPullParser.START_TAG, null, "entry");
        String name = "";
        String description = "";
        String adversary = "";
        int crystalCost = -1;
        String type = "";
        int ap = -1;
        int rp = -1;
        int hp = -1;
        int mp = -1;
        int abilityId = -1;
        int bg = -1;
        int thmbn = -1;

        // tand que l'élément suivant n'est pas une balise fermante </..>
        while (parser.next() != XmlPullParser.END_TAG) {
            // si le XML est une de balise ouvrante <..>, continuer
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String n = parser.getName();

            switch (n) {
                case "name":
                    name = readTag(parser, "name");
                    break;
                case "description":
                    description = readTag(parser, "description");
                    break;
                case "adversary":
                    adversary = readTag(parser, "adversary");
                    break;
                case "crystalCost":
                    crystalCost = Integer.parseInt(readTag(parser, "crystalCost"));
                    break;
                case "type":
                    type = readTag(parser, "type");
                    break;
                case "ap":
                    ap = Integer.parseInt(readTag(parser, "ap"));
                    break;
                case "rp":
                    rp = Integer.parseInt(readTag(parser, "rp"));
                    break;
                case "hp":
                    hp = Integer.parseInt(readTag(parser, "hp"));
                    break;
                case "abilityId":
                    abilityId = Integer.parseInt(readTag(parser, "abilityId"));
                    break;
                case "mp":
                    mp = Integer.parseInt(readTag(parser, "mp"));
                    Log.i("Parser","Parsed mp : "+mp);
                    break;
                case "bg":
                    bg = Integer.decode(readTag(parser, "bg"));
                    break;
                case "thmbn":
                    thmbn = Integer.decode(readTag(parser, "thmbn"));
                    break;

                default:
                    skip(parser);
                    break;
            }
        }
        // retourner une nouvelle Entry avec le nom et message extrait du XML
        return new Entry(name, description, type, crystalCost,ap,rp,hp,mp,abilityId,bg,thmbn,adversary);
    }


    /**
     * Cette fonction extrait le contenu d'une balise avec le titre tag
     *
     * @param parser le bout de XML
     * @param tag    le titre de la balise (ex: "nom", "message")
     * @return le contenu String de la balise
     */
    private String readTag(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        String result = "";
        // le XML doit commencer par "<tag>"
        parser.require(XmlPullParser.START_TAG, null, tag);
        /* si l'élément suivant est un text, sauvegarder le text dans result
        et passer à l'élément suivant */
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        // le XML doit finir par "</tag>"
        parser.require(XmlPullParser.END_TAG, null, tag);
        return result;
    }


    /**
     * Cette fonction saute une balise entière, y compris les autres balises qui sont à l'interieur
     *
     * @param parser le bout de XML
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /**
     * Cette fonction écrit dans le fichier XML et sauvegarde les avions et les paramètres
     * @param context
     * @param cards
     * @throws IOException
     */
    public void write(Context context, ArrayList<Card> cards, int version) throws IOException {

        FileOutputStream fileos = new FileOutputStream(context.getFilesDir()+"/save.xml");//Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/ked.atc-simulator/files/save.xml");
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.startTag(null, "data");
        xmlSerializer.startTag(null, "version");
        xmlSerializer.text(""+version); // TODO: Récup la version et la réécrire ici
        xmlSerializer.endTag(null,"version");
        for(Card c : cards) {
            xmlSerializer.startTag(null, "entry");
            xmlSerializer.startTag(null, "name");
            xmlSerializer.text(c.getName());
            xmlSerializer.endTag(null, "name");

            xmlSerializer.startTag(null, "description");
            xmlSerializer.text(c.getDescription());
            xmlSerializer.endTag(null, "description");

            xmlSerializer.startTag(null, "adversary");
            xmlSerializer.text(""+c.isAdversary());
            xmlSerializer.endTag(null, "adversary");

            xmlSerializer.startTag(null, "crystalCost");
            xmlSerializer.text(""+c.getCrystalCost());
            xmlSerializer.endTag(null, "crystalCost");

            xmlSerializer.startTag(null, "type");
            if(c instanceof Army)
                xmlSerializer.text("army");
            else if(c instanceof Hero)
                xmlSerializer.text("hero");
            else if(c instanceof Spell)
                xmlSerializer.text("spell");
            else xmlSerializer.text("default");
            xmlSerializer.endTag(null, "type");

            xmlSerializer.startTag(null, "ap");
            xmlSerializer.text(""+c.getAttactPoints());
            xmlSerializer.endTag(null, "ap");

            xmlSerializer.startTag(null, "rp");
            xmlSerializer.text(""+c.getRangePoints());
            xmlSerializer.endTag(null, "rp");

            xmlSerializer.startTag(null, "hp");
            if(c instanceof Army)
                xmlSerializer.text(""+((Army) c).getHealthPoints());
            else xmlSerializer.text("0");
            xmlSerializer.endTag(null, "hp");

            xmlSerializer.startTag(null, "abilityId");
            if(c instanceof Hero)
                xmlSerializer.text("0");
            else if(c instanceof Spell)
                xmlSerializer.text("0");
            else xmlSerializer.text("0");
            xmlSerializer.endTag(null, "abilityId");

            xmlSerializer.startTag(null, "mp");
            if(c instanceof Army)
                xmlSerializer.text(""+((Army) c).getMovementPoints());
            else if(c instanceof Hero)
                xmlSerializer.text(""+((Hero) c).getMovementPoints());
            else xmlSerializer.text("0");
            xmlSerializer.endTag(null, "mp");

            xmlSerializer.startTag(null, "bg");
            xmlSerializer.text(""+ImagesEnum.getImageIdWithDrawableId(c.getBackground()));
            xmlSerializer.endTag(null, "bg");

            xmlSerializer.startTag(null, "thmbn");
            xmlSerializer.text(""+ImagesEnum.getImageIdWithDrawableId(c.getBackground()));
            xmlSerializer.endTag(null, "thmbn");

            xmlSerializer.endTag(null, "entry");
        }
        xmlSerializer.endTag(null, "data");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
        String dataWrite = writer.toString();
        Log.i("Parser",dataWrite);
        fileos.write(dataWrite.getBytes());
        fileos.close();
    }

    public int getVersion(){ return version;}
}
