package fr.loual.cinemabackend.repositories;

import fr.loual.cinemabackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
