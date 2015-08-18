package com.javassf.basic.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Repository {
	public abstract String storeByExt(String paramString, InputStream paramInputStream) throws IOException;

	public abstract String storeByName(String paramString, InputStream paramInputStream) throws IOException;

	public abstract boolean retrieve(String paramString, OutputStream paramOutputStream);
}
