package jsonPage;

/**
 * Created by Kailend on 14.12.2016.
 */
public class JsonSavePhoto {

    public int server;
    public String photos_list;
    public int aid;
    public String hash;

    @Override
    public String toString(){
        return server+" "+photos_list+" "+aid+" "+hash;
    }

}
