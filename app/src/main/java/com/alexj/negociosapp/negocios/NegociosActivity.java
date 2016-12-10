package com.herprogramacion.negociosapp.negocios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.negociosapp.R;

public class NegociosActivity extends AppCompatActivity {

    public static final String EXTRA_NEGOCIO_ID = "extra_negocio_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NegociosFragment fragment = (NegociosFragment)
                getSupportFragmentManager().findFragmentById(R.id.negocios_container);

        if (fragment == null) {
            fragment = NegociosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.negocios_container, fragment)
                    .commit();
        }
    }
}
