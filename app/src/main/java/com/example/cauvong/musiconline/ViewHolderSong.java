package com.example.cauvong.musiconline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by cauvong on 1/5/2018.
 */

public class ViewHolderSong extends RecyclerView.ViewHolder{
    protected TextView tvNumber;
    protected TextView tvNameSong;
    protected TextView tvNameSinger;
    protected ImageView ivSong;
    protected LinearLayout llName;

    public ViewHolderSong(View itemView, final Context context) {
        super(itemView);
        tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
        tvNameSong = (TextView) itemView.findViewById(R.id.tv_name_song);
        tvNameSinger = (TextView) itemView.findViewById(R.id.tv_name_singer);
        llName = (LinearLayout) itemView.findViewById(R.id.ll_name);
        ivSong = (ImageView) itemView.findViewById(R.id.iv_number);
    }
}
