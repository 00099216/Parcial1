package com.andres00099216.parcial1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andres on 6/5/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    //Variables
    private int contador= 2;
    private List<String> titulos= new ArrayList<>();
    private List<Fragment> fragmento = new ArrayList<>();
    
    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titulos.get(position);
    }

    //Se agrega fragmentos y titulos al Viewpager
    public void addFragment(Fragment fragment, String title) {
        fragmento.add(fragment);
        titulos.add(title);
    }
    //Metodos por defecto
    @Override
    public Fragment getItem(int position) {
        return fragmento.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return contador;
    }
}
