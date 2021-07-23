package edu.neu.venuify.reservationPage;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position){
        switch(position) {
            case 0:
                return new UpcomingListOfInvitesFragment();
            case 1:
                return new PendingListOfInvitesFragment();
            case 3:
                return new PastListOfInvitesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
