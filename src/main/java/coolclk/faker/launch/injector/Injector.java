package coolclk.faker.launch.injector;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

import java.util.ArrayList;
import java.util.List;

public class Injector {
    public static List<VirtualMachine> findProcesses() {
        List<VirtualMachine> minecraftVirtualMachines = new ArrayList<VirtualMachine>();
        try {
            MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost("localhost");
            List<VirtualMachineDescriptor> virtualMachines = VirtualMachine.list();
            for (VirtualMachineDescriptor virtualMachineDescriptor : virtualMachines) {
                VirtualMachine virtualMachine = virtualMachineDescriptor.provider().attachVirtualMachine(virtualMachineDescriptor);
                MonitoredVm monitoredVm = monitoredHost.getMonitoredVm(new VmIdentifier("//" + virtualMachine.id()));
                String processMainClass = MonitoredVmUtil.mainClass(monitoredVm, true),
                        processMainArgs = MonitoredVmUtil.mainArgs(monitoredVm);
                boolean isMinecraft =
                        processMainClass.equals("net.minecraft.launchwrapper.Launch") ||
                        (processMainArgs != null && processMainArgs.startsWith("net.minecraft.launchwrapper.Launch")) ||
                        processMainClass.equals("com.moonsworth.lunar.genesis.Genesis") // For Lunar Client
                        ;
                if (isMinecraft) minecraftVirtualMachines.add(virtualMachine);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return minecraftVirtualMachines;
    }
}
