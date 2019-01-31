package home.stanislavpoliakov.meet14_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NetworkData {
    private static final String TAG = "meet14_logs";
    private static int count = 0;
    public static Context context;
    public static List<Bitmap> result = new ArrayList<>();

    public static Bitmap getBitmapThroughHttpUrlConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            //Log.d(TAG, "connection response code = " + responseCode);
            if (responseCode == 200) {
                //Log.d(TAG, "getBitmapThroughHttpUrlConnection: ");
                return  BitmapFactory.decodeStream(connection.getInputStream());
            } else return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapThroughPicasso(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                count++;
                if (count == 5) Log.d(TAG, "onBitmapLoaded: Finished!");
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: e = " + e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //Log.d(TAG, "onPrepareLoad: url = " + uri);
            }
        });
        Log.d(TAG, "getBitmapThroughPicasso: result = " + result.size());
        return null;
    }
}
