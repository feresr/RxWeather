package com.feresr.rxweather.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.feresr.rxweather.R;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.SearchPresenter;
import com.feresr.rxweather.presenters.views.SearchView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchView, RecyclerItemClickListener.OnItemClickListener {

    @Inject
    SearchPresenter presenter;

    private EditText searchEditText;
    private RecyclerView suggestionsRecyclerView;
    private GoogleApiClientProvider googleApiClientProvider;
    private SuggestionAdapter suggestionAdapter;
    private FragmentInteractionsListener listener;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = (EditText) view.findViewById(R.id.search);
        suggestionsRecyclerView = (RecyclerView) view.findViewById(R.id.suggestions_recyclerview);
        searchEditText.requestFocus();
        suggestionAdapter = new SuggestionAdapter(getActivity());
        suggestionsRecyclerView.setAdapter(suggestionAdapter);
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        suggestionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        this.getComponent(WeatherApiComponent.class).inject(this);
        presenter.attachView(this);
        if (getArguments() != null) {
            presenter.attachIncomingArg(getArguments());
        }
        presenter.onCreate();
        searchEditText.addTextChangedListener(presenter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            googleApiClientProvider = (GoogleApiClientProvider) context;
            listener = (FragmentInteractionsListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onCitySuggestionSelected(suggestionAdapter.getCities().get(position), listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public GoogleApiClient getGoogleApiClient() {
        return googleApiClientProvider.getApiClient();
    }

    @Override
    public void setCities(ArrayList<City> cities) {
        suggestionAdapter.setCities(cities);
    }
}
