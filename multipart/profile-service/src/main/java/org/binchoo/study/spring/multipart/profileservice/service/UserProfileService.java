package org.binchoo.study.spring.multipart.profileservice.service;

import lombok.AllArgsConstructor;
import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.binchoo.study.spring.multipart.profileservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class UserProfileService {

    UserProfileRepository repository;

    @Transactional(readOnly = true)
    public Optional<UserProfile> findById(String id) {
        return repository.findById(id);
    }

    public UserProfile save(UserProfile profile) {
        return repository.save(profile);
    }

    public void updateProfileWithoutPhoto(String id, UserProfile newProfile) {
        UserProfile updatingProfile = repository.findById(id).get();
        updatingProfile.setName(newProfile.getName());
        updatingProfile.setBirthDate(newProfile.getBirthDate());
        updatingProfile.setPhoneNumber(newProfile.getPhoneNumber());
        repository.save(updatingProfile);
    }
}
