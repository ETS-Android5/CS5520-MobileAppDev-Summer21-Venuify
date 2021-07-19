package edu.neu.venuify.reservationPage;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.neu.venuify.reservationPageRedo.PastListOfInvitesFragment1;
import edu.neu.venuify.reservationPageRedo.PendingListOfInvitesFragment1;
import edu.neu.venuify.reservationPageRedo.UpcomingListOfInvitesFragment1;

public class TabPagerAdapter1 extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter1(FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position){
        switch(position) {
            case 0:
                return new UpcomingListOfInvitesFragment1();
            case 1:
                return new PendingListOfInvitesFragment1();
            case 3:
                return new PastListOfInvitesFragment1();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
