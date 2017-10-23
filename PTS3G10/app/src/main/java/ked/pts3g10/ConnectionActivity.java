package ked.pts3g10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ConnectionActivity extends AppCompatActivity {

    private EditText pseudo;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        pseudo = (EditText)findViewById(R.id.pseudo);
        password =(EditText)findViewById(R.id.password);
    }
}
