package com.feresr.weather.UI.fragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.feresr.weather.DI.component.ActivityComponent;
import com.feresr.weather.R;
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

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.suggestions_recyclerview)
    RecyclerView suggestionsRecyclerView;

    @Inject
    SuggestionAdapter suggestionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText.addTextChangedListener(presenter);

        searchEditText.requestFocus();

        suggestionsRecyclerView.setAdapter(suggestionAdapter);
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        suggestionAdapter.setOnClickListener(presenter);

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
    public void setCities(ArrayList<City> cities) {
        suggestionAdapter.setCities(cities);
    }

    @Override
    public void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
