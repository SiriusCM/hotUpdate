package com;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public class Main {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        test();
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent("D:\\agent-1.0-SNAPSHOT.jar", "D:\\config");
        test();
    }

    public static void test() {
        System.out.println("Hello");
    }
}
