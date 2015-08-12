package com.example.sunnygurnani.multimenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnFragmentInteractionListener {

    private ShareActionProvider mShareActionProvider;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        switch (position)
        {
            //Home
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Home.newInstance(position))
                        .commit();
                break;
            //Map
            case 1:
                Context context= this;
                Intent intent=new Intent(context,Geolocation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            //Developers
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, DevelopersProfile.newInstance(position ))
                        .commit();
                break;
            default:


        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_home);
                break;
            case 1:
                mTitle = getString(R.string.title_map);

                break;
            case 2:
                mTitle = getString(R.string.title_developer_profile);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            intent = new Intent(this, EmergencyContactActivity.class);
        }
        else if (id == R.id.action_about_us){
            aboutMenuItem();

        }
        if (intent != null) {
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentAttach(int sectionNumber) {
        onSectionAttached(sectionNumber);
    }

    private void aboutMenuItem(){
        new AlertDialog.Builder(this).setTitle("About")
                .setMessage("Do you often find yourself in emergency situations?Do you walk alone on campus, ride subway at night or travel abroad to a new place? eHelp is an app to quickly place an emergency call in case of emergency situations and can alert someone of your whereabouts - this app will simplify explaining exactly where you are. Help can come faster and within GPS accuracy.\n" +
                                "Define a phone number and when you tap the big red button, the text message will be sent out to the added recipient along with your GPS/Network location with time and date.\n" +
                                "\n" +
                                "It also gets you nearby places like hospitals etc. on a map and you can get the direction to those places with a single click.\n" +
                                "\n" +
                                "Disclaimer: It won't work without reception or connectivity."
                )
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        // Create the share Intent
        String playStoreLink = "https://play.google.com/store/apps/details?id=" +
                getPackageName();
        String yourShareText = "Install this app " + playStoreLink;
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain").setText(yourShareText).getIntent();
        // Set the share Intent
        mShareActionProvider.setShareIntent(shareIntent);
        return true;
    }

}
