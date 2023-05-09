package Hardware;

public class Processor {
    String name;
    String codeName;
    String cores;
    String chip;
    String clock;
    String socket;
    String process;
    int l3Cache;
    int tdp;
    String released;

    public Processor(String name, String codeName, String cores, String chip, String clock, String socket, String process,
                     int l3Cache, int tdp, String released) {
        this.name = name;
        this.codeName = codeName;
        this.cores = cores;
        this.chip = chip;
        this.clock = clock;
        this.socket = socket;
        this.process = process;
        this.l3Cache = l3Cache;
        this.tdp = tdp;
        this.released = released;
    }
}