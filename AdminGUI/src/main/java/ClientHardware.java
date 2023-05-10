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
    }

    public int CPU (int overload, int time) {
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
        return overload;
    }

    public int RAM (int overload) {
        if (overload > 90) {
            System.out.println("RAM overload: " + overload +"%");
        }
        return overload;
    }

    public int GPU (int overload, int time) {
        // time per overload (100% only)
        if (overload == 100 && time < 10000) {
            System.out.println("GPU overload: " + overload + "% for " + time + " seconds" );
        }
        return overload;
    }

    public int HardDrive (int overload, int time) {
        // time per overload (100% only)
        if (overload == 100 && time < 10000) {
            System.out.println("HardDrive overload: " + overload + "% for " + time + " seconds" );
        }
        // value
        // writing/ reading speed
        return overload;
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
