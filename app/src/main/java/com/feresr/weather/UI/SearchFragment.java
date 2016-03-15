package com.feresr.weather.UI;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.feresr.weather.R;
import com.feresr.weather.injector.AppComponent;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.SearchPresenter;
import com.feresr.weather.presenters.views.SearchView;

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
    private CardView searchView;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = (CardView) view.findViewById(R.id.search_view);
        searchEditText = (EditText) searchView.findViewById(R.id.search);
        suggestionsRecyclerView = (RecyclerView) view.findViewById(R.id.suggestions_recyclerview);
        searchEditText.requestFocus();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (suggestionAdapter.getCities().isEmpty()) {return true;}
                City firstCity = suggestionAdapter.getCities().get(0);
                if (firstCity != null) {
                    listener.onCitySuggestionSelected(firstCity);
                    return true;
                }
                return false;
            }
        });
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (searchView.isAttachedToWindow()) {
                //float radius = Math.max(searchView.getWidth(), searchView.getHeight()) * 2.0f;
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                ViewAnimationUtils.createCircularReveal(searchView, 0, 0, 5, metrics.widthPixels).start();
            }
        }
    }

    private void initialize() {
        this.getComponent(AppComponent.class).inject(this);
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

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
