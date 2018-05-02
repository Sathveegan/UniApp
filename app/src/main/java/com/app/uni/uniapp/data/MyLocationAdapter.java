package com.app.uni.uniapp.data;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.uni.uniapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathveegan on 02-May-18.
 */

public class MyLocationAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<MyLocation> myLocationArrayList ;

    public MyLocationAdapter(Context context, ArrayList<MyLocation> myLocations) {
        super(context, R.layout.layout_mylocation_list, myLocations);
        this.mContext = context;
        this.myLocationArrayList = myLocations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final MyLocation myLocation = myLocationArrayList.get(position);

        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.layout_mylocation_list, null);
        }

        TextView date = (TextView) view.findViewById(R.id.list_mylocation_date);
        TextView time = (TextView) view.findViewById(R.id.list_mylocation_time);
        TextView location = (TextView) view.findViewById(R.id.list_mylocation_location);

        Geocoder geocoder=new Geocoder(mContext);

        try {
            List<Address> addressList=geocoder.getFromLocation(myLocation.getLatitude(),myLocation.getLongitude(),1);

            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();

                if (!locality.isEmpty() && !country.isEmpty())
                    location.setText(": "+locality + "  " + country);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        date.setText(": "+format.format(myLocation.getDate()));
        time.setText(": "+myLocation.getTimeHours()+" : "+myLocation.getTimeMinute());

        return view;
    }

}
