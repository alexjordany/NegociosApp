package com.herprogramacion.negociosapp.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/**
 * Entidad "abogado"
 */
public class Negocio {
    private String id;
    private String name;
    private String categoria;
    private String numero;
    private String bio;
    private String avatarUri;

    public Negocio(String name,
                   String categoria, String numero,
                   String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.categoria = categoria;
        this.numero = numero;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public Negocio(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.NAME));
        categoria = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.CATEGORIA));
        numero = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.NUMERO));
        bio = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex(NegociosContract.NegocioEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NegociosContract.NegocioEntry.ID, id);
        values.put(NegociosContract.NegocioEntry.NAME, name);
        values.put(NegociosContract.NegocioEntry.CATEGORIA, categoria);
        values.put(NegociosContract.NegocioEntry.NUMERO, numero);
        values.put(NegociosContract.NegocioEntry.BIO, bio);
        values.put(NegociosContract.NegocioEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNumero() {
        return numero;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
