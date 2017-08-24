package it.unibo.mobileuser.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import it.unibo.mobileuser.R;

import java.util.ArrayList;

/**
 * Adapter that provides a binding from a data set of CardView to view that is displayed withing a RecyclerView.
 */
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final ArrayList<CardViewModel> dataSet;
    private final View.OnClickListener listener;

    /**
     * Constructor of adapter to set data set and listener.
     *
     * @param dataSet  an array list of CardView
     * @param listener a listener for the CardView
     */
    public RecyclerViewAdapter(final ArrayList<CardViewModel> dataSet, final View.OnClickListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        view.setOnClickListener(this.listener);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int listPosition) {

        final TextView titleTextView = holder.getTitleTextView();
        final ImageView iconImageView = holder.getIconImageView();

        titleTextView.setText(this.dataSet.get(listPosition).getTitle());
        iconImageView.setImageResource(this.dataSet.get(listPosition).getIcon());
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    /**
     * Static class that puts item in the correct place within the RecyclerView.
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final ImageView iconImageView;

        /**
         * Constructor that sets items of view.
         *
         * @param view the view of activity
         */
        public RecyclerViewHolder(final View view) {
            super(view);
            this.titleTextView = (TextView) view.findViewById(R.id.cardview_title);
            this.iconImageView = (ImageView) view.findViewById(R.id.cardview_icon);
        }

        /**
         * Return the text view of the title of CardView.
         *
         * @return the text view of title
         */
        public TextView getTitleTextView() {
            return this.titleTextView;
        }

        /**
         * Return the image view of the icon of CardView.
         *
         * @return the image view of icon
         */
        public ImageView getIconImageView() {
            return this.iconImageView;
        }
    }
}
