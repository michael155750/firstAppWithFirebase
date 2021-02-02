package com.example.firstappwithfirebase.Repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.firstappwithfirebase.Models.Travel;
import com.example.firstappwithfirebase.Models.TravelDataSource;


public class TravelRepository {
    TravelDataSource travelDataSource;

    public TravelRepository(){
        travelDataSource = TravelDataSource.getInstance();
    }

    public void addTravel(Travel travel){
        travelDataSource.addTravel(travel);
    }

    public LiveData<Boolean> isTravelSuccess() {
        return travelDataSource.getIsSuccess();
    }

}
