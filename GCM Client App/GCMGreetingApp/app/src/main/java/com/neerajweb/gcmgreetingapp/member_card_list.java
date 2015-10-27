package com.neerajweb.gcmgreetingapp;

import android.content.res.Resources;
import android.util.Log;
import android.os.Bundle;
import android.widget.Toast;

import com.neerajweb.gcmgreetingapp.model.CardModel;
import com.neerajweb.gcmgreetingapp.view.CardContainer;
import com.neerajweb.gcmgreetingapp.view.SimpleCardStackAdapter;
import com.neerajweb.gcmgreetingapp.helper.AlertDialogManager;
import com.neerajweb.gcmgreetingapp.helper.ConnectionDetector;



/**
 * Created by Admin on 21/10/2015.
 */
public class member_card_list extends base_activity_main {
    private CardContainer mCardContainer;

    // Connection detector
    ConnectionDetector cd;
    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.member_card_list);
        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/
        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.member_card_list, frameLayout);
        try
        {
            cd = new ConnectionDetector(getApplicationContext());

            // Check for internet connection
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present
                alert.showAlertDialog(member_card_list.this, "Internet Connection Error",
                        "Please connect to working Internet connection", false);
                // stop executing code by return
                return;
            }
            /*Card Implementation Start from here        */
            mCardContainer = (CardContainer) findViewById(R.id.layoutview);
            Resources r = getResources();

            SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

            adapter.add(new CardModel("Neeraj Goswami", "F3 New Delhi", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Neeraj Goswami", "F2 New Delhi", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Pradeep", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Ankush", "Description goes here", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Kunal Dhawan", "Description goes here", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Neeraj Goswami", "F3 New Delhi", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Neeraj Goswami", "F3 New Delhi", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Neeraj Goswami", "F2 New Delhi", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Pradeep", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Ankush", "Description goes here", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.neeraj)));
            adapter.add(new CardModel("Neeraj Goswami", "F2 New Delhi", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Pradeep", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.neeraj)));
            adapter.add(new CardModel("Neeraj Goswami", "F2 New Delhi", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Pradeep", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Neeraj Goswami", "F3 New Delhi", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Neeraj Goswami", "F2 New Delhi", r.getDrawable(R.drawable.picture2)));
            adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
            adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
            adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.neeraj)));


            CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1));
            cardModel.setOnClickListener(new CardModel.OnClickListener() {
                @Override
                public void OnClickListener() {
                    Log.i("Swipeable Cards", "I am pressing the card");
                }
            });

            cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
                @Override
                public void onLike() {
                    Log.i("Swipeable Cards", "I like the card");
                }

                @Override
                public void onDislike() {
                    Log.i("Swipeable Cards", "I dislike the card");
                }
            });

            adapter.add(cardModel);
            mCardContainer.setAdapter(adapter);
            /*end Card Implementation Start from here        */
        }
        catch(Exception Ex)
        {
            Log.i("SwipeableCards ", Ex.getMessage());
            Toast.makeText(member_card_list.this, "member_card_listOnCreate  Error : " + Ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}
