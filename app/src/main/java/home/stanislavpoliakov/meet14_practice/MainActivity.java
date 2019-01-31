package home.stanislavpoliakov.meet14_practice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    private List<List<Bitmap>> bitmapCollection;
    private NetworkService mService;
    private WorkThread workThread = new WorkThread();
    private static final String TAG = "meet14_logs";

    private class WorkThread extends HandlerThread {
        private static final int FETCH_BITMAP_DATA = 1;
        private Handler mHandler;

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            mHandler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case FETCH_BITMAP_DATA:
                            NetworkData.context = getApplicationContext();
                            bitmapCollection = mService.getCollection();

                            Log.d(TAG, "handleMessage: size = " + bitmapCollection.size());
                            initRecyclerView();
                    }
                }
            };
        }

        public WorkThread() {
            super("WorkThread", Process.THREAD_PRIORITY_BACKGROUND);
        }

        public void setCollection() {
            mHandler.sendEmptyMessage(FETCH_BITMAP_DATA);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((NetworkService.NetworkServiceBinder) service).getService();
            workThread.setCollection();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);
        workThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(NetworkService.newIntent(this), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    private void initRecyclerView() {
        runOnUiThread(() -> {
            RecyclerView outer = findViewById(R.id.outerRecyclerView);
            OuterAdapter adapter = new OuterAdapter(this, bitmapCollection);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            outer.setAdapter(adapter);
            outer.setLayoutManager(layoutManager);
        });
    }
}
