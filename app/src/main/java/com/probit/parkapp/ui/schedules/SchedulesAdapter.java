package com.probit.parkapp.ui.schedules;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.probit.parkapp.R;
import com.probit.parkapp.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SchedulesAdapter extends ListAdapter<SchedulesListItem, SchedulesAdapter.ViewHolder> {

    private final int TITLE_VIEW_TYPE = 1;
    private final int SCHEDULE_VIEW_TYPE = 2;

    private OnDeleteCallback onDeleteCallback;

    public interface OnDeleteCallback {
        void run(int position);
    }

    @Override
    public void submitList(@Nullable List<SchedulesListItem> list) {

        super.submitList(list);
        this.notifyDataSetChanged();
    }

    public SchedulesAdapter(OnDeleteCallback onDeleteCallback){
        this( onDeleteCallback ,new SchedulesDiff());
    }

    protected SchedulesAdapter(OnDeleteCallback onDeleteCallback, @NonNull SchedulesDiff diffCallback) {
        super(diffCallback);
        this.onDeleteCallback = onDeleteCallback;
    }

    public static class SchedulesDiff extends DiffUtil.ItemCallback<SchedulesListItem> {

        @Override
        public boolean areItemsTheSame(@NonNull SchedulesListItem oldItem, @NonNull SchedulesListItem newItem) {
            boolean areItemsTheSame;
            if (oldItem.isSectionTitle() || newItem.isSectionTitle()){
                areItemsTheSame = false;
            } else {
                areItemsTheSame = ((Schedule) oldItem).getId().equals(((Schedule) newItem).getId());
            }

            return areItemsTheSame;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SchedulesListItem oldItem, @NonNull SchedulesListItem newItem) {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getCurrentList().get(position).isSectionTitle()) return TITLE_VIEW_TYPE;
        else return SCHEDULE_VIEW_TYPE;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view;
        switch (viewType) {
            case TITLE_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row_schedules_title, parent, false);
                return new TitleViewHolder(view);
            default: // SCHEDULE_VIEW_TYPE
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_row_schedule, parent, false);
                return new ScheduleViewHolder(view);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getCurrentList().get(position).isSectionTitle()) {
            String title = ((SchedulesListTitle)getCurrentList().get(position)).getTitle();
            ((TitleViewHolder) holder).setTitle(title);
        } else { // Schedule
            ((ScheduleViewHolder)holder).setSchedule((Schedule) getCurrentList().get(position));
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ScheduleViewHolder extends ViewHolder {
        private TextView parkingTV;
        private TextView checkinTV;
        private TextView checkoutTV;
        private TextView costTV;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            parkingTV = itemView.findViewById(R.id.parking_value);
            checkinTV = itemView.findViewById(R.id.checkin_value);
            checkoutTV = itemView.findViewById(R.id.checkout_value);
            costTV = itemView.findViewById(R.id.cost_value);

            itemView.setOnLongClickListener(view -> {
                int position = this.getAdapterPosition();

                new AlertDialog.Builder(view.getContext())
                        .setTitle("??Est?? seguro que quiere eliminar esta reserva?")
                        .setCancelable(true)
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onDeleteCallback.run(position);
                                Toast.makeText(view.getContext(), "Eliminando reserva...", Toast.LENGTH_LONG).show();
                                dialog.dismiss(); // COMENTAR CUANDO SE COMENTE EL MENSAJE Y SE DESCOMENTE createSchedule() ya que est?? dentro de createSchedule

                            }
                        }).show();
                return true;
            });
        }

        public void setSchedule(Schedule schedule) {
            parkingTV.setText(schedule.getParkingName());

            // Formatting dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("E d, M - HH:mm", Locale.getDefault());
            String checkin = dateFormat.format(schedule.getCheckinDate());
            String checkout = dateFormat.format(schedule.getCheckoutDate());
            checkinTV.setText(checkin);
            checkoutTV.setText(checkout);

            costTV.setText(schedule.getCost().toString());
        }
    }

    class TitleViewHolder extends ViewHolder {
        private TextView titleTV;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.title_textview);
        }

        public void setTitle(String title) {
            titleTV.setText(title);
        }
    }
}
