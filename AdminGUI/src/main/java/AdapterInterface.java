public interface AdapterInterface {

    String CPU_NAME = "CPU_NAME";
    String CPU_CODENAME = "CPU_CODE";
    String CPU_CORES = "CPU_CORES";
    String CPU_CHIP = "CPU_CHIP";
    String CPU_CLOCK = "CPU_CLOCK";
    String CPU_SOCKET = "CPU_SOCKET";
    String CPU_PROCESS = "CPU_PROCESS";
    int CPU_L3Cache = 16000;
    int CPU_TDP = 65;
    String CPU_RELEASED = "12.05.2019";

    String GPU_NAME = "GPU_NAME";
    String GPU_HERSTELLER = "GPU_HERSTELLER";
    String GPU_SERIES = "GPU_SERIES";
    String GPU_ARCHITEKTUR = "GPU_ARCHITEKTUR";
    String GPU_FORMFAKTOR = "GPU_FORMFAKTOR";

    String MAINBOARD_NAME = "MAINBOARD_NAME";
    String MAINBOARD_HERSTELLER = "MAINBOARD_HERSTELLER";
    String MAINBOARD_FORMFAKTOR = "MAINBOARD_FORMFAKTOR";
    String MAINBOARD_CHIPSATZ = "MAINBOARD_CHIPSATZ";
    String MAINBOARD_RAM_TYP = "MAINBOARD_RAM_TYP";

    String RAM_TYP = "RAM_TYP";
    String RAM_TAKT = "RAM_TAKT";
    String RAM_HERSTELLER = "RAM_HERSTELLER";


    public void CPU(String CPU_NAME, String CPU_CODENAME, String CPU_CORES, String CPU_CHIP, String CPU_CLOCK, String CPU_SOCKET, String CPU_PROCESS,
                    int CPU_L3Cache, int CPU_TDP, String CPU_RELEASED);
    public void RAM(String RAM_TYP, String RAM_TAKT, String RAM_HERSTELLER);
    public void GPU(String GPU_HERSTELLER, String GPU_SERIES, String GPU_ARCHITEKTUR, String GPU_FORMFAKTOR, String GPU_NAME);
    public void Mainboard(String MAINBOARD_NAME, String MAINBOARD_HERSTELLER, String MAINBOARD_FORMFAKTOR, String MAINBOARD_CHIPSATZ, String MAINBOARD_RAM_TYP);
    public void HardDrive(String name);
}
