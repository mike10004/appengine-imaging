package com.gaecompat.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.junit.Test;

import com.gaecompat.imaging.MagicNumbers;
import com.gaecompat.imaging.MagicNumbers.BytePair;
import com.google.appengine.repackaged.com.google.common.io.Files;
import com.google.common.collect.Lists;

public class MagicNumbersTest {

	@Test
	public void test() throws IOException {
		List<String> imageSuffixes = Lists.newArrayList();
		for (ImageInfo.Format format : ImageInfo.Format.values()) {
			for (String ext : format.getCommonExtensions()) {
				imageSuffixes.add("." + ext);
			}
		}
		SuffixFileFilter filter = new SuffixFileFilter(imageSuffixes);
		List<File> files = TestData.getFiles(filter);
		for (File file : files) {
			byte[] bytes = Files.toByteArray(file);
			BytePair magic = MagicNumbers.read(bytes);
			System.out.format("%s %s%n", magic, file.getName());
		}
	}
	
}
