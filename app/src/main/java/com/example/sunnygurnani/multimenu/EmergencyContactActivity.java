package com.example.sunnygurnani.multimenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class EmergencyContactActivity extends Activity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    ContactStore contactStore = null;

    public void saveContactStore() {
        try {
            contactStore.saveToFile(this);
        } catch (IOException e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        if (contactStore == null)
            contactStore = new ContactStore(this);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(), contactStore);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        ContactStore mContactStore;

        public SectionsPagerAdapter(FragmentManager fm, ContactStore contactStore) {
            super(fm);
            mContactStore = contactStore;
        }

        Contact getContactBySectionNumber(int sectionNumber) {
            if (sectionNumber == 0) {
                return mContactStore.getContact_1();
            } else
                return mContactStore.getContact_2();
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(getContactBySectionNumber(position));
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Primary Contact";
                case 1:
                    return "Secondary Contact";
            }
            return null;
        }
    }

    /*A placeholder fragment containing a simple view.*/
    public static class PlaceholderFragment extends Fragment {
        /* The fragment argument representing the section number for this fragment.*/

        View rootView = null;
        private static final String ARG_SECTION_NUMBER = "section_number";
        int sectionNumber;
        EmergencyContactActivity parentActivity = null;
        Contact mContact;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Contact contact) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            fragment.mContact = contact;
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            rootView = inflater.inflate(R.layout.fragment_emergency_contact, container, false);

            setContactInView(mContact);
            ((Button) rootView.findViewById(R.id.save_user)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContactFromView(mContact);
                    parentActivity.saveContactStore();
                    Toast.makeText(getActivity(), "Your contact has been saved", Toast.LENGTH_LONG).show();
                    parentActivity.onBackPressed();
                }
            });

            return rootView;
        }

        void setContactInView(Contact contact) {
            EditText name = (EditText) rootView.findViewById(R.id.name_user);
            name.setText(contact.getFirstName());


            EditText phoneNumber = (EditText) rootView.findViewById(R.id.phone_user);
            phoneNumber.setText(contact.getPhoneNumber());

        }

        void getContactFromView(Contact contact) {
            EditText name = (EditText) rootView.findViewById(R.id.name_user);
            contact.setFirstName(name.getText().toString());

            EditText phoneNumber = (EditText) rootView.findViewById(R.id.phone_user);
            contact.setPhoneNumber(phoneNumber.getText().toString());

        }

        @Override
        public void onAttach(final Activity activity) {
            super.onAttach(activity);
            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            parentActivity = (EmergencyContactActivity) activity;

        }
    }

}
