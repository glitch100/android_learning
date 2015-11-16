package evans.jon.hudlu;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jon.evans on 16/11/2015.
 */

interface OnAdapterInteractionListener {
    void onItemClicked(View view, int position);
}


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private OnAdapterInteractionListener mListener;
    private String[] mDataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText;
        public MyViewHolder(View itemView) {
            super(itemView);
            myText = (TextView) itemView.findViewById(R.id.item_my_text);
        }
    }

    public RecyclerViewAdapter(Context ctx, String[] myDataset) {
        mDataSet = myDataset;
        mListener = (OnAdapterInteractionListener) ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.myText.setText(mDataSet[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(v, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
