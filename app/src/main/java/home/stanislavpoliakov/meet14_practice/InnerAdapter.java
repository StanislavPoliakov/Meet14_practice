package home.stanislavpoliakov.meet14_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.InnerHolder> {
    private static final String TAG = "meet14_logs";
    private List<Bitmap> imageList;
    private int type;
    private List<String> birds, fishes, flies;
    private Context context;

    @Override
    public void onViewRecycled(@NonNull InnerHolder holder) {

    }

    public InnerAdapter(Context context, List<Bitmap> imageList, int type) {
        this.imageList = imageList;
        this.type = type;
        this.context = context;
        if (type == 1) {
            birds = new ArrayList<>();
            birds.add("https://cs8.pikabu.ru/post_img/2018/01/24/12/1516824577158446456.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824554130874306.jpg");
            birds.add("https://cs9.pikabu.ru/post_img/big/2018/01/24/12/1516824599162057167.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824642196914067.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824661115498228.jpg");
        } else if (type == 2) {
            fishes = new ArrayList<>();
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401833946.jpeg");
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401834310.jpeg");
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401834378.jpeg");
            fishes.add("https://decem.info/wp-content/uploads/Ryba-kloun.jpg");
            fishes.add("https://decem.info/wp-content/uploads/Mandarinka.jpg");
        } else if (type == 3) {
            flies = new ArrayList<>();
            flies.add("http://printonic.ru/uploads/images/2016/03/14/img_56e660f581895.jpg");
            flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/10_3.jpg");
            flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/7_8.jpg");
            flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/3_14.jpg");
            flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/3_15.jpg");
            flies.add("https://cdn.fishki.net/upload/post/201507/23/1605583/1_20.jpg");
        }
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_holder, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        if (type == 1) {
            Picasso.get().load(birds.get(position)).fit().into(holder.imageView);
        } else if (type == 2) {
            RequestOptions options = new RequestOptions()
                    .centerCrop();
            Glide.with(context).load(fishes.get(position)).apply(options).into(holder.imageView);
        } else if (type == 3) {
            holder.imageView.setVisibility(View.GONE);
            holder.frescoView.setVisibility(View.VISIBLE);
            holder.frescoView.setImageURI(flies.get(position));
        } else {
            holder.imageView.setImageBitmap(imageList.get(position));
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 1) return birds.size();
        else if (type == 2) return fishes.size();
        else if (type == 3) return flies.size();
        else return imageList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public SimpleDraweeView frescoView;

        public InnerHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            frescoView = itemView.findViewById(R.id.frescoView);
        }
    }
}
