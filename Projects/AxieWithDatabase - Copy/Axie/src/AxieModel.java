

public class AxieModel {
    private String axieName;
    private String axieClass;
    private String originalAxieName;

public AxieModel() {
    
    }


    public String getAxieName() {
        return axieName;
    }


    public void setAxieName(String axieName) {
        if (this.originalAxieName == null) {
            this.originalAxieName = axieName;
        }
        this.axieName = axieName;
    }


    public String getAxieClass() {
        return axieClass;
    }

    public void setAxieClass(String axieClass) {
        this.axieClass = axieClass;
    }


    public String getOriginalAxieName() {
        return originalAxieName;
    }


    public void setOriginalAxieName(String originalAxieName) {
        this.originalAxieName = originalAxieName;
    }


    @Override
    public String toString() {
        return axieName;
    }
}