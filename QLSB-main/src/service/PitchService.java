package service;


import DAO.PitchDAO;
import DAO.impl.PitchDAOImpl;
import model.Pitch;

import java.util.List;

public class PitchService {
    private PitchDAO pitchDAO;
    
    public PitchService() {
        this.pitchDAO = new PitchDAOImpl();
    }
    
    public Pitch getPitchById(int id) {
        return pitchDAO.findById(id);
    }
    
    public List<Pitch> getAllPitches() {
        return pitchDAO.findAll();
    }
    
    public List<Pitch> getPitchesByBranch(int branchId) {
        return pitchDAO.findByBranch(branchId);
    }
    
    public List<Pitch> getPitchesByType(String type) {
        return pitchDAO.findByType(type);
    }
    
    public boolean addPitch(Pitch pitch) {
        return pitchDAO.save(pitch);
    }
    
    public boolean updatePitch(Pitch pitch) {
        return pitchDAO.update(pitch);
    }
    
    public boolean deletePitch(int id) {
        return pitchDAO.delete(id);
    }

    public List<Pitch> getActivePitches(int branchId) {
        return pitchDAO.findActivePitchs(branchId);
    }

    public List<Pitch> getAllActivePitchs(){
        return pitchDAO.findAllActivePitchs();
    }
}
