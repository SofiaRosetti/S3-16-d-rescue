package it.unibo.mobileuser.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import it.unibo.drescue.model.Alert;
import it.unibo.mobileuser.R;

import java.util.List;

/**
 * Class that is responsible for creating a view for each alert and
 * for providing access to every data item.
 */
public class AlertAdapter extends ArrayAdapter<Alert> {

    private final List<Alert> alertList;

    /**
     * Creates a new adapter for alerts with the given parameters.
     *
     * @param context
     * @param alertList
     */
    public AlertAdapter(final Context context, final List<Alert> alertList) {
        super(context, 0, alertList);
        this.alertList = alertList;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Alert alert = this.alertList.get(position);
        final AlertViewHolder alertViewHolder;

        if (convertView != null) {
            alertViewHolder = (AlertViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_alert, parent, false);
            alertViewHolder = new AlertViewHolder();
            alertViewHolder.eventName = convertView.findViewById(R.id.alert_event_name);
            alertViewHolder.timestamp = convertView.findViewById(R.id.alert_timestamp);
            alertViewHolder.latitude = convertView.findViewById(R.id.alert_latitude);
            alertViewHolder.longitude = convertView.findViewById(R.id.alert_longitude);
            alertViewHolder.upvotesNumber = convertView.findViewById(R.id.alert_upvotes_number);
            convertView.setTag(alertViewHolder);
        }

        alertViewHolder.eventName.setText(String.valueOf(alert.getEventName()));
        alertViewHolder.timestamp.setText(String.valueOf(alert.getTimestamp()));
        alertViewHolder.latitude.setText(String.valueOf(alert.getLatitude()));
        alertViewHolder.longitude.setText(String.valueOf(alert.getLongitude()));
        alertViewHolder.upvotesNumber.setText(String.valueOf(alert.getUpvotes()));

        return convertView;

    }

    /**
     * Class that holds the exact set of views to be shown for every
     * alert of the data set.
     */
    private static class AlertViewHolder {

        private TextView eventName;
        private TextView timestamp;
        private TextView latitude;
        private TextView longitude;
        private TextView upvotesNumber;

    }
}
