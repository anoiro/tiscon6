package com.tiscon.form;







import javax.validation.constraints.Pattern;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

/**

 * 顧客が入力する見積もり情報を保持するクラス。

 *

 * @author Oikawa Yumi

 */

public class UserOrderForm<tel, g> {

    @NotBlank(message = "名前を入力してください")

    @Size(max=100,message="名前は100桁以内で入力してください")

    private String customerName;



    @NotBlank(message="電話番号を入力してください")

    @Size(max=11,min=10,message="電話番号は10桁から11桁の文字で入力してください")

    private String tel;



    //変更

    @NotBlank

    private String movingDate;

//







    @Email

    @NotBlank(message="eメールを入力してください")

    @Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$",message="eメールアドレスを正しく入力してください")

    private String email;





    private String oldPrefectureId;



    @NotBlank(message="番地を入力してください")

    private String oldAddress;





    private String newPrefectureId;



    @NotBlank(message="番地を入力してください")

    private String newAddress;




    @NotBlank(message="段ボールの数を入力してください")
    @DecimalMax(value = "200" ,message="{value}を超える段ボールは運べません")



    private String box;



    @NotBlank(message="ベッドの数を入力してください")

    private String bed;



    @NotBlank(message="自転車の数を入力してください")

    private String bicycle;



    @NotBlank(message="洗濯機の数を入力してください")

    @Size

    private String washingMachine;



    @NotNull

    private boolean washingMachineInstallation;



    public String getCustomerName() {

        return customerName;

    }



    public void setCustomerName(String customerName) {

        this.customerName = customerName;

    }



    public String getTel() {

        return tel;

    }



    public void setTel(String tel) {

        this.tel = tel;

    }
//変更

    public String getMovingDate() {

        return movingDate;

    }




    public void setMovingDate(String movingDate) {

        this.movingDate = movingDate;

    }

//


    public String getEmail() {

        return email;

    }



    public void setEmail(String email) {

        this.email = email;

    }



    public String getOldPrefectureId() {

        return oldPrefectureId;

    }



    public void setOldPrefectureId(String oldPrefectureId) {

        this.oldPrefectureId = oldPrefectureId;

    }



    public String getOldAddress() {

        return oldAddress;

    }



    public void setOldAddress(String oldAddress) {

        this.oldAddress = oldAddress;

    }



    public String getNewPrefectureId() {

        return newPrefectureId;

    }



    public void setNewPrefectureId(String newPrefectureId) {

        this.newPrefectureId = newPrefectureId;

    }



    public String getNewAddress() {

        return newAddress;

    }



    public void setNewAddress(String newAddress) {

        this.newAddress = newAddress;


    }




    public String getBox() {

        return box;

    }



    public void setBox(String box) {

        this.box = box;

    }



    public String getBed() {

        return bed;

    }



    public void setBed(String bed) {

        this.bed = bed;

    }



    public String getBicycle() {

        return bicycle;

    }



    public void setBicycle(String bicycle) {

        this.bicycle = bicycle;

    }



    public String getWashingMachine() {

        return washingMachine;

    }



    public void setWashingMachine(String washingMachine) {

        this.washingMachine = washingMachine;

    }



    public boolean getWashingMachineInstallation() {

        return washingMachineInstallation;

    }



    public void setWashingMachineInstallation(boolean washingMachineInstallation) {

        this.washingMachineInstallation = washingMachineInstallation;


    }}


