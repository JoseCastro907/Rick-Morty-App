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
import cr.ac.ucr.rickmorty.models.Location;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Location> locations;

    public LocationsAdapter(Context context, ArrayList<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    public LocationsAdapter(Context context) {
        this.context = context;
        this.locations = new ArrayList<>();
    }

    @NonNull
    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsAdapter.ViewHolder holder, int position) {

        Location location = locations.get(position);

        holder.tvName.setText(location.getName());
        holder.tvType.setText(location.getType());
        holder.tvDimension.setText(location.getDimension());
        holder.tvResidents.setText(String.valueOf(location.getResidents().size()));


    }

    @Override
    public int getItemCount() {
        return locations !=null ? locations.size() : 0;
    }

    public void addLocations(ArrayList<Location> locations){
        this.locations.addAll(locations);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView tvName;
        private  TextView tvType;
        private  TextView tvDimension;
        private  TextView tvResidents;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            tvType = view.findViewById(R.id.tv_type);
            tvDimension = view.findViewById(R.id.tv_dimension);
            tvResidents = view.findViewById(R.id.tv_residents);


        }
    }
}
