package controller;

import model.Branch;
import service.BranchService;
import view.BranchListView;
import java.util.List;

public class BranchController {
    private BranchListView branchListView;
    private BranchService branchService = new BranchService();

    public BranchController(BranchListView branchListView) {
        this.branchListView = branchListView;
    }

    public void loadData() {
        List<Branch> branches = branchService.findAll();
        branchListView.loadDataToTable(branches);
    }

    public void processDeleteBranch() {
        String selectedId = branchListView.getSelectedBranchId();
        if (selectedId == null) {
            branchListView.showMessage("Hãy chọn một chi nhánh!");
            return;
        }
        int id = Integer.parseInt(selectedId);
        if (branchService.delete(id)) {
            branchListView.showMessage("Xóa thành công!");
        } else {
            branchListView.showMessage("Xóa thất bại!");
        }
    }

    public void processEditBranch() {
        String selectedId = branchListView.getSelectedBranchId();
        if (selectedId == null) {
            branchListView.showMessage("Hãy chọn một chi nhánh!");
            return;
        }
        Branch branch = branchService.findbyId(Integer.parseInt(selectedId));
        branchListView.initdialog(branch.getName(), branch.getAddress(), branch.getPhone(), branch.isActive());
        branchListView.showDialog(true);
    }

    public void processUpdateBranch() {
        Branch branch = branchListView.getUpdatedBranch();
        branch.setActive(branchListView.getUpdatedActive());
        if (branchService.update(branch)) {
            branchListView.showMessage("Cập nhật thành công!");
        } else {
            branchListView.showMessage("Cập nhật thất bại!");
        }
    }

    public void processAddBranch() {
        Branch newBranch = branchListView.getNewBranch();
        newBranch.setActive(true);
        if (branchService.save(newBranch)) {
            branchListView.showMessage("Thêm thành công!");
        } else {
            branchListView.showMessage("Thêm thất bại!");
        }
    }

}
