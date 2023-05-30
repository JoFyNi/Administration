import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.ptql.ProcessFinder;

import java.io.File;

public class Monitoring implements AdapterInterface {

    /*
    String values:
    eq - Equal to value
    ne - Not Equal to value
    ew - Ends with value
    sw - Starts with value
    ct - Contains value (substring)
    re - Regular expression value matches
    operator is one of the following for numeric values:
    eq - Equal to value
    ne - Not Equal to value
    gt - Greater than value
    ge - Greater than or equal value
    lt - Less than value
    le - Less than or equal value
     */

    // TODO: Sigar monitoring class
    public void monitorSystem() {
        while (true) try {
            // CPU-Auslastung abrufen
            Sigar sigar = new Sigar(); // not up to date?
            //double cpuUsage = sigar.getCpuPerc().getCombined() * 100;

            ProcessFinder find = new ProcessFinder(sigar);
            long pid = find.findSingleProcess("Exe.Name.ct=explorer");
            ProcMem memory = new ProcMem();
            memory.gather(sigar, pid);
            System.out.println(Long.toString(memory.getSize()));

            // Speicherplatz abrufen
            File root = new File("C://");
            long freeSpace = root.getFreeSpace();

            // Festplattenbelegung abrufen
            FileSystemUsage usage = sigar.getFileSystemUsage("/");
            double diskUsage = ((double) usage.getTotal() - (double) usage.getFree()) / (double) usage.getTotal() * 100;

            // Überprüfen, ob Schwellenwerte erreicht wurden
            //if (cpuUsage > 80) {
            //    sendMessage("CPU usage is above 80%!");
            //}
            if (freeSpace < 1000000000) { // weniger als 1 GB freier Speicherplatz
                sendMessage("Low disk space!");
            }
            if (diskUsage > 90) {
                sendMessage("Disk usage is above 90%!");
            }

            // Warte 5 Minuten bis zum nächsten Abruf
            Thread.sleep(300000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void CPU(String CPU_NAME, String CPU_CODENAME, String CPU_CORES, String CPU_CHIP, String CPU_CLOCK, String CPU_SOCKET, String CPU_PROCESS, int CPU_L3Cache, int CPU_TDP, String CPU_RELEASED) {

    }

    @Override
    public void RAM(String RAM_TYP, String RAM_TAKT, String RAM_HERSTELLER) {

    }

    @Override
    public void GPU(String GPU_HERSTELLER, String GPU_SERIES, String GPU_ARCHITEKTUR, String GPU_FORMFAKTOR, String GPU_NAME) {

    }

    @Override
    public void Mainboard(String MAINBOARD_NAME, String MAINBOARD_HERSTELLER, String MAINBOARD_FORMFAKTOR, String MAINBOARD_CHIPSATZ, String MAINBOARD_RAM_TYP) {

    }

    @Override
    public void HardDrive(String name) {

    }
}
