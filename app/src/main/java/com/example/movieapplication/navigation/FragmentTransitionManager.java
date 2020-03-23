package com.example.movieapplication.navigation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentTransitionManager {

    private static FragmentTransitionManager fragmentTransitionManager = null;
    private FragmentManager fragmentManager;

    private FragmentTransitionManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    public static FragmentTransitionManager getFragmentManager(FragmentManager fragmentManager) {
        if (fragmentTransitionManager == null) {
            fragmentTransitionManager = new FragmentTransitionManager(fragmentManager);
        }
        return fragmentTransitionManager;
    }


    public void addFragment(Fragment fragment, String tag, int layout) {

        Fragment currentFragment = fragmentManager.findFragmentById(layout);

        if(currentFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .detach(currentFragment)
                    .add(layout, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }else{
            fragmentManager
                    .beginTransaction()
                    .add(layout, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

}
