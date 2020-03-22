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
            //detach current fragment before adding new fragment
            //detaching instead of replacing so that we maintain the state of the fragment
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

    public void attachFragment(Fragment fragment, int layout) {
        Fragment currentFragment = fragmentManager.findFragmentById(layout);
        if(currentFragment != null) {
            //detach current fragment before attaching new fragment
            //detaching instead of replacing so that we maintain the state of the fragment
            fragmentManager
                    .beginTransaction()
                    .detach(currentFragment)
                    .attach(fragment)
                    .commitNow();
        }
        else{
            fragmentManager
                    .beginTransaction()
                    .attach(fragment)
                    .commitNow();
        }
    }


    public boolean isFragmentAvailableInBackStack(String tag) {
        return getFragment(tag) != null;
    }

    public Fragment getFragment(String tag){
        return fragmentManager.findFragmentByTag(tag);
    }


    public static void resetFragmentTransitionManager() {
        fragmentTransitionManager = null;
    }

    public void removeAllFragments() {
        for (Fragment fragment: fragmentManager.getFragments()) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNowAllowingStateLoss();
        }

        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        fragmentManager.executePendingTransactions();
    }
}
