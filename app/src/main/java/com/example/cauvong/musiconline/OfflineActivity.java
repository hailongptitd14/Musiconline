package com.example.cauvong.musiconline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OfflineActivity extends AppCompatActivity implements SongAdapter.InterfAdapter,
        View.OnClickListener{

    private RecyclerView mRecycleView;
    private List<ItemSong> mListItems;
    private SongAdapter mSongAdapter;
    private SeekBar mSeek;
    private MediaPlayer mPlayer;
    private Button mBtnPlay, mBtnPause, mBtnshuffer, mBtnShufferOn,mBtnRepeat, mBtnRepeat1, mBtnRepeatAll, mBtnPrev, mBtnNext;
    private TextView mTvTimeBegin, mTvTimeEnd;
    private Handler mHandler = new Handler();
    private int currentId = 0;
    private List<String> mLvImage;
    private RelativeLayout mRelative;
    private Button mBtnImageOff, mBtnImageOn;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        findViews();
        initComponents();
        startService();
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this,ServiceMedia.class);
        startService(intent);
    }

    private void findViews() {
        mRecycleView = (RecyclerView) findViewById(R.id.rv_songs);
        mSeek = (SeekBar) findViewById(R.id.seekbar);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnRepeat = (Button) findViewById(R.id.btn_repeat);
        mBtnRepeat1 = (Button) findViewById(R.id.btn_repeat_1);
        mBtnRepeatAll = (Button) findViewById(R.id.btn_repeat_all);
        mBtnshuffer = (Button) findViewById(R.id.btn_shuffle);
        mBtnShufferOn = (Button) findViewById(R.id.btn_shuffle_on);
        mBtnPrev = (Button) findViewById(R.id.btn_prev);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mTvTimeBegin = (TextView) findViewById(R.id.tv_time_run);
        mTvTimeEnd = (TextView) findViewById(R.id.tv_time_total);
        mRelative = (RelativeLayout) findViewById(R.id.relative);
        mBtnImageOff = (Button) findViewById(R.id.btn_image_off);
        mBtnImageOn = (Button) findViewById(R.id.btn_image_on);
        mImageView = (ImageView) findViewById(R.id.iv_background);
    }

    private void initComponents() {
        mListItems = new ArrayList<>();
        addList();
        mSongAdapter = new SongAdapter(this,this);
        mRecycleView.setAdapter(mSongAdapter);
        mRecycleView.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        mRecycleView.setLayoutManager(manager);

        mBtnPlay.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnRepeat.setOnClickListener(this);
        mBtnRepeat1.setOnClickListener(this);
        mBtnRepeatAll.setOnClickListener(this);
        mBtnshuffer.setOnClickListener(this);
        mBtnShufferOn.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnImageOff.setOnClickListener(this);
        mBtnImageOn.setOnClickListener(this);
        mPlayer = new MediaPlayer();

    }

    private void addList() {
        Uri uri = MediaStore.Files.getContentUri("external");
        Cursor cursor = getContentResolver().query(uri, null, null, null,null);
        mListItems.clear();
        int idxPath = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
        int idxSong = cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String path = cursor.getString(idxPath);
            if(path.contains("mp3") ){
                if (path.contains("_")) {
                    String informSong = cursor.getString(idxSong);
                    String[] name = informSong.split("_");
                    String nameSong = name[0];
                    String nameSinger = "";
                    if (name.length == 2){
                        nameSinger += name[1];
                    }
                    mListItems.add(new ItemSong(nameSong, nameSinger, path));
                }
            }
            cursor.moveToNext();
        }
        cursor.close();

        mLvImage = new ArrayList<>();
        Uri uri1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor1 = getContentResolver().query(uri1,null, null, null, null);
        mLvImage.clear();
        int indxPathIv = cursor1.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()){
            String pathIv = cursor1.getString(indxPathIv);
            mLvImage.add(pathIv);
            cursor1.moveToNext();
        }
        cursor1.close();
    }

    private void setSeek(int duration) {
        long minuteEnd = TimeUnit.MILLISECONDS.toMinutes(duration);
        long secondEnd = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minuteEnd);
        mTvTimeEnd.setText(String.format("%d:%d",minuteEnd,secondEnd));
        mSeek.setMax(duration);
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public ItemSong getItemSong(int position) {
        return mListItems.get(position);
    }

    @Override
    public void onClick(int position) {
        currentId = position;
        play(position);
    }

    private void play(int positiion) {
        mSongAdapter.setCurrentPosition(positiion);
        mSongAdapter.notifyDataSetChanged();
        if (mPlayer.isPlaying()){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null; // dùng xong xóa,,,,
            mHandler.removeCallbacks(runnable);
        }
        try {
            mPlayer = new MediaPlayer(); // Mỗi lần dùng mới thì phải tạo lại...
            mPlayer.setDataSource(mListItems.get(positiion).getmPath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mBtnImageOn.getVisibility() == View.VISIBLE){
            setImageBg();
        }
        else
            mImageView.setImageResource(R.drawable.bg_login);

        mBtnPlay.setVisibility(View.INVISIBLE);
        mBtnPause.setVisibility(View.VISIBLE);
        setSeek(mPlayer.getDuration());
        mHandler.postDelayed(runnable,100);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPause.setVisibility(View.INVISIBLE);
                mBtnPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setImageBg() {
        Random random = new Random();
        int size = mLvImage.size();
        int rand = random.nextInt(size-1) + 0;
        String path = mLvImage.get(rand);
        Bitmap bitmap = decodeFile(path,mRelative.getWidth(),mRelative.getHeight());
        mImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        mImageView.setImageBitmap(bitmap);
    }

    public static Bitmap decodeFile(String f, int WIDTH, int HIGHT){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(f,o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH=WIDTH;
            final int REQUIRED_HIGHT=HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(o.outWidth/scale/2>=REQUIRED_WIDTH && o.outHeight/scale/2>=REQUIRED_HIGHT)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeFile(f, o2);
        } catch (Exception e) {}
        return null;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int timeBegin = mPlayer.getCurrentPosition();
            long minuteBegin = TimeUnit.MILLISECONDS.toMinutes(timeBegin);
            long secondBegin = TimeUnit.MILLISECONDS.toSeconds(timeBegin) - TimeUnit.MINUTES.toSeconds(minuteBegin);
            mTvTimeBegin.setText(String.format("%d:%d",minuteBegin,secondBegin));
            mHandler.postDelayed(this,100);
            mSeek.setProgress(timeBegin);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                playing();
                break;
            case R.id.btn_pause:
                pauseSong();
                break;
            case R.id.btn_next:
                playNext();
                break;
            case R.id.btn_prev:
                playPreveuos();
                break;
            case R.id.btn_shuffle:
                shuffer();
                break;
            case R.id.btn_shuffle_on:
                shufferOn();
                break;
            case R.id.btn_repeat:
                mBtnRepeat.setVisibility(View.INVISIBLE);
                mBtnRepeat1.setVisibility(View.VISIBLE);
                repeat1();
                break;
            case R.id.btn_repeat_1:
                mBtnRepeat1.setVisibility(View.INVISIBLE);
                mBtnRepeatAll.setVisibility(View.VISIBLE);
                repeatAll();
                break;
            case R.id.btn_repeat_all:
                repeat();
                break;
            case R.id.btn_image_off:
                turnOnImage();
                break;
            case R.id.btn_image_on:
                turnOffImage();
                break;
            default:
                break;
        }
    }

    private void turnOnImage() {
        mBtnImageOff.setVisibility(View.INVISIBLE);
        mBtnImageOn.setVisibility(View.VISIBLE);
        setImageBg();
    }

    private void turnOffImage() {
        mBtnImageOff.setVisibility(View.VISIBLE);
        mBtnImageOn.setVisibility(View.INVISIBLE);
        mImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        mImageView.setImageResource(R.drawable.bg_login);
    }

    private void pauseSong() {
        mPlayer.pause();
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnPause.setVisibility(View.INVISIBLE);
    }

    private void playing() {
        mPlayer.start();
        mBtnPlay.setVisibility(View.INVISIBLE);
        mBtnPause.setVisibility(View.VISIBLE);
        setSeek(mPlayer.getDuration());
    }

    private void playNext() {
        if (currentId < mListItems.size() - 1) {
            play(++currentId);
        }
        else {
            currentId = 0;
            play(currentId);
        }
    }

    private void playPreveuos() {
        if (currentId > 0) {
            play(--currentId);
        }
        else {
            currentId = mListItems.size()-1;
            play(currentId);
        }
    }

    private void shufferOn() {
        mBtnShufferOn.setVisibility(View.INVISIBLE);
        mBtnshuffer.setVisibility(View.VISIBLE);
    }

    private void shuffer() {
        Collections.shuffle(mListItems);
        mBtnShufferOn.setVisibility(View.VISIBLE);
        mBtnshuffer.setVisibility(View.INVISIBLE);
    }

    private void repeat() {
        mBtnRepeatAll.setVisibility(View.INVISIBLE);
        mBtnRepeat.setVisibility(View.VISIBLE);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPause.setVisibility(View.INVISIBLE);
                mBtnPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void repeatAll() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentId < mListItems.size() - 1) {
                    play(++currentId);
                }
                else {
                    currentId = 0;
                    play(currentId);
                }
                if (mBtnRepeatAll.getVisibility() == View.VISIBLE){
                    repeatAll();
                }
            }
        });
    }

    private void repeat1() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(mListItems.get(currentId).getmPath());
                    mPlayer.prepare();
                    mPlayer.start();
                    if (mBtnRepeat1.getVisibility() == View.VISIBLE)
                        repeat1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
