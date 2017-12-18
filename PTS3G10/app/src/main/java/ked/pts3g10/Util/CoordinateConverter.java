package ked.pts3g10.Util;

/**
 * Cette classe permet de convertir des coordonées du Joueur1 pour le Joueur2
 */

public abstract class CoordinateConverter {

    public static Pos convert(Pos p){
        return new Pos(2+(2-p.getPosX()),2+(2-p.getPosY()));
    }
}
