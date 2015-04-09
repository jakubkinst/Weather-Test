package cz.kinst.jakub.weather.android.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jakubkinst on 09/04/15.
 */
public class BitmapUtility {
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] array = stream.toByteArray();
			stream.close();
			return array;
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}


	public static Bitmap byteArrayToButmap(byte[] array) {
		return BitmapFactory.decodeByteArray(array, 0, array.length);
	}
}
