package com.example.adi.recyclerviewmusic.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adi.recyclerviewmusic.R;
import com.example.adi.recyclerviewmusic.model.DataVideo;
import com.example.adi.recyclerviewmusic.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    public RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        mainPresenter = new MainPresenter(this);
        mainPresenter.initData(this);

    }


    @Override
    public void addDatas(List<DataVideo> datas) {
        for (DataVideo data : datas) {
            recyclerViewAdapter.add(data);
        }
        recyclerViewAdapter.notifyDataSetChanged();

    }


    private static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        Context context;
        CardView mycardView;
        List<DataVideo> datas;

        RecyclerViewAdapter(Context context) {
            datas = new ArrayList<DataVideo>();
            this.context = context;
        }

        public void add(DataVideo data) {
            datas.add(data);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mycardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_video, parent, false);

            return new ViewHolder(mycardView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.artistName.setText(datas.get(position).getArtistName());
            holder.trackCensoredName.setText(datas.get(position).getTrackCensoredName());
            Glide.with(context).load(datas.get(position).getArtworkUrl30())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("URL", datas.get(position).getVideoURL());
                    context.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return datas.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;
            CardView mycardView;
            TextView artistName, trackCensoredName;
            VideoView video;
            ImageView imageView;


            public ViewHolder(CardView itemView) {
                super(itemView);
                this.mycardView = itemView;
                artistName = itemView.findViewById(R.id.artistName);
                trackCensoredName = itemView.findViewById(R.id.trackCensoredName);
                video = itemView.findViewById(R.id.video);
                imageView = itemView.findViewById(R.id.imageView);
                linearLayout = itemView.findViewById(R.id.layoutView);

            }


        }


    }
}
