package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Pitch;
import service.BookingService;
import service.BranchService;
import service.CustomerService;
import service.PitchService;
import view.ManageFieldsView;
import view.PitchListView;

public class PitchController {
    private ManageFieldsView manageFieldsView;
    private PitchListView pitchListView;
    private PitchService pitchService = new PitchService();
    private BookingService bookingService = new BookingService();
    private CustomerService customerService = new CustomerService();
    private BranchService branchService = new BranchService();

    public PitchController(PitchListView pitchListView, ManageFieldsView manageFieldsView) {
        this.pitchListView = pitchListView;
        this.manageFieldsView = manageFieldsView;
    }

    public void reloadTimeslotTable() {
        List<Map<String, Object>> data = bookingService.getAllBookingsMap()
            .stream()
            .filter(b -> (int) b.get("pitchId") == manageFieldsView.getselectedPitch().getId())
            .collect(Collectors.toList());
        manageFieldsView.reloadTable(data);
    }

    public void loadDataForManageFieldsView() {
        manageFieldsView.loadPitches(pitchService.getAllPitches());
        manageFieldsView.loadCustomers(customerService.getAllCustomers());
        reloadTimeslotTable();
    }

    public void loadPitchStatusdata(){
        //pitchListView.loadComboboxData(branchService.findAll());
        if(pitchListView.getSelectedBranchId()!= -1) {
            List<Pitch> pitchList = pitchService.getPitchesByBranch(pitchListView.getSelectedBranchId());
            pitchListView.loadDataToTable(pitchList);
        }
    }

    public void loadComboBoxData() {
        pitchListView.loadComboboxData(branchService.findAll());
    }
        
    

    public void processAddPitch(){
        Pitch newPitch = pitchListView.getNewPitch();
        if (newPitch != null) {
            if (pitchService.addPitch(newPitch)) {
                loadPitchStatusdata();
                pitchListView.showMessage("Thêm sân thành công!");
                pitchListView.showDialog(false);
            } else {
                pitchListView.showMessage("Thêm sân thất bại!");
                
            }
        } else {
            pitchListView.showMessage("Vui lòng điền đầy đủ thông tin sân!");
        }
    }

    public void processDeletePitch() {
        int selectedPitchId = pitchListView.getSelectedPitchId();
        if (selectedPitchId != -1) {
            if (pitchService.deletePitch(selectedPitchId)) {
                loadPitchStatusdata();
                pitchListView.showMessage("Xóa sân thành công!");
            } else {
                pitchListView.showMessage("Xóa sân thất bại!");
            }
        } 
    }

    public void displayEditPitch() {
        int selectedPitchId = pitchListView.getSelectedPitchId();
        if (selectedPitchId != -1) {
            Pitch selectedPitch = pitchService.getPitchById(selectedPitchId);
            if (selectedPitch != null) {
                pitchListView.initDialog(selectedPitch.getName(), selectedPitch.getType(), 
                                            String.valueOf(selectedPitch.getPricePerHour()), 
                                            selectedPitch.getDescription(), 
                                            String.valueOf(selectedPitch.getBranchId()),selectedPitch.isActive());
            } else {
                pitchListView.showMessage("Không tìm thấy sân với ID đã chọn!");
            }
        }
    }

    public void processUpdatePitch() {
        Pitch updatedPitch = pitchListView.getUpdatedPitch();
        if (updatedPitch != null) {
            if (pitchService.updatePitch(updatedPitch)) {
                loadPitchStatusdata();
                pitchListView.showMessage("Cập nhật sân thành công!");
                pitchListView.showDialog(false);
            } else {
                pitchListView.showMessage("Cập nhật sân thất bại!");
            }
        } else {
            pitchListView.showMessage("Vui lòng điền đầy đủ thông tin sân!");
        }
    }
}