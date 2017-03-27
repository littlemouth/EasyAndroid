package com.dfst.media.util;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

public class Mp4Manager {
	
	private OnCompleteListener onCompleteListener;
	
	public interface OnCompleteListener {
		void onComplemte();
	}
	
	public void mergeMp4(final List<String> files, final String savePath) {
		new Thread() {
			public void run() {
				List<Movie> moviesList = new LinkedList<Movie>();
				if (files == null || files.size() <= 0) {
					return;
				}
				
				if (files.size() == 1) {
					File file = new File(files.get(0));
					File newFile = new File(savePath);
					if (newFile.exists())
						newFile.delete();
					file.renameTo(newFile);
					onCompleteListener.onComplemte();
					return;
				}

				try {
					for (String file : files) {
						moviesList.add(MovieCreator.build(file));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				List<Track> videoTracks = new LinkedList<Track>();
				List<Track> audioTracks = new LinkedList<Track>();
				for (Movie m : moviesList) {
					for (Track t : m.getTracks()) {
						if (t.getHandler().equals("soun")) {
							audioTracks.add(t);
						}
						if (t.getHandler().equals("vide")) {
							videoTracks.add(t);
						}
					}
				}

				Movie result = new Movie();

				try {
					if (audioTracks.size() > 0) {
						result.addTrack(new AppendTrack(audioTracks
								.toArray(new Track[audioTracks.size()])));
					}
					if (videoTracks.size() > 0) {
						result.addTrack(new AppendTrack(videoTracks
								.toArray(new Track[videoTracks.size()])));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Container out = new DefaultMp4Builder().build(result);

				try {
					File outputFile = new File(savePath);
					FileChannel fc = new RandomAccessFile(outputFile, "rw")
							.getChannel();
					out.writeContainer(fc);
					fc.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				for (String filePath : files) {
					File file = new File(filePath);
					if (file.exists()) {
						file.delete();
					}
				}
				
				moviesList.clear();
				onCompleteListener.onComplemte();
			}
		}.start();
	}

	public OnCompleteListener getOnCompleteListener() {
		return onCompleteListener;
	}

	public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
		this.onCompleteListener = onCompleteListener;
	}
	
	
}
