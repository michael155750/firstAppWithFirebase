package com.example.firstappwithfirebase.Models;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

/**
 * @brief class represents a data source of travel
 */
public class TravelDataSource {


    private MutableLiveData<Boolean> isSuccess= new MutableLiveData<>();
    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }



    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("ExistingTravels");

    public TravelDataSource() {
        //travelsList = new LinkedList<Travel>();
    }

    private static TravelDataSource instance;

    public static TravelDataSource getInstance() {
        if (instance == null)
            instance = new TravelDataSource();
        return instance;
    }


    public void addTravel(Travel travel) {
        String id = travels.push().getKey();
        travel.setTravelId(id);
        travels.child(id).setValue(travel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                isSuccess.setValue(true);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isSuccess.setValue(false);
            }
        });
    }
}
