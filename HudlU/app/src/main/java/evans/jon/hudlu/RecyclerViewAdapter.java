package evans.jon.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import evans.jon.hudlu.evans.jon.hudlu.models.MashableNewsItem;
import evans.jon.hudlu.realmModels.Favourite;

/**
 * Created by jon.evans on 16/11/2015.
 */

interface OnAdapterInteractionListener {
    void onItemClicked(View view, int position);
}


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private OnAdapterInteractionListener mListener;
    private List<MashableNewsItem> mDataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView authorText;
        TextView titleText;
        ImageView image;
        Button favouriteButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            authorText = (TextView) itemView.findViewById(R.id.item_author);
            titleText = (TextView) itemView.findViewById(R.id.item_title);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            favouriteButton = (Button) itemView.findViewById(R.id.favouriteButton);
        }
    }
    private RequestQueue requestQueue;

    public RecyclerViewAdapter(Context ctx, List<MashableNewsItem> myDataset) {
        mDataSet = myDataset;
        mListener = (OnAdapterInteractionListener) ctx;
        requestQueue = Volley.newRequestQueue(ctx);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item4, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MashableNewsItem newsItem = mDataSet.get(position);
        holder.authorText.setText(newsItem.author);
        holder.titleText.setText(newsItem.title);

        if (FavouriteUtil.isFavourite(holder.itemView.getContext(), newsItem)) {
            holder.favouriteButton.getBackground().setColorFilter(Color.parseColor("#FF6600"), PorterDuff.Mode.DARKEN);
            holder.favouriteButton.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.favouriteButton.getBackground().setColorFilter(Color.parseColor("#d6d7d7"), PorterDuff.Mode.DARKEN);
            holder.favouriteButton.setTextColor(Color.parseColor("#000000"));
        }
        holder.favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FavouriteUtil.isFavourite(holder.itemView.getContext(), newsItem)) {
                    FavouriteUtil.addFavourite(holder.itemView.getContext(), newsItem);
                    holder.favouriteButton.getBackground().setColorFilter(Color.parseColor("#FF6600"), PorterDuff.Mode.DARKEN);
                    holder.favouriteButton.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    FavouriteUtil.removeFavourite(holder.itemView.getContext(), newsItem);
                    holder.favouriteButton.getBackground().setColorFilter(Color.parseColor("#d6d7d7"), PorterDuff.Mode.DARKEN);
                    holder.favouriteButton.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        ImageRequest imgRequest = new ImageRequest(newsItem.image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        holder.image.setImageBitmap(response);
                    }
                },0,0, ImageView.ScaleType.FIT_XY, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(holder.itemView, "Fail", Snackbar.LENGTH_LONG).show();
                        Log.e("Error", "Error Occured fetching news image");
                    }
                });
        requestQueue.add(imgRequest);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
