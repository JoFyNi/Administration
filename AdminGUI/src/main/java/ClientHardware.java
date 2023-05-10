import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ClientHardware implements AdapterInterface {

    /*
    @ zu überwachende Metriken
    CPU Auslastung
    RAM Auslastung
    Speicher Auslastung
    GPU Auslastung

    @ Java Management Extensions (JMX) Implementieren
    https://de.wikipedia.org/wiki/Java_Management_Extensions

    Abfragen und Logik in den Chatbot implementieren → Verknüpfung createAChatBot

    konfiguration und Bereitstellen
     */


    public ClientHardware() {
        // CLIENT(CPU, RAM, GPU, HardDrive)
        CPU(50,50);
        RAM(50);
        GPU(50,50);
        HardDrive(50,50);
    }

    public void CPU (int overload, int time) {
        /* Total number of processors or cores available to the JVM */
        System.out.println("Available processors (cores): " +
                Runtime.getRuntime().availableProcessors());

        // get Hardware Information
        /* get overload / used performance
            -> how long is the overload (100% only)
            -> how many threads are aktive
            -> check the caches
         */
        if (overload == 100 && time < 10000) {
            System.out.println("CPU overload: " + overload + "% for " + time + " seconds" );
        }
        // look for needed upgrade (compare with GPU and RAM)
    }

    public void RAM (int overload) {
        /* Total amount of free memory available to the JVM */
        System.out.println("Free memory (bytes): " +
                Runtime.getRuntime().freeMemory());

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println("Maximum memory (bytes): " +
                (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        /* Total memory currently available to the JVM */
        System.out.println("Total memory available to JVM (bytes): " +
                Runtime.getRuntime().totalMemory());

        if (overload > 90) {
            System.out.println("RAM overload: " + overload +"%");
        }
    }

    public void GPU (int overload, int time) {
        try {

            String filePath = "./foo.txt";
            // Use "dxdiag /t" variant to redirect output to a given file
            ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","dxdiag","/t",filePath);
            System.out.println("-- Executing dxdiag command --");
            Process p = pb.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            System.out.println(String.format("-- Printing %1$1s info --",filePath));
            while((line = br.readLine()) != null){
                if(line.trim().startsWith("Card name:") || line.trim().startsWith("Current Mode:")){
                    System.out.println(line.trim());
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        // time per overload (100% only)
        if (overload == 100 && time < 10000) {
            System.out.println("GPU overload: " + overload + "% for " + time + " seconds" );
        }
    }

    public void HardDrive (int overload, int time) {
        /* Get a list of all filesystem roots on this system */
        File[] roots = File.listRoots();

        /* For each filesystem root, print some info */
        for (File root : roots) {
            System.out.println("File system root: " + root.getAbsolutePath());
            System.out.println("Total space (bytes): " + root.getTotalSpace());
            System.out.println("Free space (bytes): " + root.getFreeSpace());
            System.out.println("Usable space (bytes): " + root.getUsableSpace());
        }

        // time per overload (100% only)
        if (overload == 100 && time < 10000) {
            System.out.println("HardDrive overload: " + overload + "% for " + time + " seconds" );
        }
        // value
        // writing/ reading speed
    }


    @Override
    public void CPU(String CPU_NAME, String CPU_CODENAME, String CPU_CORES, String CPU_CHIP, String CPU_CLOCK, String CPU_SOCKET, String CPU_PROCESS,
                    int CPU_L3Cache, int CPU_TDP, String CPU_RELEASED) {
        CPU_NAME = CPU_NAME;
    }

    @Override
    public void RAM(String RAM_TYP, String RAM_TAKT, String RAM_HERSTELLER) {

    }

    @Override
    public void GPU(String GPU_HERSTELLER, String GPU_SERIES, String GPU_ARCHITEKTUR, String GPU_FORMFAKTOR, String GPU_NAME) {

    }

    public void Mainboard(String MAINBOARD_NAME, String MAINBOARD_HERSTELLER, String MAINBOARD_FORMFAKTOR, String MAINBOARD_CHIPSATZ, String MAINBOARD_RAM_TYP) {

    }

    @Override
    public void HardDrive(String name) {

    }
}
