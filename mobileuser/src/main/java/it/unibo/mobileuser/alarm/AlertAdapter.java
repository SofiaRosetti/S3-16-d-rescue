package it.unibo.mobileuser.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import it.unibo.drescue.model.Alert;
import it.unibo.mobileuser.R;

import java.util.List;

public class AlertAdapter extends ArrayAdapter<Alert> {

    public AlertAdapter(final Context context, final List<Alert> alertList) {
        super(context, 0, alertList);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_alert, parent, false);

        return convertView;

    }
}
