package com.dfst.network.callback;

import com.dfst.network.NetworkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * @author yanfei@supcon.com
 * download callback
 * @date 2016/3/29.
 */
public class DownloadCallback extends SimpleCallback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    private boolean isBreakPoint;

    public void inProgress(float progress,long total) {};

    /**
     * 构造函数
     * @param destFileDir 目标文件夹路径
     * @param destFileName 存储文件名
     */
    public DownloadCallback(String destFileDir, String destFileName)
    {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        this.isBreakPoint = false;
    }

    public DownloadCallback(String destFileDir, String destFileName, boolean isBreakPoint) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        this.isBreakPoint = isBreakPoint;
    }


    @Override
    public File parseResponse(Response response) throws Exception
    {
        return saveFile(response);
    }

    /**
     * 读取response中流数据，并写到本地
     * @param response
     * @return
     * @throws IOException
     */
    public File saveFile(Response response) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        long per = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            per = total / 100 == 0 ? 1 : total / 100;
            long sum = 0;
            long limit = 0;

            File dir = new File(destFileDir);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            final File file = new File(dir, destFileName);
            if (!isBreakPoint && file.exists()) {
                file.delete();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            final long fileLength = file.length();
            raf.seek(fileLength);
            //fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                limit += len;
                //fos.write(buf, 0, len);
                raf.write(buf, 0, len);
                final long finalSum = sum;
                if (limit > per || sum == total) {
                    NetworkUtils.getInstance().getHandler().post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 下载进度跟踪
                            inProgress((finalSum + fileLength) * 1.0f / (total + fileLength),(total + fileLength));
                        }
                    });
                    limit = 0;
                }

            }

            //fos.flush();
            raf.close();

            return file;

        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }

    public boolean isBreakPoint() {
        return isBreakPoint;
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

}
