package org.sirius.hotfix.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2021/11/23
 * @auth gaoliandi
 **/
public class Main {

	private static Map<String, Class<?>> map = new HashMap<>();

	public static void agentmain(String pathname, Instrumentation inst) throws ClassNotFoundException, IOException, UnmodifiableClassException {
		if (pathname == null) {
			System.out.println("文件目录为空");
			return;
		}
		System.out.println("热更开始");
		for (Class<?> c : inst.getAllLoadedClasses()) {
			map.put(c.getName(), c);
		}
		File file = new File(pathname);
		for (File f : file.listFiles()) {
			reload(inst, f, "");
		}
		System.out.println("热更完成");
	}

	public static void reload(Instrumentation inst, File file, String name) throws IOException, UnmodifiableClassException, ClassNotFoundException {
		name = name.isEmpty() ? file.getName() : (name + "." + file.getName());
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				reload(inst, f, name);
			}
		} else {
			if (name.endsWith(".class")) {
				String cName = name.substring(0, name.length() - 6);
				if (map.containsKey(cName)) {
					System.out.println("热更" + cName);
					FileInputStream in = new FileInputStream(file);
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					in.close();
					ClassDefinition classDefinition = new ClassDefinition(map.get(cName), bytes);
					inst.redefineClasses(classDefinition);
				}
			}
		}
	}
}
