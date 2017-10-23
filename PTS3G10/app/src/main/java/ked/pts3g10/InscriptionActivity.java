package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class InscriptionActivity extends AppCompatActivity {

    private EditText pseudoIns;
    private EditText passwordIns;
    private EditText email;
    private EditText passwordConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        pseudoIns = (EditText)findViewById(R.id.pseudoIns);
        passwordIns = (EditText)findViewById(R.id.passwordIns);
        email = (EditText)findViewById(R.id.email);
        passwordConf = (EditText)findViewById(R.id.mdpconf);
    }
}
