package com.softvalley.hotelpos.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.softvalley.hotelpos.R;

public class CalendarFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
//        if (navBar!=null)
//        {
//            navBar.setVisibility(View.GONE);
//        }

        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}