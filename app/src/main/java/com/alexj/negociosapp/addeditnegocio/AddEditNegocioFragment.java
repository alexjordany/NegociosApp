package com.herprogramacion.negociosapp.addeditnegocio;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.herprogramacion.negociosapp.R;
import com.herprogramacion.negociosapp.data.Negocio;
import com.herprogramacion.negociosapp.data.NegociosDbHelper;

/**
 * Vista para creación/edición de un negocio
 *
 */
public class AddEditNegocioFragment extends Fragment {
    private static final String ARG_NEGOCIO_ID = "arg_negocio_id";

    private String mNegocioId;

    private NegociosDbHelper mNegociosDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mPhoneNumberField;
    private TextInputEditText mSpecialtyField;
    private TextInputEditText mBioField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mPhoneNumberLabel;
    private TextInputLayout mSpecialtyLabel;
    private TextInputLayout mBioLabel;


    public AddEditNegocioFragment() {
        // Required empty public constructor
    }

    public static AddEditNegocioFragment newInstance(String negocioId) {
        AddEditNegocioFragment fragment = new AddEditNegocioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEGOCIO_ID, negocioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNegocioId = getArguments().getString(ARG_NEGOCIO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_negocio, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mPhoneNumberField = (TextInputEditText) root.findViewById(R.id.et_phone_number);
        mSpecialtyField = (TextInputEditText) root.findViewById(R.id.et_specialty);
        mBioField = (TextInputEditText) root.findViewById(R.id.et_bio);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mPhoneNumberLabel = (TextInputLayout) root.findViewById(R.id.til_phone_number);
        mSpecialtyLabel = (TextInputLayout) root.findViewById(R.id.til_specialty);
        mBioLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditNegocio();
            }
        });

        mNegociosDbHelper = new NegociosDbHelper(getActivity());

        // Carga de datos
        if (mNegocioId != null) {
            loadNegocio();
        }

        return root;
    }

    private void loadNegocio() {
        new GetNegocioByIdTask().execute();
    }

    private void addEditNegocio() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String phoneNumber = mPhoneNumberField.getText().toString();
        String specialty = mSpecialtyField.getText().toString();
        String bio = mBioField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(specialty)) {
            mSpecialtyLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(bio)) {
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Negocio negocio = new Negocio(name, specialty, phoneNumber, bio, "");

        new AddEditNegocioTask().execute(negocio);

    }

    private void showNegociosScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showNegocio(Negocio negocio) {
        mNameField.setText(negocio.getName());
        mPhoneNumberField.setText(negocio.getNumero());
        mSpecialtyField.setText(negocio.getCategoria());
        mBioField.setText(negocio.getBio());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar el negocio", Toast.LENGTH_SHORT).show();
    }

    private class GetNegocioByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mNegociosDbHelper.getNegocioById(mNegocioId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showNegocio(new Negocio(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditNegocioTask extends AsyncTask<Negocio, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Negocio... negocios) {
            if (mNegocioId != null) {
                return mNegociosDbHelper.updateNegocio(negocios[0], mNegocioId) > 0;

            } else {
                return mNegociosDbHelper.saveNegocio(negocios[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showNegociosScreen(result);
        }

    }

}
