package smellychiz.projects.ogc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import smellychiz.projects.ogc.objects.world.Biome;
import android.content.Context;
import android.util.Log;

public class IO {

	public String fileName;

	static Context mContext;

	public IO(String fileName, Context c) {
		this.fileName = fileName;
		mContext = c;
	}

	public static void save(Biome biome) {
		System.out.println("saving...");
		if (biome != null) {
			try {

				String filePath = mContext.getFilesDir().getPath().toString()
						+ "/FINDME.ser";

				File file = new File(filePath);

				System.out.println(filePath);

				if (!file.exists()) {
					System.out.println("File is created!");
					file.createNewFile();
				} else {
					System.out.println("File already exists.");
				}

				FileOutputStream fos = new FileOutputStream(file);

				ObjectOutputStream oos = new ObjectOutputStream(fos);

				oos.writeObject(biome);

				oos.flush();

				oos.close();

				fos.flush();

				fos.close();

				System.out.println(mContext.getFilesDir() + ", "
						+ mContext.fileList());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Biome loadBiome() {
		Biome b = null;
		try {
			String filePath = mContext.getFilesDir().getPath().toString()
					+ "/FINDME.ser";
			File file = new File(filePath);
			if (file.exists()) {
				Log.d("working...", "loading...");
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				b = (Biome) ois.readObject();

				ois.close();
				fis.close();
				Log.d("working...", "done..?");
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

}
