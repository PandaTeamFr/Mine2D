package celo.mine2d.utils;

import java.io.File;
import java.net.URI;

public enum OperatingSystem {
	LINUX("linux", new String[] { "linux", "unix" }), 
	WINDOWS("windows", new String[] { "win" }), 
	OSX("osx", new String[] { "mac" }), 
	UNKNOWN("unknown", new String[0]);

	private final String name;
	private final String[] aliases;

	public static OperatingSystem getCurrentPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		OperatingSystem[] arrayOfOperatingSystem;
		int j = (arrayOfOperatingSystem = values()).length;
		for (int i = 0; i < j; i++) {
			OperatingSystem os = arrayOfOperatingSystem[i];
			String[] arrayOfString;
			int m = (arrayOfString = os.getAliases()).length;
			for (int k = 0; k < m; k++) {
				String alias = arrayOfString[k];
				if (osName.contains(alias)) {
					return os;
				}
			}
		}
		return UNKNOWN;
	}

	public static void openLink(URI link) {
		try {
			Class<?> desktopClass = Class.forName("java.awt.Desktop");
			Object o = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
			desktopClass.getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { link });
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	OperatingSystem(String name, String[] aliases) {
		this.name = name;
		this.aliases = (aliases == null ? new String[0] : aliases);
	}

	public String[] getAliases() {
		return this.aliases;
	}

	public String getJavaDir() {
		String separator = System.getProperty("file.separator");
		String path = System.getProperty("java.home") + separator + "bin" + separator;
		if ((getCurrentPlatform() == WINDOWS) && (new File(path + "javaw.exe").isFile())) {
			return path + "javaw.exe";
		}
		return path + "java";
	}

	public String getName() {
		return this.name;
	}

	public boolean isSupported() {
		return this != UNKNOWN;
	}
}