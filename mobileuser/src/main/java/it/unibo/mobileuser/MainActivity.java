package it.unibo.mobileuser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import it.unibo.mobileuser.gps.GpsActivityImpl;
import it.unibo.mobileuser.home.CardViewModel;
import it.unibo.mobileuser.home.RecyclerViewAdapter;

import java.util.ArrayList;

import static it.unibo.mobileuser.utils.Utils.getDrawableArrayByResources;
import static it.unibo.mobileuser.utils.Utils.getStringArrayByResources;

public class MainActivity extends GpsActivityImpl {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(false);
        goToSplashActivity();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager llm = new GridLayoutManager(this, 2);
        llm.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        final ArrayList<CardViewModel> data = new ArrayList<>();
        for (int i = 0; i < getStringArrayByResources(this, R.array.card_titles).length; i++) {
            data.add(new CardViewModel(
                    getStringArrayByResources(this, R.array.card_titles)[i],
                    getDrawableArrayByResources(this, R.array.card_icons).getResourceId(i, -1),
                    getStringArrayByResources(this, R.array.card_activities)[i]
            ));
        }

        final View.OnClickListener onClickListener = new CardOnClickListener(this, recyclerView);
        final RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> adapter = new RecyclerViewAdapter(data, onClickListener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbar(false);
        goToSplashActivity();
    }

    /**
     * Go to Splash Activity because the user is no longer logged.
     */
    private void goToSplashActivity() {
        if (!checkIfUserIsLogged()) {
            final Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Static class that allows to invoke a callback when a CardView is clicked.
     */
    public static class CardOnClickListener implements View.OnClickListener {

        private final Context context;
        private final RecyclerView recyclerView;

        /**
         * Constructor.
         *
         * @param context      the context of activity
         * @param recyclerView the recycler view used
         */
        private CardOnClickListener(final Context context, final RecyclerView recyclerView) {
            this.context = context;
            this.recyclerView = recyclerView;
        }

        @Override
        public void onClick(final View view) {
            openActivity(view);
        }

        /**
         * Get the selected item and identify the activity to be started.
         *
         * @param view the view of activity
         */
        private void openActivity(final View view) {
            final int selectedItemPosition = this.recyclerView.getChildAdapterPosition(view);
            final RecyclerView.ViewHolder viewHolder =
                    this.recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            final String selectedCardTitle = (String) ((TextView) viewHolder.itemView.findViewById(R.id.cardview_title)).getText();
            String selectedCardActivity = null;
            for (int i = 0; i < getStringArrayByResources(this.context, R.array.card_titles).length; i++) {
                if (selectedCardTitle.equals(getStringArrayByResources(this.context, R.array.card_titles)[i])) {
                    selectedCardActivity = getStringArrayByResources(this.context, R.array.card_activities)[i];
                }
            }
            try {
                view.getContext().startActivity(new Intent(view.getContext(), Class.forName(selectedCardActivity)));
            } catch (final ClassNotFoundException ex) {
                Toast.makeText(this.context, view.getContext().getResources().getString(R.string.functionality_not_active), Toast.LENGTH_SHORT).show();
            }
        }
    }

}

