package com.herprogramacion.negociosapp.negocios;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.herprogramacion.negociosapp.R;
import com.herprogramacion.negociosapp.addeditnegocio.AddEditNegocioActivity;
import com.herprogramacion.negociosapp.data.NegociosDbHelper;
import com.herprogramacion.negociosapp.negociodetail.NegocioDetailActivity;
import com.herprogramacion.negociosapp.data.NegociosContract;


/**
 * Vista para la lista de abogados del gabinete
 */
public class NegociosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_NEGOCIO = 2;

    private NegociosDbHelper mNegociosDbHelper;

    private ListView mNegociosList;
    private NegociosCursorAdapter mNegociosAdapter;
    private FloatingActionButton mAddButton;


    public NegociosFragment() {
        // Required empty public constructor
    }

    public static NegociosFragment newInstance() {
        return new NegociosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_negocios, container, false);

        // Referencias UI
        mNegociosList = (ListView) root.findViewById(R.id.negocios_list);
        mNegociosAdapter = new NegociosCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mNegociosList.setAdapter(mNegociosAdapter);

        // Eventos
        mNegociosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mNegociosAdapter.getItem(i);
                String currentNegocioId = currentItem.getString(
                        currentItem.getColumnIndex(NegociosContract.NegocioEntry.ID));

                showDetailScreen(currentNegocioId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(NegociosDbHelper.DATABASE_NAME);

        // Instancia de helper
        mNegociosDbHelper = new NegociosDbHelper(getActivity());

        // Carga de datos
        loadNegocios();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditNegocioActivity.REQUEST_ADD_NEGOCIO:
                    showSuccessfullSavedMessage();
                    loadNegocios();
                    break;
                case REQUEST_UPDATE_DELETE_NEGOCIO:
                    loadNegocios();
                    break;
            }
        }
    }

    private void loadNegocios() {
        new NegociosLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Negocio guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditNegocioActivity.class);
        startActivityForResult(intent, AddEditNegocioActivity.REQUEST_ADD_NEGOCIO);
    }

    private void showDetailScreen(String negocioId) {
        Intent intent = new Intent(getActivity(), NegocioDetailActivity.class);
        intent.putExtra(NegociosActivity.EXTRA_NEGOCIO_ID, negocioId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_NEGOCIO);
    }

    private class NegociosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mNegociosDbHelper.getAllNegocios();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mNegociosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
