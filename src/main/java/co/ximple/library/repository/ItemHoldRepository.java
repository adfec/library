package co.ximple.library.repository;

import co.ximple.library.entities.ItemHolds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemHoldRepository extends JpaRepository<ItemHolds, Long> {
    <T extends ItemHolds> ItemHolds findByItemId(Long id);
}
