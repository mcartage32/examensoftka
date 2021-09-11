package modelo;

public class Opcion {
    
    private String opcA;
    private String opcB;
    private String opcC;
    private String opcD;

    public String getOpcA() {
        return opcA;
    }

    public void setOpcA(String opcA) {
        this.opcA = opcA;
    }

    public String getOpcB() {
        return opcB;
    }

    public void setOpcB(String opcB) {
        this.opcB = opcB;
    }

    public String getOpcC() {
        return opcC;
    }

    public void setOpcC(String opcC) {
        this.opcC = opcC;
    }

    public String getOpcD() {
        return opcD;
    }

    public void setOpcD(String opcD) {
        this.opcD = opcD;
    }

    public Opcion(String opcA, String opcB, String opcC, String opcD) {
        this.opcA = opcA;
        this.opcB = opcB;
        this.opcC = opcC;
        this.opcD = opcD;
    }

    public Opcion() {
    }
    
    
    
}
