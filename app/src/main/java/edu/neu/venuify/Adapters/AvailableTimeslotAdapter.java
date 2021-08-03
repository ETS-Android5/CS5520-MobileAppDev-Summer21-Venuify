package edu.neu.venuify.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import java.util.List;

import edu.neu.venuify.Models.ReservationObject;
import edu.neu.venuify.R;

/*
Adapted from https://www.geeksforgeeks.org/android-horizontal-recyclerview-with-examples/
 */

public class AvailableTimeslotAdapter extends RecyclerView.Adapter<AvailableTimeslotAdapter.MyView> {

    private List<String> list;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        TextView textView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textView = (TextView) view
                    .findViewById(R.id.availableTimeslot);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public AvailableTimeslotAdapter(List<String> horizontalList) {
        this.list = horizontalList;
    }


    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType) {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.available_timeslot,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    public void onBindViewHolder(final MyView holder,
                                 final int position) {

        holder.textView.setText(list.get(position));
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount() {
        return list.size();
    }
}