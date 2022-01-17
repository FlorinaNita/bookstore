package sda.store.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.store.entities.CartEntity;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<CartEntity,Integer> {

    List<CartEntity> findAllByUserEntity_Username(String username);

    Long countAllByUserId(Integer id);

    CartEntity findByBookIdAndUserId(Integer bkId, Integer userId);
}


