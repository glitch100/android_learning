package evans.jon.hudlu.realmModels;

import io.realm.RealmObject;

/**
 * Created by jon.evans on 27/11/2015.
 */
public class Favourite extends RealmObject {
    private String title;
    private String author;
    private String image;
    private String link;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
