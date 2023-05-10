import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.ptql.ProcessFinder;

import java.io.File;

public class Monitoring {

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
    }

    //public static void MonitorSystem() throws Exception {

    //    Sigar sigar = new Sigar();

    //    // get the system metrics using Sigar
    //    Mem mem = sigar.getMem();
    //    CpuPerc cpu = sigar.getCpuPerc();
    //    FileSystemUsage fileSystemUsage = sigar.getFileSystemUsage("/");
    //    double loadAverage = sigar.getLoadAverage()[0];

    //    // create a JMXTrans configuration
    //    List<Query> queries = new ArrayList<Query>();
    //    List<String> targetServers = new ArrayList<String>();

    //    Query query = new Query();
    //    query.setObj("java.lang:type=OperatingSystem");
    //    query.addAttr("SystemLoadAverage");

    //    queries.add(query);
    //    targetServers.add("localhost:9999");

    //    JmxTransExporter exporter = new JmxTransExporter();
    //    exporter.setQueries(queries);
    //    exporter.setTargetServers(targetServers);

    //    List<OutputWriter> outputWriters = new ArrayList<OutputWriter>();

    //    // send the system metrics to console output
    //    ConsoleWriter consoleWriter = new ConsoleWriter();
    //    consoleWriter.addTypeName("typeNames");
    //    consoleWriter.setSettings(new HashMap<String, Object>());
    //    outputWriters.add(consoleWriter);

    //    // send the system metrics to a Graphite server
    //    GraphiteWriter graphiteWriter = new GraphiteWriter();
    //    graphiteWriter.setHost("graphite.example.com");
    //    graphiteWriter.setPort(2003);
    //    graphiteWriter.setDebugEnabled(false);
    //    graphiteWriter.setSettings(new HashMap<String, Object>());
    //    outputWriters.add(graphiteWriter);

    //    exporter.setOutputWriters(outputWriters);
    //    exporter.start();

    //    // print the system metrics to console output
    //    System.out.println("System Load Average: " + loadAverage);
    //    System.out.println("Free Memory: " + mem.getFree() / (1024 * 1024) + " MB");
    //    System.out.println("CPU Usage: " + cpu.getCombined() * 100 + " %");
    //    System.out.println("File System Usage: " + fileSystemUsage.getUsePercent() + " %");
    //}


    private void sendMessage(String s) {
        System.out.println(s);
    }
}
