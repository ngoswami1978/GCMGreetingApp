package com.neerajweb.gcmgreetingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neerajweb.gcmgreetingapp.model.CardModel;
import com.neerajweb.gcmgreetingapp.model.RecycleViewNavigation;
import com.neerajweb.gcmgreetingapp.view.CardContainer;
import com.neerajweb.gcmgreetingapp.view.SimpleCardStackAdapter;

/**
 * Created by Admin on 14/10/2015.
 */
public class base_activity_main extends AppCompatActivity {
    protected RecycleViewNavigation mRecycleViewNavigation;

    /**
     *  Frame layout: Which is going to be used as parent layout for child activity layout.
     *  This layout is protected so that child activity can access this
     *  */
    protected FrameLayout frameLayout;
    /**
     *  This flag is used just to check that launcher activity is called first time
     *  so that we can open appropriate Activity on launch and make list item position selected accordingly.
     * */
    private static boolean isLaunch = true;

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     * */
    protected static int position;



    String TITLES[] = {"Home","Lakshaya Community GuideLines"
            ,"General Information","Maintenance","Leisure Facilities"
            ,"Laundry Facilities","Safety and Security","Common Areas"
            ,"Parking","Homeowner Association","Residents Photo Album"
            ,"Utilities","Lakshaya Events","Important Phone Numbers"
            ,"Maps","Post and comments","Renters"
            ,"Rules & Regulations","Reviews & Testimonials","Contact Us","About Us"
    };
    int ICONS[] = {R.drawable.ic_home
            ,R.drawable.ic_lakshayacommunityguidelines
            ,R.drawable.ic_generalinformation
            ,R.drawable.ic_maintenance
            ,R.drawable.ic_leisurefacilities
            ,R.drawable.ic_laundry
            ,R.drawable.ic_safetyandsecurity
            ,R.drawable.ic_commonareas
            ,R.drawable.ic_parking
            ,R.drawable.ic_homeownerassociation
            ,R.drawable.ic_residentsphotoalbum
            ,R.drawable.ic_utilities
            ,R.drawable.ic_lakshayaevents
            ,R.drawable.ic_importantphonenumbers
            ,R.drawable.ic_maps
            ,R.drawable.ic_postandcomments
            ,R.drawable.ic_renters
            ,R.drawable.ic_rulesandregulations
            ,R.drawable.ic_reviewsandtestimonials
            ,R.drawable.ic_contactus
            ,R.drawable.ic_aboutus
    };

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Neeraj Goswami";
    String EMAIL = "Neeraj.login@gmail.com";
    int PROFILE = R.drawable.neeraj;

    private Toolbar toolbar;                              // Declaring the Toolbar Object

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    ProgressBar progress ;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.base_activity_main);
            //progress = (ProgressBar) findViewById(R.id.device_progress);
            //progress.setVisibility(View.VISIBLE);
            spinner=(ProgressBar)findViewById(R.id.device_progress);
            spinner.setVisibility(View.GONE);

            frameLayout = (FrameLayout)findViewById(R.id.content_frame);

            /* Assinging the toolbar object ot the view and setting the the Action bar to our toolbar*/
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            /* ** **  **  RecycleView Implementation   * ** ** */
            mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

            mRecyclerView.setHasFixedSize(true);
            // Letting the system know that the list objects are of fixed size
            mRecycleViewNavigation = new RecycleViewNavigation();
            mAdapter = new MyAdapter(mRecycleViewNavigation,TITLES,ICONS,NAME,EMAIL,PROFILE);      // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
            mRecyclerView.setAdapter(mAdapter);                             // Setting the adapter to RecyclerView

            mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

            mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

            final GestureDetector mGestureDetector = new GestureDetector(base_activity_main.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            //mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ClickListener() {
            //    @Override
            //    public void onClick(View view, int position) {
            //        Drawer.closeDrawer(mRecyclerView);
            //    }
            //   @Override
            //    public void onLongClick(View view, int position) {
            //   }
            //}));
            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }

                @Override
                public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        Drawer.closeDrawers();

                        //Toast.makeText(drawable_main_activity.this, "The Item Clicked is: " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();

                        if (recyclerView.getChildAdapterPosition(child) == 0) {

                        } else {
                            //mCardContainer.removeAllViewsInLayout();
                            openActivity(recyclerView.getChildPosition(child));
                        }
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

                }
            });
            /* ** **  **  RecycleView Implementation   * ** ** */
            Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view

            mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.drawer_open,R.string.drawer_close){
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                    // open I am not going to put anything here)
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    setTitle(mRecycleViewNavigation.getTitle());

                    // Code here will execute once drawer is closed
                    if (!isLaunch){
                        mRecycleViewNavigation.setTitle(mRecycleViewNavigation.getTitle());
                    }
                }
            }; // Drawer Toggle Object Made
            Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
            mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

            if(isLaunch){
                /**
                 *Setting this flag false so that next time it will not open our first activity.
                 *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
                 *In this case this base activity will always be call when any child activity will launch.
                 */
                isLaunch = false;
                openActivity(0);
            }
        }
        catch   (Exception Ex)
        {
            Log.i("Swipeable Cards", Ex.getMessage());
            Toast.makeText(base_activity_main.this, "Swipeable Cards Error : " + Ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openActivity(int intPosition)
    {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;
        Class fragmentClass = null;

        base_activity_main.position = intPosition; //Setting currently selected position in this field so that it will be available in our child activities.
        try
        {
            frameLayout.removeAllViewsInLayout();
            switch (intPosition) {
                case 0:
                    Log.d("HomeActivity", "Open automatically");
                    //mRecycleViewNavigation.setTitle("Member Gallary");
                    //startActivity(new Intent(this, member_card_list.class));
                    break;
                case 1: //case "Home":
                    Log.d("Home", "onClick ");
                    spinner.setVisibility(View.VISIBLE);
                    mRecycleViewNavigation.setTitle("Home");
                    //fragmentClass = Greeting_registration_activity.class;
                    startActivity(new Intent(this, apartment_registeration_activity.class));
                    break;
                case 2: //case "Lakshaya Community GuideLines":
                    Log.d("GuideLines", "onClick");
                    mRecycleViewNavigation.setTitle("Lakshaya Community GuideLines");
                    startActivity(new Intent(this, Greeting_registration_activity.class));
                    break;
                case 3: //case "General Information":
                    Log.d("General Information", "onClick ");
                    mRecycleViewNavigation.setTitle("General Information");
                    startActivity(new Intent(this, member_card_list.class));
                    break;
                case 4: //case "Maintenance":
                    Log.d("Maintenance", "onClick ");
                    mRecycleViewNavigation.setTitle("Maintenance");
                    break;
                case 5: //case "Leisure Facilities":
                    Log.d("Leisure Facilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Leisure Facilities");
                    break;
                case 6: //case "Laundry Facilities":
                    Log.d("Laundry Facilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Laundry Facilities");
                    break;
                case 7: //case "Safety and Security":
                    Log.d("Safety and Security", "onClick ");
                    mRecycleViewNavigation.setTitle("Safety and Security");
                    break;
                case 8: //case "Common Areas":
                    Log.d("Common Areas", "onClick ");
                    mRecycleViewNavigation.setTitle("Common Areas");
                    break;
                case 9: //case "Parking":
                    Log.d("Parking", "onClick ");
                    mRecycleViewNavigation.setTitle("Parking");
                    break;
                case 10: //case "Homeowner Association":
                    Log.d("Homeowner Association", "onClick ");
                    mRecycleViewNavigation.setTitle("Homeowner Association");
                    break;
                case 11: //case "Residents Photo Album":
                    Log.d("Residents Photo Album", "onClick ");
                    mRecycleViewNavigation.setTitle("Residents Photo Album");
                    break;
                case 12: //case "Utilities":
                    Log.d("Utilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Utilities");
                    break;
                case 13: //case "Lakshaya Events":
                    Log.d("Lakshaya Events", "onClick ");
                    mRecycleViewNavigation.setTitle("Lakshaya Events");
                    break;
                case 14: //case "Important Phone Numbers":
                    Log.d("Important Phone Numbers", "onClick ");
                    mRecycleViewNavigation.setTitle("Important Phone Numbers");
                    break;
                case 15: //case "Maps":
                    Log.d("Maps", "onClick ");
                    mRecycleViewNavigation.setTitle("Maps");
                    break;
                case 16: //case "Post and comments":
                    Log.d("Post and comments", "onClick ");
                    mRecycleViewNavigation.setTitle("Post and comments");
                    break;
                case 17: //case "Renters":
                    Log.d("Renters", "onClick ");
                    mRecycleViewNavigation.setTitle("Renters");
                    break;
                case 18: //case "Rules & Regulations":
                    Log.d("Rules & Regulations", "onClick ");
                    mRecycleViewNavigation.setTitle("Rules & Regulations");
                    break;
                case 19: //case "Reviews & Testimonials":
                    Log.d("Reviews & Testimonials", "onClick ");
                    mRecycleViewNavigation.setTitle("Reviews & Testimonials");
                    break;
                case 20: //case "Contact Us":
                    Log.d("Contact Us", "onClick ");
                    mRecycleViewNavigation.setTitle("Contact Us");
                    break;
                case 21: //case "About Us":
                    Log.d("About Us", "onClick ");
                    mRecycleViewNavigation.setTitle("About Us");
                    break;
                default:
                    break;
            }
            //LayoutInflater inflater = getLayoutInflater();
            //LinearLayout container = (LinearLayout) findViewById(R.id.content_frame);
            //inflater.inflate(R.layout.Base_activity_main, container);

            //fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            //FragmentManager fragmentManager = getSupportFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        }catch(Exception Ex)
        {
            Log.i("openActivity ", Ex.getMessage());
            Toast.makeText(base_activity_main.this, "openActivity  Error : " + Ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        try
        {
            switch (base_activity_main.position) {
                case 0:
                    Log.d("HomeActivity", "Open automatically");
                    mRecycleViewNavigation.setTitle("Member Gallary");
                    break;
                case 1: //case "Home":
                    Log.d("Home", "onClick ");
                    mRecycleViewNavigation.setTitle("Home");
                    break;
                case 2: //case "Lakshaya Community GuideLines":
                    Log.d("GuideLines", "onClick");
                    mRecycleViewNavigation.setTitle("Lakshaya Community GuideLines");
                    break;
                case 3: //case "General Information":
                    Log.d("General Information", "onClick ");
                    mRecycleViewNavigation.setTitle("General Information");
                    break;
                case 4: //case "Maintenance":
                    Log.d("Maintenance", "onClick ");
                    mRecycleViewNavigation.setTitle("Maintenance");
                    break;
                case 5: //case "Leisure Facilities":
                    Log.d("Leisure Facilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Leisure Facilities");
                    break;
                case 6: //case "Laundry Facilities":
                    Log.d("Laundry Facilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Laundry Facilities");
                    break;
                case 7: //case "Safety and Security":
                    Log.d("Safety and Security", "onClick ");
                    mRecycleViewNavigation.setTitle("Safety and Security");
                    break;
                case 8: //case "Common Areas":
                    Log.d("Common Areas", "onClick ");
                    mRecycleViewNavigation.setTitle("Common Areas");
                    break;
                case 9: //case "Parking":
                    Log.d("Parking", "onClick ");
                    mRecycleViewNavigation.setTitle("Parking");
                    break;
                case 10: //case "Homeowner Association":
                    Log.d("Homeowner Association", "onClick ");
                    mRecycleViewNavigation.setTitle("Homeowner Association");
                    break;
                case 11: //case "Residents Photo Album":
                    Log.d("Residents Photo Album", "onClick ");
                    mRecycleViewNavigation.setTitle("Residents Photo Album");
                    break;
                case 12: //case "Utilities":
                    Log.d("Utilities", "onClick ");
                    mRecycleViewNavigation.setTitle("Utilities");
                    break;
                case 13: //case "Lakshaya Events":
                    Log.d("Lakshaya Events", "onClick ");
                    mRecycleViewNavigation.setTitle("Lakshaya Events");
                    break;
                case 14: //case "Important Phone Numbers":
                    Log.d("Important Phone Numbers", "onClick ");
                    mRecycleViewNavigation.setTitle("Important Phone Numbers");
                    break;
                case 15: //case "Maps":
                    Log.d("Maps", "onClick ");
                    mRecycleViewNavigation.setTitle("Maps");
                    break;
                case 16: //case "Post and comments":
                    Log.d("Post and comments", "onClick ");
                    mRecycleViewNavigation.setTitle("Post and comments");
                    break;
                case 17: //case "Renters":
                    Log.d("Renters", "onClick ");
                    mRecycleViewNavigation.setTitle("Renters");
                    break;
                case 18: //case "Rules & Regulations":
                    Log.d("Rules & Regulations", "onClick ");
                    mRecycleViewNavigation.setTitle("Rules & Regulations");
                    break;
                case 19: //case "Reviews & Testimonials":
                    Log.d("Reviews & Testimonials", "onClick ");
                    mRecycleViewNavigation.setTitle("Reviews & Testimonials");
                    break;
                case 20: //case "Contact Us":
                    Log.d("Contact Us", "onClick ");
                    mRecycleViewNavigation.setTitle("Contact Us");
                    break;
                case 21: //case "About Us":
                    Log.d("About Us", "onClick ");
                    mRecycleViewNavigation.setTitle("About Us");
                    break;
                default:
                    break;
            }
        }
        catch(Exception Ex)
        {
            Log.i("onCreateOptionsMenu ", Ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
