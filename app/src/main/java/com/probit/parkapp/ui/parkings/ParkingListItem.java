package com.probit.parkapp.ui.parkings;

import com.probit.parkapp.ui.schedules.SchedulesListItem;

public interface ParkingListItem {
    default boolean isSectionTitle() {
        return false;
    }
}

class ParkingListTitle implements ParkingListItem {
    private String title;
    @Override
    public boolean isSectionTitle() {
        return false;
    }

    public ParkingListTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
