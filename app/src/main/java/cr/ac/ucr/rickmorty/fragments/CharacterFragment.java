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

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.R;
import cr.ac.ucr.rickmorty.adapters.CharactersAdapter;
import cr.ac.ucr.rickmorty.api.CharacterService;
import cr.ac.ucr.rickmorty.api.RetrofitBuilder;
import cr.ac.ucr.rickmorty.models.Character;
import cr.ac.ucr.rickmorty.models.CharacterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterFragment extends Fragment {

    private final String TAG = "CharacterFragment";
    private AppCompatActivity activity;
    private ArrayList<Character> characters;
    private CharactersAdapter charactersAdapter;

    public CharacterFragment() {
        // Required empty public constructor
    }


    public static CharacterFragment newInstance() {
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: inicializamos variables que no dependen de la vista(layout)

        characters = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TODO: Inicializamos variables que dependen de la vists. TOdos los componentes visuales(TextView, Button, EditText)

        View view = inflater.inflate(R.layout.fragment_character, container, false);

        RecyclerView rvCharacters = view.findViewById(R.id.rv_characters);

        //ArrayList -------> Adapter <-------- RV

        charactersAdapter = new CharactersAdapter(activity);

        rvCharacters.setAdapter(charactersAdapter);
        rvCharacters.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);

        rvCharacters.setLayoutManager(linearLayoutManager);

        charactersAdapter.addCharacters(characters);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: se hace la lógica

        getCharactersInfo();
    }

    private void getCharactersInfo() {
        CharacterService characterService = RetrofitBuilder.createService(CharacterService.class);

        Call<CharacterResponse> response = characterService.getCharacters();

        response.enqueue(new Callback<CharacterResponse>() {

            @Override
            public void onResponse(@NonNull Call<CharacterResponse> call, @NonNull Response<CharacterResponse> response) {
                if(response.isSuccessful()){

                    CharacterResponse characterResponse = response.body();

                    ArrayList<Character> characters = characterResponse.getResults();

                    for(Character character: characters){
                        Log.i(TAG, "Character: " + character.getName());
                    }

                    charactersAdapter.addCharacters(characters);

                } else{
                    Log.e(TAG, "onError: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CharacterResponse> call, @NonNull Throwable t) {
                throw new RuntimeException(t);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}