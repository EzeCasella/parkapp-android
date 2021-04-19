package com.probit.parkapp.ui.schedules;


import com.probit.parkapp.model.Schedule;

public interface SchedulesListItem {
    default boolean isSectionTitle() {
        return false;
    }
}

class SchedulesListTitle implements  SchedulesListItem {
    private String title;
    @Override
    public boolean isSectionTitle() {
        return true;
    }

    public SchedulesListTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
