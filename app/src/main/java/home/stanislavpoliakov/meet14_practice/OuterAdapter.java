package home.stanislavpoliakov.meet14_practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.OuterHolder> {
    //private InnerAdapter adapter;
    private List<List<Bitmap>> mCollection;
    private Context context;

    public OuterAdapter(Context context, List<List<Bitmap>> collection) {
        this.mCollection = collection;
        this.context = context;
    }

    @NonNull
    @Override
    public OuterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outer_holder, parent, false);
        return new OuterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OuterHolder holder, int position) {
        InnerAdapter adapter = new InnerAdapter(mCollection.get(position), position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.innerRecyclerView.setAdapter(adapter);
        holder.innerRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public class OuterHolder extends RecyclerView.ViewHolder {
        public RecyclerView innerRecyclerView;

        public OuterHolder(View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
        }
    }
}
