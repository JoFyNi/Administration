import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.ptql.ProcessFinder;

import java.io.File;

public class Monitoring {

    public void monitorSystem() {
        while (true) {
            try {
                // CPU-Auslastung abrufen
                Sigar sigar = new Sigar();
                //double cpuUsage = sigar.getCpuPerc().getCombined() * 100;

                ProcessFinder find=new ProcessFinder(sigar);
                long pid=find.findSingleProcess("Exe.Name.ct=explorer");
                ProcMem memory=new ProcMem();
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
                if (freeSpace < 1000000000) { // weniger als 1GB freier Speicherplatz
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
    }

    private void sendMessage(String s) {
        System.out.println(s);
    }
}
