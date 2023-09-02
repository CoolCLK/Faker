package coolclk.faker.launch.injector;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.jvmstat.monitor.*;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Injector {
    public static class VirtualMachineProcess {
        public final static VirtualMachineProcess NULL = new VirtualMachineProcess();

        protected VirtualMachine virtualMachine;
        protected MonitoredHost monitoredHost;
        protected MonitoredVm monitoredVm;
        protected boolean isMinecraft;

        protected String mainClass, mainArgs;

        protected VirtualMachineProcess() {  }

        public VirtualMachineProcess(VirtualMachine virtualMachine) {
            try {
                this.virtualMachine = virtualMachine;
                this.monitoredHost = MonitoredHost.getMonitoredHost("localhost");
                this.monitoredVm = this.monitoredHost.getMonitoredVm(new VmIdentifier("//" + virtualMachine.id()));
                this.mainClass = MonitoredVmUtil.mainClass(monitoredVm, true);
                this.mainArgs = MonitoredVmUtil.mainArgs(monitoredVm);
                this.isMinecraft = (this.mainClass != null && (this.mainClass.equals("net.minecraft.launchwrapper.Launch") || this.mainClass.equals("com.moonsworth.lunar.genesis.Genesis"))) || (this.mainArgs != null && this.mainArgs.startsWith("net.minecraft.launchwrapper.Launch"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public MonitoredVm getMonitoredVm() {
            return this.monitoredVm;
        }

        public VirtualMachine getVirtualMachine() {
            return this.virtualMachine;
        }

        public String getMainClass() {
            return this.mainClass;
        }

        public String getMainArgs() {
            return this.mainArgs;
        }

        public boolean isMinecraft() {
            return this.isMinecraft;
        }

        public String getWindowText() {
            if (this != Injector.VirtualMachineProcess.NULL) {
                final String[] name = {""};
                User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
                    @Override
                    public boolean callback(WinDef.HWND hWnd, Pointer data) {
                        IntByReference windowThreadId = new IntByReference();
                        User32.INSTANCE.GetWindowThreadProcessId(hWnd, windowThreadId);
                        if (User32.INSTANCE.IsWindowEnabled(hWnd) && User32.INSTANCE.IsWindowVisible(hWnd) && windowThreadId.getValue() == Integer.parseInt(getVirtualMachine().id())) {
                            int windowTextLength = User32.INSTANCE.GetWindowTextLength(hWnd) + 1;
                            char[] windowTextChars = new char[windowTextLength];
                            User32.INSTANCE.GetWindowText(hWnd, windowTextChars, windowTextLength);
                            String windowText = new String(windowTextChars);
                            if (!windowText.trim().isEmpty()) {
                                if (!name[0].trim().isEmpty()) name[0] += ",";
                                name[0] += windowText;
                            }
                        }
                        return true;
                    }
                }, Pointer.NULL);
                return name[0];
            }
            return "";
        }

        public boolean inject(PrintStream printStream) {
            try {
                String file = new File(Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath();
                printStream.println("Starting inject agent jar " + file + " to pid " + this.getVirtualMachine().id());
                this.getVirtualMachine().loadAgent(file);
                printStream.println("Inject successfully");
                this.getVirtualMachine().detach();
                return true;
            } catch (Exception e) {
                e.printStackTrace(printStream);
            }
            return false;
        }
    }

    public static List<VirtualMachineProcess> findProcesses() {
        List<VirtualMachineProcess> virtualMachinesProcesses = new ArrayList<VirtualMachineProcess>();
        try {
            List<VirtualMachineDescriptor> virtualMachines = VirtualMachine.list();
            for (VirtualMachineDescriptor virtualMachineDescriptor : virtualMachines) {
                VirtualMachine virtualMachine = virtualMachineDescriptor.provider().attachVirtualMachine(virtualMachineDescriptor);
                virtualMachinesProcesses.add(new VirtualMachineProcess(virtualMachine));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return virtualMachinesProcesses;
    }

    public static List<VirtualMachineProcess> findMinecraftProcesses() {
        List<VirtualMachineProcess> virtualMachinesProcesses = new ArrayList<VirtualMachineProcess>();
        for (VirtualMachineProcess virtualMachineProcess : findProcesses()) {
            if (virtualMachineProcess.isMinecraft()) virtualMachinesProcesses.add(virtualMachineProcess);
        }
        return virtualMachinesProcesses;
    }
}
