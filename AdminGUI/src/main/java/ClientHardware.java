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


    public ClientHardware() {}

    public void CPU () {}

    public void RAM () {}

    public void HardDrive () {}

    public void GPU () {}

    @Override
    public void CPU(String CPU_NAME, String CPU_CODENAME, String CPU_CORES, String CPU_CHIP, String CPU_CLOCK, String CPU_SOCKET, String CPU_PROCESS,
                    int CPU_L3Cache, int CPU_TDP, String CPU_RELEASED) {

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
