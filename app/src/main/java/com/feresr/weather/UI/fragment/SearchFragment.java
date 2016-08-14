package com.feresr.weather.UI.fragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
import com.feresr.weather.UI.FragmentInteractionsListener;
import com.feresr.weather.UI.GoogleApiClientProvider;
import com.feresr.weather.UI.RecyclerItemClickListener;
import com.feresr.weather.UI.SuggestionAdapter;
import com.feresr.weather.common.BaseFragment;
import com.feresr.weather.models.City;
import com.feresr.weather.presenters.SearchPresenter;
import com.feresr.weather.presenters.views.SearchView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment<SearchPresenter> implements SearchView {

    @BindView(R.id.search)
    EditText searchEditText;

    @BindView(R.id.search_view)
    CardView searchView;

    @BindView(R.id.suggestions_recyclerview)
    RecyclerView suggestionsRecyclerView;

    @Inject
    SuggestionAdapter suggestionAdapter;

    private GoogleApiClientProvider googleApiClientProvider;

    private FragmentInteractionsListener listener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setFragmentInteractionListener(listener);
        presenter.setSuggestionAdapter(suggestionAdapter);
        suggestionsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), presenter));
        searchEditText.addTextChangedListener(presenter);

        searchEditText.requestFocus();

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (suggestionAdapter.getCities().isEmpty()) {
                    return true;
                }
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (searchView.isAttachedToWindow()) {
                //float radius = Math.max(searchView.getWidth(), searchView.getHeight()) * 2.0f;
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                ViewAnimationUtils.createCircularReveal(searchView, 0, 0, 5, metrics.widthPixels).setDuration(500).start();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        } else {
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    protected void injectDependencies(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void bindPresenterToView() {
        presenter.setView(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_search;
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
    public void setCities(ArrayList<City> cities) {
        suggestionAdapter.setCities(cities);
    }

}
