package com.example.firstappwithfirebase.UI;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.firstappwithfirebase.Models.Travel;
import com.example.firstappwithfirebase.Repository.TravelRepository;

public class TravelViewModel extends ViewModel {
    TravelRepository travelRepository;
    public TravelViewModel(){
        travelRepository = new TravelRepository();
    }
    public void addNewTravel(Travel travel){
        travelRepository.addTravel(travel);
    }

    public LiveData<Boolean> isTravelSuccess() {
        return travelRepository.isTravelSuccess();
    }

}
