package com.feresr.rxweather.models.wrappers;

import com.feresr.rxweather.models.Main;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Fernando on 16/10/2015.
 */
public class FiveDaysWrapper extends RealmObject {

    private RealmList<ListaWrapper> lista = new RealmList<ListaWrapper>();
    private MainWrapper main;

    public RealmList<ListaWrapper> getLista() {
        return lista;
    }

    public void setLista(RealmList<ListaWrapper> lista) {
        this.lista = lista;
    }

    public MainWrapper getMain() {
        return main;
    }

    public void setMain(MainWrapper main) {
        this.main = main;
    }
}
