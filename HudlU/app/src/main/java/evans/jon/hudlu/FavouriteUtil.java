package evans.jon.hudlu;

import android.content.Context;
import android.util.Log;

import evans.jon.hudlu.evans.jon.hudlu.models.MashableNewsItem;
import evans.jon.hudlu.realmModels.Favourite;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by jon.evans on 27/11/2015.
 */
public class FavouriteUtil {

    public static Favourite mapFavourite(MashableNewsItem mni, Context context) {
        Realm realm = Realm.getInstance(context);
        Favourite favourite = new Favourite();
        try {
            realm.beginTransaction();
            favourite = realm.createObject(Favourite.class);
            favourite.setAuthor(mni.author);
            favourite.setTitle(mni.title);
            favourite.setImage(mni.image);
            favourite.setLink(mni.link);
            realm.commitTransaction();
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return favourite;
    }

    public static MashableNewsItem mapNewsItem(Favourite f, Context context){
        MashableNewsItem mni = new MashableNewsItem();
        mni.author = f.getAuthor();
        mni.title = f.getTitle();
        mni.image = f.getImage();
        mni.link = f.getLink();
        return mni;
    }

    public static void addFavourite(Context context, MashableNewsItem newsItem) {
        Realm realm = Realm.getInstance(context);
        Favourite favourite = mapFavourite(newsItem,context);
        realm.beginTransaction();
        realm.copyToRealm(favourite);
        realm.commitTransaction();
    }

    public static void removeFavourite(Context context, MashableNewsItem newsItem) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        Favourite f = realm.where(Favourite.class).equalTo("title", newsItem.title).findFirst();
        f.removeFromRealm();
        realm.commitTransaction();
    }

    public static boolean isFavourite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        Favourite target = realm.where(Favourite.class).equalTo("title", newsItem.title).findFirst();
        if (target != null) {
            return true;
        }
        return false;
    }

    public static void removeAll(Context context){
        Realm realm = Realm.getInstance(context);
        RealmResults<Favourite> fs = realm.allObjects(Favourite.class);

        realm.beginTransaction();
        for (int i = 0; i < fs.size(); i++) {
            fs.get(i).removeFromRealm();
        }
        realm.commitTransaction();
    }

    public static RealmResults<Favourite> getAllFavourites(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.allObjects(Favourite.class);
    }
}
