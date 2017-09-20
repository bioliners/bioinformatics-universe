package biojobs;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="biojobs")
public class BioJob {

    @Id
    @SequenceGenerator(name="pk_sequence", sequenceName="biojobs_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pk_sequence")
    @Column(name="ID")
    private String id;

    @NotNull
    @Column(name="JOBID")
    private int jobId;


    @NotNull
    @Column(name="FINISHED")
    private boolean finished;

    @NotNull
    @Column(name="RESULT_FILE_NAME")
    private String resultFileName;
}
