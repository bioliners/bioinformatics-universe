package biojobs;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="biojob_results")
public class BioJobResult {

    @MapsId("id")
    @ManyToOne
    @JoinColumn(name="ID")
    private BioJob biojob;

    @NotNull
    @Column(name="RESULT_FILE_NAME")
    private String resultFileName;

    @NotNull
    @Column(name="RESULT_FILE")
    private String resultFile;

    public BioJob getBiojob() {
        return biojob;
    }

    public void setBiojob(BioJob biojob) {
        this.biojob = biojob;
    }

    public String getResultFile() {
        return resultFile;
    }

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }
}
