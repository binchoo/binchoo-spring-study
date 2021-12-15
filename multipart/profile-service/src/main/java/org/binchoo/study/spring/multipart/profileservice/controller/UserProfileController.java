package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.multipart.profileservice.controller.vo.UserProfileVO;
import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.binchoo.study.spring.multipart.profileservice.service.UserProfileService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RequestMapping("/profile")
@Controller
public class UserProfileController {

    private UserProfileService profileService;

    private ConversionService conversion;

    /**
     * GET /profile/jbinchoo
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String profileView(@PathVariable("id") String id, Model uiModel, HttpServletResponse response) {
        Optional<UserProfile> profile = profileService.findById(id);
        if (!profile.isPresent()) {
            response.setStatus(404);
            return "profile-not-found";
        }
        uiModel.addAttribute("profile", profile.get());
        return "profile";
    }

    /**
     * GET /profile/jbinchoo?update
     */
    @RequestMapping(value = "/{id}", params = "update", method = RequestMethod.GET)
    public String updateProfileView(@PathVariable("id") String id, Model uiModel) {
        Optional<UserProfile> profile = profileService.findById(id);
        if (!profile.isPresent()) {
            return "profile-not-found";
        }
        uiModel.addAttribute("profile", profile.get());
        return "profile-update";
    }

    /**
     * POST /profile/jbinchoo
     * with form url encoded
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateProfile(UserProfileVO profileVO) {
        String id = profileVO.getId();
        profileService.findById(id).ifPresent(it-> {
            UserProfile newProfile = conversion.convert(profileVO, UserProfile.class);
            if (newProfile.getPhoto() == null) {
                profileService.updateProfileWithoutPhoto(id, newProfile);
            } else {
                profileService.save(newProfile);
            }
        });
        return "redirect:/profile/" + id;
    }

    /**
     * GET /profile/jbinchoo/img
     */
    @RequestMapping(value = "/{id}/img", method = RequestMethod.GET)
    public Object getPhoto(@PathVariable String id) {
        byte[] photo = profileService.findById(id)
                .map(UserProfile::getPhoto)
                .orElse(null);
        return (photo == null)? ResponseEntity.notFound().build()
                : ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photo);
    }
}
