package ked.pts3g10.Util;

import android.util.Log;

import ked.pts3g10.R;

/**
 * Liste des images avec leur id et leur R.drawable.id
 *
 */

public enum ImagesEnum {
    //Black
    CASTLE(0, R.drawable.castle),
    SWORD(1, R.drawable.sword),
    BOW(2, R.drawable.bow),
    //RED
    CASTLERED(1000, R.drawable.castlered),
    SWORDRED(1001, R.drawable.swordred),
    BOWRED(1002, R.drawable.bowred);

    private int imageId;
    private int drawableId;

    private ImagesEnum(int imageId, int drawableId){
        this.imageId = imageId;
        this.drawableId = drawableId;
    }

    public static int getDrawableIdWithImageId(int imageId){
        for(ImagesEnum e : values()) if(e.imageId == imageId) return e.drawableId;
        throw new NullPointerException();
    }

}