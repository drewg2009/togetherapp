package com.auth0.logindemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//This connects the cards to the RecyclerView
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private String[] mDataSet;
    private int[] mDataSetTypes;

    public static final int INBOUND = 0;
    public static final int DONE = 1;
    public static final int BROADCAST = 2;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class CollabHolder extends ViewHolder {
        TextView temp;

        public CollabHolder(View v) {
            super(v);
            this.temp = (TextView) v.findViewById(R.id.event_Title);
        }
    }

    public class AcceptedHolder extends ViewHolder {
        TextView temp;

        public AcceptedHolder(View v) {
            super(v);
            this.temp = (TextView) v.findViewById(R.id.event_Title);
        }
    }

    public class BroadcastHolder extends ViewHolder {
        TextView temp;

        public BroadcastHolder(View v) {
            super(v);
            this.temp = (TextView) v.findViewById(R.id.event_Title);
        }
    }



    public CustomAdapter(String[] dataSet, int[] dataSetTypes) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == INBOUND) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pending_invite_card, viewGroup, false);

            return new CollabHolder(v);
        }  else if (viewType == DONE) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.accepted_card, viewGroup, false);
            return new AcceptedHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.broadcast_card, viewGroup, false);
            return new BroadcastHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == INBOUND) {
            CollabHolder holder = (CollabHolder) viewHolder;
            holder.temp.setText(mDataSet[position]);
        }
        else if (viewHolder.getItemViewType() == DONE) {
            AcceptedHolder holder = (AcceptedHolder) viewHolder;
            holder.temp.setText(mDataSet[position]);
        }
        else {
            BroadcastHolder holder = (BroadcastHolder) viewHolder;
            holder.temp.setText(mDataSet[position]);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }
}