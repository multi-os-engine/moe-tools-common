/*
Copyright 2014-2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.common.exec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class AbstractJarExec extends AbstractExec {

	private final ArrayList<String> vmArguments = new ArrayList<String>();

	private final File jar;

	protected AbstractJarExec(File jar, File workingDir) {
		super(getDefaultJavaExec(), workingDir);
		if (jar == null) {
			throw new NullPointerException();
		}
		this.jar = jar;
	}

	public ArrayList<String> getVmArguments() {
		return vmArguments;
	}

	public static String getDefaultJavaExec() {
		return "java";
	}
	
	@Override
	protected String lookupExec() {
		return null;
	}

	@Override
	protected void applyArguments(ArrayList<String> cmds) {
		cmds.addAll(vmArguments);
		cmds.add("-jar");
		cmds.add(jar.getAbsolutePath());
		super.applyArguments(cmds);
	}

	protected static File copyToTmpFromResource(String inFilePath,
			String outFileName) throws IOException {
		File of = File.createTempFile(outFileName, ".jar");
		of.delete();
		of.createNewFile();
		of.deleteOnExit();
		InputStream is = AbstractJarExec.class.getResourceAsStream(inFilePath);
		try {
			FileOutputStream fos = new FileOutputStream(of);
			try {
				byte buffer[] = new byte[64 * 1024];
				int read;
				while ((read = is.read(buffer)) != -1) {
					fos.write(buffer, 0, read);
				}
			}finally {
				fos.close();
			}
		}finally {
			is.close();
		}
		return of;
	}
}
