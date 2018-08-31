package keysight.ixia.hackathon.ixride_server.controller;


import keysight.ixia.hackathon.ixride_server.model.Profile;
import keysight.ixia.hackathon.ixride_server.model.User;
import keysight.ixia.hackathon.ixride_server.service.ProfileService;
import keysight.ixia.hackathon.ixride_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @Autowired
    private ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("/profiles")
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        profileService.findAll().forEach(profiles::add);
        return profiles;

    }

    @GetMapping("/profiles/{profileId}")
    public Profile getProfileById(@PathVariable Long id) {
        return profileService.findById(id);

    }

    @GetMapping("/users/{userId}/profile")
    public Profile getProfileByUserId(@PathVariable long userId) {
        User user = userService.findById(userId);
        return profileService.findByUser(user);
    }

    @PostMapping("/users/{userId}/profiles")
    public Profile addNewProfile(@PathVariable Long userId, @Valid @RequestBody Profile profile) {
        User user = userService.findById(userId);
        profile.setUser(user);

        return profileService.save(profile);

    }

    @DeleteMapping("profiles/{profileId}")
    public long deleteProfile(@PathVariable Long id) {
        return profileService.deleteProfileById(id);
    }

    @PutMapping("/users/{userId}/profile")
    public Profile updateProfile(@RequestBody Profile profile, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Profile profileToUpdate = profileService.findByUser(user);
        if (profileToUpdate != null) {
            profileToUpdate.setAddressLatitude(profile.getAddressLatitude());
            profileToUpdate.setAddressLongitude(profile.getAddressLongitude());
            profileToUpdate.setUser(user);
            profileToUpdate.setDriver(profile.isDriver());
            profileToUpdate.setName(profile.getName());
            profileToUpdate.setPhone(profile.getPhone());
        }

        return profileService.save(profileToUpdate);

    }


}
