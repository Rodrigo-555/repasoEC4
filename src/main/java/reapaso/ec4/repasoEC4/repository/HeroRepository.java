package reapaso.ec4.repasoEC4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reapaso.ec4.repasoEC4.entity.Hero;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {
    Hero findByName(String name);
}
