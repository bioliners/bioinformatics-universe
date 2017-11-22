package biojobs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BioDropDao extends JpaRepository<BioDrop, Integer> {

    BioDrop findByDropId(int dropId);
    BioDrop findByProgram(String program);
    BioDrop findBySubTab(String subTab);

}
