package home.stanislavpoliakov.meet14_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkData {
    private static final String TAG = "meet14_logs";

    /**
     * Метод работы с HttpURLConnection
     * @param url ссылка на ресурс в формате URL
     * @return загруженный Bitmap
     */
    public static Bitmap getBitmapThroughHttpUrlConnection(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return  BitmapFactory.decodeStream(connection.getInputStream());
            } else return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
