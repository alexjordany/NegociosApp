package com.herprogramacion.negociosapp.addeditnegocio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.negociosapp.R;
import com.herprogramacion.negociosapp.negocios.NegociosActivity;

public class AddEditNegocioActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_NEGOCIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String negocioId = getIntent().getStringExtra(NegociosActivity.EXTRA_NEGOCIO_ID);

        setTitle(negocioId == null ? "Añadir negocio" : "Editar negocio");

        AddEditNegocioFragment addEditNegocioFragment = (AddEditNegocioFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_negocio_container);
        if (addEditNegocioFragment == null) {
            addEditNegocioFragment = AddEditNegocioFragment.newInstance(negocioId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_negocio_container, addEditNegocioFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
