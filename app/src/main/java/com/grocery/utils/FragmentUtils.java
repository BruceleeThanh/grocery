package com.grocery.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by PC on 1/15/2017.
 */

public class FragmentUtils {
    private FragmentManager fragmentManager = null;
    private int parentContainer;
    private Fragment currentFragment = null;

    public FragmentUtils(FragmentManager fragmentManager, int parentContainer) {
        this.fragmentManager = fragmentManager;
        this.parentContainer = parentContainer;
    }

    public void replaceFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = beginTransaction();
        if (fragment.isHidden()) {
            transaction.show(fragment);
        } else {
            transaction.replace(parentContainer, fragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFragment1(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = beginTransaction();
        transaction.replace(parentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void addToFragment(Fragment fragment) {
        currentFragment = fragment;
        FragmentTransaction transaction = beginTransaction();
        if (fragment.isHidden()) {
            transaction.show(fragment);
        } else {
            transaction.add(parentContainer, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public void changeFragment(Fragment show) {
        FragmentTransaction transaction = beginTransaction();
        transaction.hide(getCurrentFragment());
        currentFragment = show;
        if (show.isHidden() || show.isAdded()) {
            transaction.show(show);
        } else {
            transaction.add(parentContainer, show);
        }
        transaction.commit();
    }


    public void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    private FragmentTransaction beginTransaction() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        return fragmentTransaction;
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = beginTransaction();
        transaction.add(parentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean onBackKeyPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
