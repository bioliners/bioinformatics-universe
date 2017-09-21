package biojobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BioJobDao extends JpaRepository<BioJob, Long> {

    BioJob findByJobId(int jobId);

    BioJob save(BioJob bioJob);

    void deleteByJobId(long roleId);

    @Query("select max(jobId) from BioJob")
    Integer getMaxIdOfJob();
}
