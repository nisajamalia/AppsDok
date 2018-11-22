package com.nisa.bismillah_dokter.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nisa.bismillah_dokter.R;
import com.nisa.bismillah_dokter.tab_layout.Spesialis_bekam;
import com.nisa.bismillah_dokter.tab_layout.spesialis_anak;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_dokter extends Fragment {
    TabLayout tab;
    ViewPager viewPager;


    public fragment_dokter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_fragment_dokter, null);
        tab = (TabLayout) V.findViewById(R.id.Ltab);
        viewPager = (ViewPager) V.findViewById(R.id.pagerI);

        tab.addTab(tab.newTab().setText("Spesialis Anak"));
        tab.addTab(tab.newTab().setText("Spesialis Bekam"));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        return V;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new spesialis_anak();
                //ini yang tab menu yang pas back gak bisa, diambil dari  kelas
            } else if (position == 1) {
                return new Spesialis_bekam();
                //ini yang tab menu yang pas back gak bisa, diambil dari  kelas
            }
            return null;
        }


        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Spesialis Anak";
            } else {
                return "Spesialis Bekam";
            }
        }
    }

}
