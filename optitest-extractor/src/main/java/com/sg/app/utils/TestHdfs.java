package com.sg.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
/**
 * @author Karoonakar
 *
 */
public class TestHdfs {

	public static void testMain() {
		InputStream inputStream = null;
		try {
			Configuration configuration = new Configuration();
			URI uri = new URI("hdfs://10.0.2.15:8020/user/opti-test/SCF_Deal_Data-1.csv");
			FileSystem hdfs = FileSystem.get(uri, configuration);
			Path path = new Path(uri);
			BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(path)));
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(inputStream);
		}
	}
}
