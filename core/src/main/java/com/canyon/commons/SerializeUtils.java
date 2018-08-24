package com.canyon.commons;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
	public static void serializeToFile(String path, Object obj) {
		if (obj == null)
			return;

		String dir = IOUtils.getDirectory(path);
		IOUtils.createDirectory(dir);

		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(obj);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T deserializeFromFile(String path) {
		T result = null;
		if (!IOUtils.isFile(path))
			return result;

		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(path));
			result = (T) in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static byte[] serializeToBytes(Object obj) {
		byte[] result = null;
		if (obj == null)
			return result;

		ByteArrayOutputStream stream = null;
		ObjectOutputStream out = null;
		try {
			stream = new ByteArrayOutputStream();
			out = new ObjectOutputStream(stream);
			out.writeObject(obj);
			result = stream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeFromBytes(byte[] data) {
		T result = null;
		if (data == null || data.length == 0)
			return result;

		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(data));
			result = (T) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	public static void xmlSerializeToFile(String path, Object obj) {
		if (obj == null)
			return;

		String dir = IOUtils.getDirectory(path);
		IOUtils.createDirectory(dir);

		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new FileOutputStream(path));
			encoder.writeObject(obj);
			encoder.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (encoder != null) {
				encoder.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlDeserializeFromFile(String path) {
		T result = null;
		if (!IOUtils.isFile(path))
			return result;

		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new FileInputStream(path));
			result = (T) decoder.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (decoder != null) {
				decoder.close();
			}
		}
		return result;
	}
}
