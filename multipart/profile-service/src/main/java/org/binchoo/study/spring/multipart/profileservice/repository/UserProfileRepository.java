package org.binchoo.study.spring.multipart.profileservice.repository;

import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, String> {
}
