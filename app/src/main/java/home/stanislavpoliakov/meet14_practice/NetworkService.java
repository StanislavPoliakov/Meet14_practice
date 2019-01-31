package home.stanislavpoliakov.meet14_practice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.os.Binder;
import android.os.IBinder;
import android.util.ArraySet;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NetworkService extends Service {
    private static final String TAG = "meet14_logs";
    private NetworkServiceBinder mBinder = new NetworkServiceBinder();
    private List<String> snakes, birds, fishes, flies;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public static Intent newIntent(Context context) {
        return new Intent(context, NetworkService.class);
    }

    public class NetworkServiceBinder extends Binder {
        public NetworkService getService() {
            return NetworkService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public List<List<Bitmap>> getCollection() {
        initResources();

        List<List<Bitmap>> bitmapCollection = Collections.synchronizedList(new ArrayList<>());
        try {
            bitmapCollection.add(pool.submit(this::getSnakes).get());
            bitmapCollection.add(pool.submit(this::getBirds).get());
            bitmapCollection.add(pool.submit(this::getFishes).get());
            bitmapCollection.add(pool.submit(this::getFlies).get());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
        //Log.d(TAG, "getCollection: size = " + bitmapCollection.size());
        return bitmapCollection;
    }

    private List<Bitmap> getSnakes() {
        return snakes.stream()
                .map(this::stringToUrl)
                .map(NetworkData::getBitmapThroughHttpUrlConnection)
                .collect(toList());
    }

    private List<Bitmap> getBirds() {
        return new ArrayList<>(birds.size());
        /*return birds.stream()
                .map(this::stringToUrl)
                .map(NetworkData::getBitmapThroughHttpUrlConnection)
                .collect(toList());*/
    }

    private List<Bitmap> getFishes() {
        return fishes.stream()
                .map(this::stringToUrl)
                .map(NetworkData::getBitmapThroughHttpUrlConnection)
                .collect(toList());
    }

    private List<Bitmap> getFlies() {
        return flies.stream()
                .map(this::stringToUrl)
                .map(NetworkData::getBitmapThroughHttpUrlConnection)
                .collect(toList());
    }

    /*private Bitmap getBirds() {
        return birds.stream()
                .map(NetworkData::getBitmapThroughPicasso)
                .collect(toList());
    }*/

    private void initResources() {

        snakes = new ArrayList<>();
        snakes.add("http://apikabu.ru/img_n/2011-10_5/37m.jpg");
        snakes.add("https://cs3.pikabu.ru/post_img/big/2014/03/06/8/1394106007_2078356280.jpg");
        snakes.add("https://cs8.pikabu.ru/post_img/big/2017/04/27/10/1493311875148080447.jpg");
        snakes.add("https://cs8.pikabu.ru/post_img/big/2017/04/27/10/1493311769136821455.jpg");
        snakes.add("https://cs8.pikabu.ru/post_img/big/2017/04/27/10/149331224211268164.jpg");
        snakes.add("https://cs8.pikabu.ru/post_img/big/2017/04/27/10/1493312364142231959.jpg");

        birds = new ArrayList<>();
        birds.add("https://cs8.pikabu.ru/post_img/2018/01/24/12/1516824577158446456.jpg");
        birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824554130874306.jpg");
        birds.add("https://cs9.pikabu.ru/post_img/big/2018/01/24/12/1516824599162057167.jpg");
        birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824642196914067.jpg");
        birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824661115498228.jpg");
        
        fishes = new ArrayList<>();
        fishes.add("https://uznayvse.ru/images/stories/uzn_1401833946.jpeg");
        fishes.add("https://uznayvse.ru/images/stories/uzn_1401834310.jpeg");
        fishes.add("https://uznayvse.ru/images/stories/uzn_1401834378.jpeg");
        fishes.add("https://decem.info/wp-content/uploads/Ryba-kloun.jpg");
        fishes.add("https://decem.info/wp-content/uploads/Mandarinka.jpg");

        flies = new ArrayList<>();
        flies.add("http://printonic.ru/uploads/images/2016/03/14/img_56e660f581895.jpg");
        flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/10_3.jpg");
        flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/7_8.jpg");
        flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/3_14.jpg");
        flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/3_15.jpg");
        flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/1_20.jpg");

    }

    private URL stringToUrl(String value) {
        URL url = null;
        try {
            url = new URL(value);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }
}
