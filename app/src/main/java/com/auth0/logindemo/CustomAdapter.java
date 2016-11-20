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

    public static final int UNSEEN = 0;
    public static final int SEEN = 1;
    public static final int ACCEPTED = 2;


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

 /*   public class ScoreViewHolder extends ViewHolder {
        TextView score;

        public ScoreViewHolder(View v) {
            super(v);
            this.score = (TextView) v.findViewById(R.id.score);
        }
    }

    public class NewsViewHolder extends ViewHolder {
        TextView headline;
        Button read_more;

        public NewsViewHolder(View v) {
            super(v);
            this.headline = (TextView) v.findViewById(R.id.headline);
            this.read_more = (Button) v.findViewById(R.id.read_more);
        }
    }
    */


    public CustomAdapter(String[] dataSet, int[] dataSetTypes) {
        mDataSet = dataSet;
        mDataSetTypes = dataSetTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == UNSEEN) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pending_invite_card, viewGroup, false);

            return new CollabHolder(v);
        }  else if (viewType == SEEN) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pending_invite_card, viewGroup, false);
            return new CollabHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pending_invite_card, viewGroup, false);
            return new CollabHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == UNSEEN) {
            CollabHolder holder = (CollabHolder) viewHolder;
            holder.temp.setText(mDataSet[position]);
        }
        /*else if (viewHolder.getItemViewType() == SEEN) {
            NewsViewHolder holder = (NewsViewHolder) viewHolder;
            holder.headline.setText(mDataSet[position]);
        }
        else {
            ScoreViewHolder holder = (ScoreViewHolder) viewHolder;
            holder.score.setText(mDataSet[position]);
        }*/
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