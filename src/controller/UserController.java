package controller;

import model.User;
import service.UserService;
import service.BranchService;
import java.util.List;

import javax.swing.JOptionPane;

import view.UserListView;
import view.SettingView;
public class UserController {
    private UserListView userListView;
    private SettingView settingView;
    private UserService userService = new UserService();
    private BranchService branchService = new BranchService();
    public UserController(UserListView userListView,SettingView settingView) {
        // Constructor logic if needed
        this.userListView = userListView;
        this.settingView = settingView;

    }
    public void loadData(){
        List<User> users = userService.getUsersByBranch(Integer.parseInt(userListView.getSelectedBranch()));
        userListView.loadDataToTable(users);
    }

    public void loadcbdata(){
        userListView.loadcbdata(branchService.findAll());
    }

    public void processDeleteCustomer(){
        User currenuser = userService.getUserById(userListView.getSelectedUserId());
        try{
            if(userService.deleteUser(currenuser.getId())){
                userListView.showMessage("Xóa thành công !");
            }
            else{
                userListView.showMessage("Xóa thất bại !");
            }
        }
        catch (Exception e){
            userListView.showMessage("hãy chọn 1 người !");
            System.out.println(e.getMessage());
            return;
        }
    }

    public void processEditUser(){
        try{
            User currenuser = userService.getUserById(userListView.getSelectedUserId());
             
            userListView.initdialog(currenuser.getFullName(), currenuser.getEmail(), currenuser.getPhone(), currenuser.getRole());
            userListView.showDialog(true);
        }
        catch (Exception e){
            userListView.showMessage("hãy chọn 1 người !");
            return;
        }
    }

    public void processUpdateUser(){
        User user = userListView.getUpdatedUser();
        if(userService.updateUser(user)){
            userListView.showMessage("Cập nhật thành công !");
        }
        else{
            userListView.showMessage("Cập nhật thất bại !");
        }
    }

    public void processAddUser(){
        // User user = userListView.getNewUser();
        // if(userService.addUser(user)){
        //     userListView.showMessage("Thêm thành công !");
        // }
        // else{
        //     userListView.showMessage("Thêm thất bại !");
        // }
        
        User newUser = userListView.getNewUser();
        if(userService.addUser(newUser)){
            userListView.showMessage("Thêm thành công !");
        }
        else{
            userListView.showMessage("Thêm thất bại !");
        }
    }

    public void loadSettingData(User currentUser){

        //settingView.setUserInfo(null, null, null, null, null, null);
        String currenBranch;
        if(currentUser.getBranchId() == 0){
            currenBranch = null;
        }
        else{
            currenBranch = branchService.findbyId(currentUser.getBranchId()).getName();
        }
        //String currentBranch = branchService.findbyId(currentUser.getBranchId()).getName();
        settingView.setUserInfo(currentUser.getFullName(), currentUser.getUsername(), currentUser.getEmail(), currentUser.getPhone(), currentUser.getRole(),currenBranch);
    }

    public void processChangePassword(User currentUser){
        String newPassword = JOptionPane.showInputDialog(settingView,"Nhập mật khẩu mới :");
        try{
            if(newPassword.isEmpty()){
                settingView.showMessage("Mật khẩu mới không được để trống !");
                return;
            }
        }
        catch (Exception e){//shutup the terminal
            return;
        }
        

        try{
            if(userService.changePassword(currentUser.getId(), newPassword)){
                settingView.showMessage("Đổi mật khẩu thành công! , mật khẩu mới là : " + newPassword);
            }
            else{
                settingView.showMessage("Đổi mật khẩu thất bại !");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void processChangeEmail(User currentUser){
        String newEmail = JOptionPane.showInputDialog(settingView,"Nhập email mới :",currentUser.getEmail());
        try{
            if(newEmail.isEmpty()){
                settingView.showMessage("Email mới không được để trống !");
                return;
            }
        }
        catch (Exception e){//shutup the terminal
            return;
        }
        

        try{
            currentUser.setEmail(newEmail);
            if(userService.updateUser(currentUser)){//hack de khoi phai viet them method doi mk
                settingView.showMessage("Đổi email thành công! , email mới là : " + newEmail);
            }
            else{
                settingView.showMessage("Đổi email thất bại !");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void processChangePhone(User currentUser,String newPhone){
        
        try{
            
            if(newPhone.isEmpty() || newPhone == null){
                settingView.showMessage("Số điện thoại mới không được để trống !");
                newPhone = JOptionPane.showInputDialog(settingView,"Nhập số điện thoại mới :",currentUser.getPhone());
                return;
            }
            else if(newPhone.length() != 10){
                settingView.showMessage("Số điện thoại mới phải có 10 số !");
                newPhone = JOptionPane.showInputDialog(settingView,"Nhập số điện thoại mới :",currentUser.getPhone());
                return;
            }
            else if(!newPhone.matches("\\d+")){
                settingView.showMessage("Số điện thoại mới phải là số !");
                newPhone = JOptionPane.showInputDialog(settingView,"Nhập số điện thoại mới :",currentUser.getPhone());
                return;
            }
        }
        catch (Exception e){
            //shutup the terminal
        }
        
        try{
            currentUser.setPhone(newPhone);
            if(userService.updateUser(currentUser)){
                settingView.showMessage("Đổi số điện thoại thành công! , số điện thoại mới là : " + newPhone);
            }
            else{
                settingView.showMessage("Đổi số điện thoại thất bại !");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean processDeleteUser(User currentUser){
        int choice = 
        JOptionPane.showOptionDialog(settingView, "Xác nhận xóa tài khoản?", "Xác nhận", 
        2, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Có", "Không"}, "Có");
        if(choice == 0){
            if(userService.deleteUser(currentUser.getId())){
                System.out.println("xoa tk thanh cong");
                return true;
            }
            else{
                settingView.showMessage("Xóa tài khoản thất bại !");
                return false;
            }
        }
        else{
            return false;
        }   

        
    }
}
