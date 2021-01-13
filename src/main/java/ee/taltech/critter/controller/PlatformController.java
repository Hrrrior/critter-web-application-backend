package ee.taltech.critter.controller;

import ee.taltech.critter.model.Platform;
import ee.taltech.critter.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("platforms")
@RestController
public class PlatformController {
    @Autowired
    private PlatformService platformService;

    /*
    GET. Get all platforms.
     */
    @GetMapping
    public List<Platform> getAllPlatforms() {
        return platformService.findAll();
    }

    /*
    GET. Get platform with ID.
     */
    @GetMapping("{id}")
    public Platform getPlatformById(@PathVariable Long id) {
        return platformService.findById(id);
    }

    /*
    POST. Save new platform to db.
     */
    @PostMapping
    public Platform savePlatform(@RequestBody Platform platform) {
        return platformService.save(platform);
    }

    /*
    DELETE. Delete platform with ID.
     */
    @DeleteMapping("{id}")
    public void deletePlatformById(@PathVariable Long id) {
        platformService.delete(id);
    }
}
