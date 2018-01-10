package com.example.cauvong.musiconline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

/**
 * Created by cauvong on 1/5/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private InterfAdapter mInfAdapter;
    private int mPositionClick = -1;
    private Context context;

    public SongAdapter(InterfAdapter intef, Context context){
        this.mInfAdapter = intef;
        this.context  = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songs,parent,false);
        ViewHolderSong viewHolderSong = new ViewHolderSong(contentView,context);
        return viewHolderSong;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        ItemSong itemSong = mInfAdapter.getItemSong(position);
        ViewHolderSong viewHolderSong = (ViewHolderSong) holder;

        viewHolderSong.tvNumber.setText((position+ 1) +"");
        viewHolderSong.tvNameSong.setText(itemSong.getmNameSong());
        viewHolderSong.tvNameSinger.setText(itemSong.getmNameSinger());
        viewHolderSong.llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfAdapter.onClick(position);
            }
        });
        if (position == mPositionClick){
            viewHolderSong.ivSong.setVisibility(View.VISIBLE);
            viewHolderSong.ivSong.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_rotate));
            viewHolderSong.tvNumber.setVisibility(View.INVISIBLE);
        }
        else {
            viewHolderSong.ivSong.setVisibility(View.INVISIBLE);
            viewHolderSong.tvNumber.setVisibility(View.VISIBLE);
        }
    }

    public void setCurrentPosition(int position){
        mPositionClick = position;
    }


    @Override
    public int getItemCount() {
        return mInfAdapter.getCount();
    }

    protected interface InterfAdapter{
        int getCount();
        ItemSong getItemSong(int position);
        void onClick(int position);
    }
}
