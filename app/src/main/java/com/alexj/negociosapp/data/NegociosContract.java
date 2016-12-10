package com.herprogramacion.negociosapp.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class NegociosContract {

    public static abstract class NegocioEntry implements BaseColumns{
        public static final String TABLE_NAME ="negocio";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CATEGORIA = "categoria";
        public static final String NUMERO = "numero";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";
    }
}
