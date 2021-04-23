package com.probit.parkapp.ui.parkings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Parking;
import com.probit.parkapp.model.Schedule;
import com.probit.parkapp.ui.parkings.ParkingListAdapter;
import com.probit.parkapp.ui.parkings.ParkingListItem;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ParkingListAdapter extends ListAdapter<Parking, ParkingListAdapter.ViewHolder> {


    public ParkingListAdapter(){
        this(new ParkingListDiff());
    }

    protected ParkingListAdapter(@NonNull ParkingListDiff diffCallback) {
        super(diffCallback);
    }

    public static class ParkingListDiff extends DiffUtil.ItemCallback<Parking> {

        @Override
        public boolean areItemsTheSame(@NonNull Parking oldItem, @NonNull Parking newItem) {
            boolean areItemsTheSame;
            areItemsTheSame = false; //((Parking) oldItem).getId().equals(((Parking) newItem).getId());


            return areItemsTheSame;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Parking oldItem, @NonNull Parking newItem) {
            return false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parking_item, parent, false);
                return new ParkingListViewHolder(view);
        }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            ((ParkingListViewHolder)holder).setParking((Parking) getCurrentList().get(position));


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ParkingListViewHolder extends ViewHolder {
        private TextView nombreTV;
        private TextView direccionTV;
        private TextView horarioTV;

        public ParkingListViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTV = itemView.findViewById(R.id.item_parking_valor);
            direccionTV = itemView.findViewById(R.id.item_direccion_valor);
            horarioTV = itemView.findViewById(R.id.item_horario_valor);
        }

        public void setParking(Parking parking) {
            nombreTV.setText(parking.getName());
            direccionTV.setText(parking.getAddress());
            horarioTV.setText(parking.getOpeningHours());
        }
    }

}
