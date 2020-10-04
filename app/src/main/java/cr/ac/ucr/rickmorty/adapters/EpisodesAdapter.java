package cr.ac.ucr.rickmorty.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cr.ac.ucr.rickmorty.R;
import cr.ac.ucr.rickmorty.models.Episode;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Episode> episodes;

    public EpisodesAdapter(Context context, ArrayList<Episode> episodes) {
        this.context = context;
        this.episodes = episodes;
    }

    public EpisodesAdapter(Context context) {
        this.context = context;
        this.episodes = new ArrayList<>();
    }

    @NonNull
    @Override
    public EpisodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_episode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesAdapter.ViewHolder holder, int position) {

        Episode episode = episodes.get(position);

        holder.tvName.setText(episode.getName());
        holder.tvEpisode.setText(String.valueOf(episode.getId()));
        holder.tvDate.setText(episode.getAir_date());
        String season = episode.getEpisode();
        String seasonNub = season.substring(2,3);
        holder.tvSeason.setText(seasonNub);
    }

    @Override
    public int getItemCount() {
        return episodes !=null ? episodes.size() : 0;
    }

    public void addEpisodes(ArrayList<Episode> episodes){
        this.episodes.addAll(episodes);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvEpisode;
        private TextView tvDate;
        private TextView tvSeason;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvEpisode = view.findViewById(R.id.tv_episode);
            tvName = view.findViewById(R.id.tv_name);
            tvDate = view.findViewById(R.id.tv_date);
            tvSeason = view.findViewById(R.id.tv_season);

        }
    }
}
