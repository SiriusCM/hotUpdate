package org.sirius.hotfix.hot;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * @date 2021/11/23
 * @auth gaoliandi
 **/
public class Main {

	public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
		VirtualMachine vm = VirtualMachine.attach(args[0]);
		vm.loadAgent(args[1], args[2]);
	}
}
