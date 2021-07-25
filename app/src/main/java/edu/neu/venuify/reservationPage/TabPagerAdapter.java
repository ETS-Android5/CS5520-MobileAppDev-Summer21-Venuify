package edu.neu.venuify.reservationPage;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        switch(position) {
            case 2:
                return new PendingListOfInvitesFragment();
            case 3:
                return new PastListOfInvitesFragment();
            default:
                return new UpcomingListOfInvitesFragment();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
