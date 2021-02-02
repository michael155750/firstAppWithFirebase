package com.example.firstappwithfirebase.UI;




import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.location.Geocoder;


import com.example.firstappwithfirebase.Models.Travel;
import com.example.firstappwithfirebase.Models.UserLocation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstappwithfirebase.R;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @brief the code behind class which aimed to travel adding .
 */
public class AddTravelActivity extends AppCompatActivity {


    private TravelViewModel travelViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        setContentView(R.layout.activity_add_travel_activity);
        //       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //      CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        toolBarLayout.setTitle(getTitle());

        LiveData<Boolean> isSuccess = travelViewModel.isTravelSuccess();
        isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean element) {
                if (element)
                    Toast.makeText(AddTravelActivity.this,
                            "The travel had inserted successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddTravelActivity.this,
                            "The travel had not inserted", Toast.LENGTH_LONG).show();

            }
        });


        Button button = (Button) findViewById(R.id.Sending);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendingTravel();
                //checking code
//                try {
//                    checkingSendingTravel();
//                }catch (Exception e){}
            }
        });


    }



    /**
     * sending the suitable value of what the user input.
     */

    public void sendingTravel() {
        try {
            Location pickupLocation = null;
            Geocoder geocoderPickup = new Geocoder(getBaseContext());

            EditText name = (EditText) findViewById(R.id.name);

            EditText phone = (EditText) findViewById(R.id.phone);

            EditText email = (EditText) findViewById(R.id.email);

            String pickupAddress= ((EditText) findViewById(R.id.pickUp)).getText().toString();
            List<Address> l1 = geocoderPickup.getFromLocationName(pickupAddress, 1);
            if (!l1.isEmpty()) {
                Address temp = l1.get(0);
                pickupLocation= new Location("pickupLocation");
                pickupLocation.setLatitude(temp.getLatitude());
                pickupLocation.setLongitude(temp.getLongitude());
            } else {
                Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();
                return;
            }

            String numOfPassengersString = ((EditText) findViewById(R.id.numOfPassengers)).getText().toString();

            Integer numOfPassengers = Integer.parseInt(numOfPassengersString);

            DatePicker travelDatePicker = (DatePicker) findViewById(R.id.travelDate);
            Date travelDate = new Date(travelDatePicker.getYear(),travelDatePicker.getMonth(),travelDatePicker.getDayOfMonth());

            DatePicker arrivalDatePicker = (DatePicker) findViewById(R.id.arrivalDate);
            Date arrivalDate = new Date(arrivalDatePicker.getYear(),arrivalDatePicker.getMonth(),arrivalDatePicker.getDayOfMonth());

            Location destination = null;
            Geocoder geocoder2 = new Geocoder(getBaseContext());
            String destAddress =  ((EditText) findViewById(R.id.destAddresses)).getText().toString();
            List<Address> l = geocoder2.getFromLocationName(destAddress, 1);
            if (!l.isEmpty()) {
                Address temp = l.get(0);
                destination = new Location("destination");
                destination.setLatitude(temp.getLatitude());
                destination.setLongitude(temp.getLongitude());
            } else {
                Toast.makeText(this, "4:Unable to understand address", Toast.LENGTH_LONG).show();

            }

            RadioGroup radioGroup = findViewById(R.id.isUnbreakAble);
            int radioId = radioGroup.getCheckedRadioButtonId();
            Boolean isSafeGuarded = radioId == 1 ? true : false;

            RadioGroup radioGroup1 = findViewById(R.id.isChildren);
            int radioId2 = radioGroup1.getCheckedRadioButtonId();
            Boolean childrenInvolved = radioId2 == 1 ? true : false;

            /*travelViewModel.addNewTravel(new Travel(name.getText().toString(), phone.getText().toString(), email.getText().toString(),
                    UserLocation.convertFromLocation(pickupLocation), numOfPassengers,
                    travelDate, arrivalDate,
                    isSafeGuarded, childrenInvolved, UserLocation.convertFromLocation(destination)));*/
            Travel travel = new Travel();
            travel.setClientName(name.getText().toString());
            travel.setClientPhone(phone.getText().toString());
            travel.setClientEmail( email.getText().toString());
            travel.setPickupAddress(UserLocation.convertFromLocation(pickupLocation));
            travel.setNumOfPassengers(numOfPassengers);
            travel.travelDateTypeSetter(travelDate);
            travel.arrivalDateTypeSetter(arrivalDate);
            travel.setChildrenTransportation(childrenInvolved);
            travel.setDestAddress(UserLocation.convertFromLocation(destination));
            travelViewModel.addNewTravel(travel);
        }catch (IOException e) {
            Toast.makeText(this, "5:Unable to understand address. Check Internet connection.", Toast.LENGTH_LONG).show();
            return;
        }


        catch (Exception e) {
            TextView textView = findViewById(R.id.message);
            final String error = "System error occurred";
            final String invalidClientPhone = "invalid client phone";
            final String invalidEmail = "invalid Email";

            if (e.equals(new NullPointerException("illegal phone number format")))
                textView.setText(invalidClientPhone);

            else if (e.equals(new IllegalArgumentException("illegal email format")))
                textView.setText(invalidEmail);
            else
                textView.setText(error);
        }

    }

}
