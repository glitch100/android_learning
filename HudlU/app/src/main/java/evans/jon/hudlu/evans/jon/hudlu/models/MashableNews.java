package evans.jon.hudlu.evans.jon.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jon.evans on 20/11/2015.
 */
public class MashableNews {

    //Doing this because in the API response the JSON Property will be called new,
    //and we want it to use new when deserializing instead of newsItem
    @SerializedName("new")
    public List<MashableNewsItem> newsItems;
}
