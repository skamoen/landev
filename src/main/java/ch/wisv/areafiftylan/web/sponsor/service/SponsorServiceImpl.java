package ch.wisv.areafiftylan.web.sponsor.service;

import ch.wisv.areafiftylan.web.sponsor.model.Sponsor;
import ch.wisv.areafiftylan.web.sponsor.model.SponsorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SponsorServiceImpl implements SponsorService {

    private SponsorRepository sponsorRepository;

    @Autowired
    public SponsorServiceImpl(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public Sponsor createSponsor(Sponsor sponsor) {
        return sponsorRepository.saveAndFlush(sponsor);
    }

    @Override
    public Collection<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    @Override
    public Collection<Sponsor> getAllSponsorsOfType(SponsorType type) {
        return sponsorRepository.findByType(type);
    }

    @Override
    public void deleteSponsor(Long sponsorId) {
        sponsorRepository.delete(sponsorId);
    }

    @Override
    public void deleteAllSponsors() {
        sponsorRepository.deleteAll();
    }
}
