package home.stanislavpoliakov.meet14_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

    /**
     * Конструктор внутреннего, горизонтального, RecyclerView
     * @param context Activity. Нужен для Glide
     * @param imageList - это, фактически, только список, подготовленный через HttpUrlConnection
     * @param type - тип данных для отображения (порядковый элемент в коллекции коллекций, раз уж
     *             они все разные)
     */
    public InnerAdapter(Context context, List<Bitmap> imageList, int type) {
        this.imageList = imageList;
        this.type = type;
        this.context = context;

        // Если у нас второй тип данных, то рисуем птиц
        if (type == 1) {
            birds = new ArrayList<>();
            birds.add("https://cs8.pikabu.ru/post_img/2018/01/24/12/1516824577158446456.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824554130874306.jpg");
            birds.add("https://cs9.pikabu.ru/post_img/big/2018/01/24/12/1516824599162057167.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824642196914067.jpg");
            birds.add("https://cs10.pikabu.ru/post_img/big/2018/01/24/12/1516824661115498228.jpg");

            // Если третий - рыб
        } else if (type == 2) {
            fishes = new ArrayList<>();
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401833946.jpeg");
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401834310.jpeg");
            fishes.add("https://uznayvse.ru/images/stories/uzn_1401834378.jpeg");
            fishes.add("https://decem.info/wp-content/uploads/Ryba-kloun.jpg");
            fishes.add("https://decem.info/wp-content/uploads/Mandarinka.jpg");

            // Четвертый - мух
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

    /**
     * В зависимости от типа данных по-разному биндим элементы.
     * @param holder, который будет содержать наши загруженные (для HttpUrlConnection) и загружаемые
     *                (для всего остального г..на) данные
     * @param position позиция элемента, который мы рисуем (отображаем)
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

        // Птиц затягиваем через "убогий" Picasso, который работает только в UI-потоке (!),
        // не способен отловить загрузку группы картинок (видимо потому, что в трех выделяемых потоках
        // у Executor keepAliveTime выставлено на 0 милисекунд, при этом долгие загрузки он как-то просто
        // "забывает" (сбрасывает, наверное). Честно говоря, даже лень разбираться в этом г..! Да и еще -
        // этот "add-on" не способен сохранять загрузку в Bitmap при загрузке пачкой, вне зависимости
        // от того инстанциируем мы Target или нет (что, в принципе, очевидно). То есть инструмент для
        // ну совсем ленивых! Для тех, кто хочет копи-пастнуть (Copy-Paste) адрес картинки в строке браузера и
        // закачать в Android одной строкой. Думаю, что для тех, кому лень даже в Java разбираться. IMHO, конечно.
        if (type == 1) {
            Picasso.get().load(birds.get(position)).fit().into(holder.imageView);

            // Рыб грузим через Glide. Надо сказать, что Glide оставляет приятное впечатление тем, что
            // позволяет получать FutureTarget при указании asBitmap, что дает хоть какую-то надежду
            // на светлое будущее! Не проверял остальное, провозившись с Picasso. Просто хардкодим
            // (hardcode) значения здесь и грузим.
        } else if (type == 2) {
            RequestOptions options = new RequestOptions()
                    .centerCrop();
            Glide.with(context).load(fishes.get(position)).apply(options).into(holder.imageView);

            // Мухи идут через Fresco. Сложно комментировать это решение.
            // Добавлю только, что для того, чтобы вставить картинку во FrescoView - SimpleDraweeView, но
            // при этом не созавать отдельную разметку для ViewHolder - мы создаем Drawee на всех элементах,
            // просто для всех, кроме этих (мух) прячем его (Visibility.GONE)
        } else if (type == 3) {
            holder.imageView.setVisibility(View.GONE);
            holder.frescoView.setVisibility(View.VISIBLE);
            holder.frescoView.setImageURI(flies.get(position));

            // И, наконец, самый волшебный, самый понятный, очевидный и прекрасный способ загрузки
            // изображений, позволяющий реализовать любую струкутру java.util.concurrent, любую логику,
            // доступный "из коробки" - HttpURLConnection
        } else {
            holder.imageView.setImageBitmap(imageList.get(position));
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    /**
     * В зависимости от типа данных будем менять и размер отображаемой информации (уж коль скоро мы
     * получаем в конструкторе элемент с нулевой длинной! Спасибо, Picasso, Glide и Fresco!!!)
     * @return размер отображаемых данных
     */
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
