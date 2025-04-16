package com.fortune.repository;

import com.fortune.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByIdxAndDelYn(int userIdx, boolean isDel);

	Optional<Users> findByUserIdAndDelYn(String userId, boolean isDel);

	boolean existsByUserIdAndDelYn(String userId, boolean isDel);
}
