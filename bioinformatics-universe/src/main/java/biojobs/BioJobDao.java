package biojobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface BioJobDao extends JpaRepository<BioJob, Long> {

    BioJob findByJobId(int jobId);

    BioJob save(BioJob bioJob);

    void deleteByJobId(long roleId);

    @Query("select max(b.jobId) from BioJob b")
    Integer getMaxIdOfJob();

    @Query("delete from BioJob b where b.jobDate > :maxDate")
    void deleteIfOlderThan(@Param("maxDate") Date maxDate);
}
