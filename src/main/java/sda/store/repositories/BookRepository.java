package sda.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.store.entities.BookEntity;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    @Query(value = "select*FROM books p where p.category_id=1",nativeQuery = true)
    List<BookEntity> findByCategorySpecialOffers();

    @Query(value = "select*FROM books p where p.category_id=2",nativeQuery = true)
    List<BookEntity> findByCategoryBestsellers();

    @Query(value = "select*FROM books p where p.category_id=3",nativeQuery = true)
    List<BookEntity> findByCategoryFiction();

    @Query(value = "select*FROM books p where p.category_id=4",nativeQuery = true)
    List<BookEntity> findByCategoryNonfiction();

    @Query(value = "select*FROM books p where p.category_id=5",nativeQuery = true)
    List<BookEntity> findByCategoryEbooks();
}
