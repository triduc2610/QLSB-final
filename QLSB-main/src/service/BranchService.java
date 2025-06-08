package service;
import DAO.impl.BranchDAOImpl;
import model.Branch;
import java.util.List;
public class BranchService {
    private BranchDAOImpl branchDAO = new BranchDAOImpl();
    public Branch findbyId(int id){
        return branchDAO.findById(id);
    }

    public List<Branch> findAll(){
        return branchDAO.findAll();
    }

    public boolean save(Branch branch){
        return branchDAO.save(branch);
    }

    public boolean update(Branch branch){
        return branchDAO.update(branch);
    }   

    public boolean delete(int id){
        return branchDAO.delete(id);
    }

    public List<Branch> findActive(){
        return branchDAO.findActive();
    }

    public Branch findByPitch(int PitchId){
        return branchDAO.findByPitch(PitchId);
    }
}
