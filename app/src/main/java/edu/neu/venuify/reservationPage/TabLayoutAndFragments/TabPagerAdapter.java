package edu.neu.venuify.reservationPage.TabLayoutAndFragments;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Adapted from Chapter 47, Android Studio 4.1 Development Essentials
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    //constructor that allows the number of pages required to be passed to the class when the
    //instance is created
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        switch(position) {
            case 1:
                return new PendingListOfInvitesFragment();
            case 2:
                return new PastListOfInvitesFragment();
            default:
                return new UpcomingListOfInvitesFragment();
        }
    }

    //returns the count value that was passed through when the object was created
    @Override
    public int getCount() {
        return tabCount;
    }
}
