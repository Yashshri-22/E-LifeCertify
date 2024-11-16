package com.example.project;

public class ShowAllModel {
    private int profile_image;
    private String name;
    private String ppo_number;
    private String mobileNumber;
    private String designation;
    private String dateOfBirth;
    private String dateOfJoining;
    private String dateOfRetirement;
    private String typeOfRetirement;
    private String nomineeName;
    private String height;
    private String birthMark;
    private String bankName;
    private String bankAccountNumber;
    private String address;
    private String adharNumber;
    private String gender;
    private String nomineeType;

    public ShowAllModel(int profile_image, String name, String ppo_number, String mobileNumber, String designation, String dateOfBirth, String dateOfJoining, String dateOfRetirement, String typeOfRetirement, String nomineeName, String height, String birthMark, String bankName, String bankAccountNumber, String address, String adharNumber, String gender, String nomineeType) {
        this.profile_image = profile_image;
        this.name = name;
        this.ppo_number = ppo_number;
        this.mobileNumber = mobileNumber;
        this.designation = designation;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoining = dateOfJoining;
        this.dateOfRetirement = dateOfRetirement;
        this.typeOfRetirement = typeOfRetirement;
        this.nomineeName = nomineeName;
        this.height = height;
        this.birthMark = birthMark;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.address = address;
        this.adharNumber = adharNumber;
        this.gender = gender;
        this.nomineeType = nomineeType;
    }


    public int getProfile_image() {
        return profile_image;
    }

    public String getName() {
        return name;
    }

    public String getPpo_number() {
        return ppo_number;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public String getDateOfRetirement() {
        return dateOfRetirement;
    }

    public String getTypeOfRetirement() {
        return typeOfRetirement;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public String getHeight() {
        return height;
    }

    public String getBirthMark() {
        return birthMark;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getAdharNumber() {
        return adharNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getNomineeType() {
        return nomineeType;
    }

}
