package com.dfst.media;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dfst.media.util.Mp4Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yanfei on 2016-11-28.
 */
public class SimpleRecorder extends Activity implements View.OnClickListener, Runnable{
    private TextView timeTv;
    private ImageView startBtn, stopBtn, cancelBtn;
    private TextView recordingTv;
    private RelativeLayout recordInfoLayout;
    private VoiceLineView voiceLineView;
    private AZLoading.LoadingWheel loadingWheel;

    private MediaRecorder recorder;

    private int second;
    private boolean recording, isBreakpoint, isReset, onceStart;
    private boolean isAlive = true;
    private String rootPath;

    private Timer recordInfoTimer, lengthTimer;

    private List<String> files;
    private Mp4Manager mp4Manager;
    private String savePath;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if(recorder==null || !isAlive) return;
                double ratio = (double) recorder.getMaxAmplitude() / 100;
                double db = 0;// 分贝
                //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
                //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
                //同时，也可以配置灵敏度sensibility
                if (ratio > 1)
                    db = 20 * Math.log10(ratio);
                //只要有一个线程，不断调用这个方法，就可以使波形变化
                //主要，这个方法必须在ui线程中调用
                voiceLineView.setVolume((int) (db));
            } else if (msg.what == 1) {
                if (recordingTv.getVisibility() == View.VISIBLE) {
                    recordingTv.setVisibility(View.INVISIBLE);
                } else {
                    recordingTv.setVisibility(View.VISIBLE);
                }
            } else if (msg.what == 2) {
                timeTv.setText(formatTime(second));
            } else if (msg.what == 3) {
                files.clear();
                loadingWheel.dismiss();
                stopDialog();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_ea_dfst);

        startBtn = (ImageView) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
        stopBtn = (ImageView) findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(this);
        stopBtn.setEnabled(false);
        cancelBtn = (ImageView) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

        recordingTv = (TextView) findViewById(R.id.recordingTv);
        recordInfoLayout = (RelativeLayout) findViewById(R.id.record_info_layout);

        timeTv = (TextView) findViewById(R.id.recorder_timer_tv);
        timeTv.setText("00:00:00");

        voiceLineView = (VoiceLineView) findViewById(R.id.voiceView);

        rootPath= getExternalFilesDir(null).getPath();

        recorder = new MediaRecorder();

        files = new ArrayList<>();

        mp4Manager = new Mp4Manager();

        loadingWheel = new AZLoading.LoadingWheel(this);

        new Thread(this).start();
    }

    private String formatTime(int second) {
        String hh=second/3600>9?second/3600+"":"0"+second/3600;
        String  mm=(second % 3600)/60>9?(second % 3600)/60+"":"0"+(second % 3600)/60;
        String ss=(second % 3600) % 60>9?(second % 3600) % 60+"":"0"+(second % 3600) % 60;
        return hh+":"+mm+":"+ss;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_btn) {
            if (recording) {
                pause();
            } else {
                start();
            }
        } else if (view.getId() == R.id.stop_btn) {
            stop();
        } else if (view.getId() == R.id.cancelBtn) {
            if (onceStart) {
                cancelAndBackDialog();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null && recording && !isReset) {
            recorder.stop();
            recorder.reset();
        }
        if (onceStart)
            recorder.release();
        isAlive = false;
        cancelRecordInfoTimer();
        cancelLengthTimer();

    }

    @Override
    public void onBackPressed() {
        if (onceStart) {
            cancelAndBackDialog();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 开始录制
     */
    private void start() {
        recorder = new MediaRecorder();
        /**
         * recorder.setAudioSource设置声音来源。
         * recorder.AudioSource这个内部类详细的介绍了声音来源。
         * 该类中有许多音频来源，不过最主要使用的还是手机上的麦克风，recorder.AudioSource.MIC
         */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        /**
         * recorder.setOutputFormat代表输出文件的格式。该语句必须在setAudioSource之后，在prepare之前。
         * OutputFormat内部类，定义了音频输出的格式，主要包含MPEG_4、THREE_GPP、RAW_AMR……等。
         */
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        /**
         * recorder.setAddioEncoder()方法可以设置音频的编码
         * AudioEncoder内部类详细定义了两种编码：AudioEncoder.DEFAULT、AudioEncoder.AMR_NB
         */
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        /**
         * 设置录音之后，保存音频文件的位置
         */

        if (files.size() == 0) {
            savePath = rootPath + "/rec_" + System.currentTimeMillis() + ".amr";
        }
        String path = rootPath + "/" + System.currentTimeMillis() + ".amr";
        recorder.setOutputFile(path);
        recorder.setMaxDuration(1000 * 60 * 10);
        files.add(path);

        /**
         * 调用start开始录音之前，一定要调用prepare方法。
         */
        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startBtn.setImageResource(R.mipmap.recorder_pause);
        stopBtn.setEnabled(true);
        recordingTv.setText("录音中");
        recordInfoLayout.setVisibility(View.VISIBLE);
        recording = true;
        isReset = false;
        onceStart = true;
        if (!isBreakpoint) {
            startRecordInfoTimer();
        }
        startLengthTimer();
    }

    public void stop() {
        if (recorder != null && !isReset) {
            recorder.stop();
            recorder.reset();
            isReset = true;
        }

        cancelRecordInfoTimer();
        cancelLengthTimer();
        second = 0;
        recording = false;
        isBreakpoint = false;
        recordingTv.setText("录音中");
        recordInfoLayout.setVisibility(View.INVISIBLE);
        startBtn.setImageResource(R.mipmap.recorder_start);

        if (files.size() > 1) {
            loadingWheel.showWithText("处理中...");
            mp4Manager.setOnCompleteListener(new Mp4Manager.OnCompleteListener() {
                @Override
                public void onComplemte() {
                    handler.sendEmptyMessage(3);
                }
            });
            mp4Manager.mergeMp4(files, savePath);
        } else {
            File file = new File(files.get(0));
            File newFile = new File(savePath);
            if (!file.exists()) return;
            if (newFile.exists()) {
                newFile.delete();
            }
            file.renameTo(newFile);
            files.clear();
            stopDialog();
        }

    }

    private void pause() {
        if (recorder != null && !isReset) {
            recorder.stop();
            recorder.reset();
            isReset = true;
        }
        startBtn.setImageResource(R.mipmap.recorder_start);
        recordingTv.setText("暂停");
        recording = false;
        isBreakpoint = true;
        cancelLengthTimer();
    }

    private void startRecordInfoTimer() {
        recordInfoTimer = new Timer();
        recordInfoTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 500);
    }

    private void cancelRecordInfoTimer() {
        if (recordInfoTimer != null) {
            recordInfoTimer.cancel();
            recordInfoTimer = null;
        }
    }

    private void startLengthTimer() {
        lengthTimer = new Timer();
        lengthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                second++;
                handler.sendEmptyMessage(2);
            }
        }, 1000, 1000);
    }

    private void cancelLengthTimer() {
        if (lengthTimer != null) {
            lengthTimer.cancel();
            lengthTimer = null;
        }
    }

    @Override
    public void run() {
        while (isAlive) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopDialog() {
        AZAlertDialog.show(this, "提示", "是否使用本次录音？", "确定", "取消", false , false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == -1) {
                    returnResult(RESULT_OK);
                } else if (which == -2) {
                    returnResult(RESULT_CANCELED);
                }
            }
        });
    }

    private void cancelAndBackDialog() {
        AZAlertDialog.show(this, "提示", "是否取消本次录音？", "是", "否",false ,false ,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (recorder != null && !isReset) {
                    recorder.stop();
                    recorder.reset();
                    isReset = true;
                }
                dialog.dismiss();
                if (which == -1) {
                    new Thread() {
                        @Override
                        public void run() {
                            for (String path : files) {
                                File file = new File(path);
                                if (file.exists())
                                    file.delete();
                            }
                        }
                    }.start();
                    returnResult(RESULT_CANCELED);
                } else if (which == -2) {

                }
            }
        });
    }

    private void returnResult(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_OK) {
            intent.setData(Uri.parse(savePath));
        }
        setResult(resultCode, intent);
        finish();
    }
}
