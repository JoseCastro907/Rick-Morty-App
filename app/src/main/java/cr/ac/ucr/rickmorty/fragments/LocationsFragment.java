package cr.ac.ucr.rickmorty.fragments;

import android.app.Activity;
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
import cr.ac.ucr.rickmorty.adapters.LocationsAdapter;
import cr.ac.ucr.rickmorty.api.LocationService;
import cr.ac.ucr.rickmorty.api.RetrofitBuilder;
import cr.ac.ucr.rickmorty.models.Location;
import cr.ac.ucr.rickmorty.models.LocationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationsFragment extends Fragment {

    private  final String TAG = "LocationsFragment" ;
    private AppCompatActivity activity;
    private ArrayList<Location> locations;
    private LocationsAdapter locationsAdapter;
    private RecyclerView rvLocations;

    private ProgressBar pbLoading;

    boolean canLoad = true;
    int limit = 0;
    int page =1;

    public LocationsFragment() {

    }

    public static LocationsFragment newInstance() {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locations = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location, container, false);

        pbLoading = view.findViewById(R.id.pb_loading);

        rvLocations = view.findViewById(R.id.rv_locations);

        locationsAdapter = new LocationsAdapter(activity);

        rvLocations.setAdapter(locationsAdapter);
        rvLocations.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);

        rvLocations.setLayoutManager(linearLayoutManager);

        locationsAdapter.addLocations(locations);

        setupRVScrollListener(rvLocations, linearLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        getLocationsInfo(page);
    }

    private void getLocationsInfo(int page) {

        canLoad = false;
        LocationService locationService = RetrofitBuilder.createService(LocationService.class);

        Call<LocationResponse> response = locationService.getLocations(page);

        response.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NonNull Call<LocationResponse> call,@NonNull Response<LocationResponse> response) {

                if (response.isSuccessful()){
                    LocationResponse locationResponse = response.body();
                    ArrayList<Location> locations = locationResponse.getResults();

                    locationsAdapter.addLocations(locations);

                    showLocations(true);


                }else{
                    Log.e(TAG, "onError: "+ response.errorBody());
                }
                canLoad = true;
            }

            @Override
            public void onFailure(@NonNull Call<LocationResponse> call,@NonNull Throwable t) {
                canLoad = true;
                throw  new RuntimeException(t);

            }
        });
    }

    private void setupRVScrollListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){

                    int totalItems = linearLayoutManager.getItemCount();
                    //items que ya se mostraron
                    int pastItems = linearLayoutManager.findFirstVisibleItemPosition();
                    //items que se estan mostrando
                    int visibleItems = linearLayoutManager.getChildCount();

                    if(canLoad){
                        if ((pastItems + visibleItems)>= totalItems){
                            page++;
                            pbLoading.setVisibility(View.VISIBLE);

                            getLocationsInfo(page);
                        }
                    }
                }
            }
        });

    }

    private void showLocations(boolean setVisible){
        rvLocations.setVisibility(setVisible ? View.VISIBLE : View.GONE);
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