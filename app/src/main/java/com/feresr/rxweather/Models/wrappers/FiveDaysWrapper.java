package com.feresr.rxweather.models.wrappers;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Fernando on 16/10/2015.
 */
public class FiveDaysWrapper extends RealmObject {

    private RealmList<ListaWrapper> lista = new RealmList<ListaWrapper>();

    public RealmList<ListaWrapper> getLista() {
        return lista;
    }

    public void setLista(RealmList<ListaWrapper> lista) {
        this.lista = lista;
    }
}
