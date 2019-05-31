package poi.ivyphlox.com.poivender.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import poi.ivyphlox.com.poivender.tabFragments.AllMemberFragment;
import poi.ivyphlox.com.poivender.tabFragments.InviteFragment;
import poi.ivyphlox.com.poivender.tabFragments.MemberFragment;


/**
 * Created by dirgha-dev-05 on 5/4/18.
 */

public class ViewPagerAdpater extends FragmentPagerAdapter {

    private Bundle mExtras;

    public ViewPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    /**
     * Here we are implementing Tab view
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        try {
            if (position == 0) {
                AllMemberFragment allMenuFragment = new AllMemberFragment();
                return allMenuFragment;
            }
            if (position == 1) {
                MemberFragment offersMenuFragment = new MemberFragment();
                return offersMenuFragment;
            }
            if (position == 2) {
               InviteFragment offersMenuFragment = new InviteFragment();
                return offersMenuFragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if (position == 0) {
            title = "ALL";
        }
        if (position == 1) {
            title = "Member";
        }
        if (position == 2) {
            title = "Invite";
        }

        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
