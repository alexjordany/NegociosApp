package com.herprogramacion.negociosapp.negociodetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.herprogramacion.negociosapp.R;
import com.herprogramacion.negociosapp.addeditnegocio.AddEditNegocioActivity;
import com.herprogramacion.negociosapp.data.Negocio;
import com.herprogramacion.negociosapp.data.NegociosDbHelper;
import com.herprogramacion.negociosapp.negocios.NegociosActivity;
import com.herprogramacion.negociosapp.negocios.NegociosFragment;

/**
 * Vista para el detalle del abogado
 */
public class NegocioDetailFragment extends Fragment {
    private static final String ARG_NEGOCIO_ID = "negocioId";

    private String mNegocioId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;

    private NegociosDbHelper mNegociosDbHelper;


    public NegocioDetailFragment() {
        // Required empty public constructor
    }

    public static NegocioDetailFragment newInstance(String negocioId) {
        NegocioDetailFragment fragment = new NegocioDetailFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_negocio_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = (TextView) root.findViewById(R.id.tv_phone_number);
        mSpecialty = (TextView) root.findViewById(R.id.tv_specialty);
        mBio = (TextView) root.findViewById(R.id.tv_bio);

        mNegociosDbHelper = new NegociosDbHelper(getActivity());

        loadLawyer();

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteLawyerTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NegociosFragment.REQUEST_UPDATE_DELETE_NEGOCIO) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showLawyer(Negocio negocio) {
        mCollapsingView.setTitle(negocio.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + negocio.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(negocio.getNumero());
        mSpecialty.setText(negocio.getCategoria());
        mBio.setText(negocio.getBio());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditNegocioActivity.class);
        intent.putExtra(NegociosActivity.EXTRA_NEGOCIO_ID, mNegocioId);
        startActivityForResult(intent, NegociosFragment.REQUEST_UPDATE_DELETE_NEGOCIO);
    }

    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mNegociosDbHelper.getNegocioById(mNegocioId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Negocio(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mNegociosDbHelper.deleteNegocio(mNegocioId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

}
