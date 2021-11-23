package org.sirius.hotfix.hot;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.management.ManagementFactory;

/**
 * @date 2021/11/23
 * @auth gaoliandi
 **/
public class Main {

	public static void main(String[] args) {
		System.out.println("----------开始热更代码----------");
		if (args[0].matches("^[0-9]*$")) {
			String pid = null;
			String name = null;
			try {
				VirtualMachine virtualMachine = VirtualMachine.attach(args[0]);
				pid = virtualMachine.id();
				name = virtualMachine.provider().name();
				virtualMachine.loadAgent(args[1], args[2]);
				System.out.println("success:" + name + "@" + pid);
			} catch (Exception var7) {
				System.out.println("fail:" + name + "@" + pid);
			}
		} else {
			String pid = String.valueOf(ManagementFactory.getRuntimeMXBean().getPid());
			for (VirtualMachineDescriptor descriptor : VirtualMachine.list()) {
				if (!pid.equals(descriptor.id())) {
					String name = descriptor.displayName();
					if (name.contains(args[0])) {
						try {
							VirtualMachine.attach(descriptor).loadAgent(args[1], args[2]);
							System.out.println("success:" + name + "@" + descriptor.id());
						} catch (Exception var6) {
							System.out.println("fail:" + name + "@" + descriptor.id());
						}
					}
				}
			}
		}
		System.out.println("----------结束热更代码----------");
	}
}
