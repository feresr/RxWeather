package com.feresr.rxweather.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.feresr.rxweather.R;
import com.feresr.rxweather.injector.WeatherApiComponent;
import com.feresr.rxweather.models.City;
import com.feresr.rxweather.presenters.SearchPresenter;
import com.feresr.rxweather.presenters.views.SearchView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchView {

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
        presenter.setGoogleApiClient(googleApiClientProvider.getApiClient());
        presenter.onCreate();
        presenter.setFragmentInteractionListener(listener);
        presenter.setSuggestionAdapter(suggestionAdapter);
        suggestionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), presenter));
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
    public void setCities(ArrayList<City> cities) {
        suggestionAdapter.setCities(cities);
    }
}
