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

    @GetMapping("/users/{userId}/profiles")
    public Profile getProfileByUserId(@PathVariable Long userId) {
        return profileService.findByUser(userId);
    }

    @PostMapping("/users/{userId}/profiles")
    public ResponseEntity<Object> addNewProfile(@PathVariable Long userId, @Valid @RequestBody Profile profile) {
        User user = userService.findById(userId);
        profile.setUser(user);

        Profile profileSaved = profileService.save(profile);
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(profileSaved.getId()).
                toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("profiles/{profileId}")
    public long deleteProfile(@PathVariable Long id) {
        return profileService.deleteProfileById(id);
    }

    @PostMapping("/update-profile/{profileId}")
    public void updateProfile(@PathVariable Long profileId, @RequestBody Profile profile) {

        Profile profileToUpdate = profileService.findById(profileId);
        profileToUpdate.setAddressLatitude(profile.getAddressLatitude());
        profileToUpdate.setAddressLongitude(profile.getAddressLongitude());
        profileToUpdate.setUser(profile.getUser());
        profileToUpdate.setDriver(profile.isDriver());
        profileToUpdate.setName(profile.getName());
        profileToUpdate.setPhone(profile.getPhone());

        profileService.save(profileToUpdate);

    }


}
