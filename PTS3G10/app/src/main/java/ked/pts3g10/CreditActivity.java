package ked.pts3g10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import ked.pts3g10.R;


/**
 * Created by Kévin on 22/10/2017.
 */

public class CreditActivity extends AppCompatActivity {

    private ImageView logoIut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logoIut = (ImageView)findViewById(R.id.CreditIut);
        logoIut.setColorFilter(R.color.colorBlue);
        setContentView(R.layout.activity_credits);

    }
}
