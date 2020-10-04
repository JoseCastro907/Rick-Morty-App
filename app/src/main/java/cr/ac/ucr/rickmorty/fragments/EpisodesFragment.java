package cr.ac.ucr.rickmorty.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.R;
import cr.ac.ucr.rickmorty.adapters.EpisodesAdapter;
import cr.ac.ucr.rickmorty.api.EpisodeService;
import cr.ac.ucr.rickmorty.api.RetrofitBuilder;
import cr.ac.ucr.rickmorty.models.Episode;
import cr.ac.ucr.rickmorty.models.EpisodeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EpisodesFragment extends Fragment {

    private ArrayList<Episode> episodes;
    private  final String TAG = "EpisodesFragment" ;
    private AppCompatActivity activity;
    private EpisodesAdapter episodesAdapter;
    private RecyclerView rvEpisodes;

    private ProgressBar pbLoading;

    boolean canLoad = true;
    int limit = 0;
    int page =1;

    public EpisodesFragment() {

    }

    public static EpisodesFragment newInstance() {
        EpisodesFragment fragment = new EpisodesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        episodes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_episodes, container, false);

        pbLoading = view.findViewById(R.id.pb_loading);
        rvEpisodes = view.findViewById(R.id.rv_episodes);
        episodesAdapter = new EpisodesAdapter(activity);
        rvEpisodes.setAdapter(episodesAdapter);
        rvEpisodes.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        rvEpisodes.setLayoutManager(linearLayoutManager);

        episodesAdapter.addEpisodes(episodes);

        setupRVScrollListener(rvEpisodes,linearLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getEpisodeInfo(page);
    }

    private void getEpisodeInfo(int page) {
        canLoad = false;
        EpisodeService episodeService = RetrofitBuilder.createService(EpisodeService.class);

        Call<EpisodeResponse> response = episodeService.getEpisodes(page);

        response.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<EpisodeResponse> call, @NonNull Response<EpisodeResponse> response) {
                if (response.isSuccessful()){
                    EpisodeResponse episodeResponse = response.body();
                    ArrayList<Episode> episodes = episodeResponse.getResults();

                    episodesAdapter.addEpisodes(episodes);

                    showEpisodes(true);
                }else{
                    Log.e(TAG, "onError: "+ response.errorBody());
                }
                canLoad = true;
            }

            @Override
            public void onFailure(@NonNull Call<EpisodeResponse> call, @NonNull Throwable t) {
                canLoad = true;
                throw  new RuntimeException(t);
            }
        });

    }


    private void setupRVScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // dy = + -> si hace scroll hacia abajo
                // dy = - -> si hace scroll hacia arriba
                if (dy > 0){
                    // Log.i(TAG,"onScrolled: " + dy);

                    //total de items
                    int totalItems = linearLayoutManager.getItemCount();
                    //items que ya se mostraron
                    int pastItems = linearLayoutManager.findFirstVisibleItemPosition();
                    //items que se estan mostrando
                    int visibleItems = linearLayoutManager.getChildCount();


                    if(canLoad){
                        if ((pastItems + visibleItems)>= totalItems){
                            page++;
                            pbLoading.setVisibility(View.VISIBLE);

                            getEpisodeInfo(page);
                        }
                    }
                }
            }
        });
    }


    private void showEpisodes(boolean setVisible){
        rvEpisodes.setVisibility(setVisible ? View.VISIBLE : View.GONE);
        pbLoading.setVisibility(!setVisible ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity=null;
    }
}