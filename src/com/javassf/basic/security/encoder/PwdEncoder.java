package com.javassf.basic.security.encoder;

public abstract interface PwdEncoder {
	public abstract String encodePassword(String paramString);

	public abstract String encodePassword(String paramString1, String paramString2);

	public abstract boolean isPasswordValid(String paramString1, String paramString2);

	public abstract boolean isPasswordValid(String paramString1, String paramString2, String paramString3);
}
