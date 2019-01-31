package home.stanislavpoliakov.meet14_practice;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.InnerHolder> {
    private static final String TAG = "meet14_logs";
    private List<Bitmap> imageList;
    private int type;
    private List<String> birds;

    @Override
    public void onViewRecycled(@NonNull InnerHolder holder) {

    }

    public InnerAdapter(List<Bitmap> imageList, int type) {
        this.imageList = imageList;
        this.type = type;
        if (type == 1) {
            birds = new ArrayList<>();
            birds.add("https://cs8.pikabu.ru/post_img/2018/01/24/12/1516824577158446456.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824554130874306.jpg");
            birds.add("https://cs9.pikabu.ru/post_img/big/2018/01/24/12/1516824599162057167.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824642196914067.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824661115498228.jpg");
            //imageList = new ArrayList<>(birds.size());
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
            Picasso.get().load(birds.get(position)).into(holder.imageView);
        } else holder.imageView.setImageBitmap(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        if (type == 1) return birds.size();
        else return imageList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public InnerHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
