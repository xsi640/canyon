package com.canyon.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private static final int BUFFER_SIZE = 4096;
	private static final int COMPRESS_LEVEL = 8;

	public static void compress(String sourcePath, String zipPath) {
		File file = new File(sourcePath);
		if (!file.exists())
			return;

		String dir = "";
		if (file.isFile()) {
			dir = IOUtils.getDirectory(sourcePath);
		} else if (file.isDirectory()) {
			dir = file.getAbsolutePath();
		}

		List<File> fileLists = IOUtils.getAllFiles(sourcePath);

		byte[] buffer = new byte[BUFFER_SIZE];

		String zipDir = IOUtils.getDirectory(zipPath);
		IOUtils.createDirectory(zipDir);

		ZipOutputStream zipStream = null;

		try {
			zipStream = new ZipOutputStream(new FileOutputStream(zipPath));
			zipStream.setLevel(COMPRESS_LEVEL);
			for (File f : fileLists) {
				String name = "";
				if (f.isDirectory()) {
					name += f.getAbsolutePath().substring(dir.length() + 1) + File.separator;
				} else if (f.isFile()) {
					name = f.getAbsolutePath().substring(dir.length() + 1);
				}
				ZipEntry zipEntry = new ZipEntry(name);
				zipStream.putNextEntry(zipEntry);
				if (f.isFile()) {
					FileInputStream in = null;
					try {
						in = new FileInputStream(f);
						int len;
						while ((len = in.read(buffer)) > 0) {
							zipStream.write(buffer, 0, len);
						}
					} catch (FileNotFoundException e) {
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
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zipStream != null) {
				try {
					zipStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void decompress(String targetPath, String zipPath) {
		if (!IOUtils.isFile(zipPath))
			return;

		String dir = IOUtils.getDirectory(targetPath);
		IOUtils.createDirectory(dir);

		ZipInputStream zip = null;
		try {
			zip = new ZipInputStream(new FileInputStream(zipPath));
			ZipEntry ze = null;

			byte[] buffer = new byte[BUFFER_SIZE];
			while ((ze = zip.getNextEntry()) != null) {
				String fileName = ze.getName();
				String newFileName = IOUtils.combine(targetPath, fileName);
				if (newFileName.endsWith(File.separator)) {
					IOUtils.createDirectory(newFileName);
				} else {
					String newDir = IOUtils.getDirectory(newFileName);
					IOUtils.createDirectory(newDir);
					FileOutputStream fileStream = null;
					try {
						fileStream = new FileOutputStream(newFileName);
						int len;
						while ((len = zip.read(buffer)) > 0) {
							fileStream.write(buffer, 0, len);
						}
						fileStream.flush();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fileStream != null) {
							fileStream.close();
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zip != null) {
				try {
					zip.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					zip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
