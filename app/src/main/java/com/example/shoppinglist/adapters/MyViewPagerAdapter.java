package com.example.shoppinglist.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.shoppinglist.ui.ShoppingListsFragment;

import org.jetbrains.annotations.NotNull;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int ITEMS_COUNT = 2;
    private static final int CURRENT_LISTS_FRAGMENT_POSITION = 0;
    private static final int ARCHIVED_LISTS_FRAGMENT_POSITION = 1;
    private static final String CURRENT_LISTS_FRAGMENT_PAGE_TITLE = "Current Lists";
    private static final String ARCHIVED_LISTS_FRAGMENT_PAGE_TITLE = "Archived lists";

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NotNull
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ShoppingListsFragment();
        Bundle args = new Bundle();
        args.putInt(ShoppingListsFragment.ARGUMENTS_KEY, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEMS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == CURRENT_LISTS_FRAGMENT_POSITION) {
            return CURRENT_LISTS_FRAGMENT_PAGE_TITLE;
        } else {
            return ARCHIVED_LISTS_FRAGMENT_PAGE_TITLE;
        }
    }
}