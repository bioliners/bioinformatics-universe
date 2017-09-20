package biojobs;

import org.springframework.data.repository.CrudRepository;

public interface BioJobDao extends CrudRepository<BioJob, Long>, BioJobDaoCustom  {

    BioJob findByJobId(int jobId);

    BioJob save(BioJob bioJob);

    void deleteByJobId(long roleId);
}
