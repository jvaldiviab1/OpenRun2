package com.jvaldiviab.openrun2.util;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

@Navigator.Name("keep_state_fragment")
public class KeepStateNavigation extends FragmentNavigator {
    private Context context;
    private FragmentManager fragmentManager;
    private int containerId;

    public KeepStateNavigation(@NonNull Context context, @NonNull FragmentManager fragmentManager, int containerId) {
        super(context, fragmentManager, containerId);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @androidx.annotation.Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination,
                                   @androidx.annotation.Nullable Bundle args,
                                   @androidx.annotation.Nullable NavOptions navOptions,
                                   @androidx.annotation.Nullable Navigator.Extras navigatorExtras) {
        String tag = String.valueOf(destination.getId());

        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();

        boolean initialNavigate = false;
        Fragment currentFragment = this.fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);

        } else {
            initialNavigate = true;
        }

        Fragment fragment = this.fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            String className = destination.getClassName();
            fragment = this.fragmentManager.getFragmentFactory().instantiate(this.context.getClassLoader(), className);
            fragmentTransaction.add(this.containerId, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
        return initialNavigate ? destination : null;
    }

}
