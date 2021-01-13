package ee.taltech.critter.service;

import ee.taltech.critter.exception.ApiNotFoundException;
import ee.taltech.critter.model.Platform;
import ee.taltech.critter.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {
    @Autowired
    private PlatformRepository platformRepository;

    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    public Platform findById(Long id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Platform not found."));
    }

    public Platform save(Platform platform) {
        return platformRepository.save(platform);
    }

    public void delete(Long id) {
        platformRepository.deleteById(id);
    }
}
