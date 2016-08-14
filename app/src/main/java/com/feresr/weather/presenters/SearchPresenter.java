package com.feresr.weather.presenters;

import android.text.Editable;
import android.text.TextWatcher;

import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.RecyclerItemClickListener;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.common.BasePresenter;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.views.SearchView;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Fernando on 7/11/2015.
 */
public class SearchPresenter extends BasePresenter<SearchView> implements TextWatcher, RecyclerItemClickListener.OnItemClickListener {

    private ArrayList<City> cities;

    private GoogleApiClient googleApiClient;
    private FragmentInteractionsListener fragmentInteractionListener;
    private SuggestionAdapter suggestionAdapter;

    @Inject
    public SearchPresenter() {
        super();
    }

    @Override
    public void onCreate() {
        cities = new ArrayList<>();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void onCitySuggestionSelected(final City city) {
        fragmentInteractionListener.onCitySuggestionSelected(city);
    }

    @Override
    public void onItemClick(android.view.View view, int position) {
        if (position == -1) {
            return;
        }
        onCitySuggestionSelected(suggestionAdapter.getCities().get(position));
    }


    public void setSuggestionAdapter(SuggestionAdapter adapter) {
        this.suggestionAdapter = adapter;
    }

    public void setFragmentInteractionListener(FragmentInteractionsListener listener) {
        this.fragmentInteractionListener = listener;
    }
}
