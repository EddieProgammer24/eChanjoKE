package com.example.eChanjoKE;

import android.os.Parcel;
import android.os.Parcelable;

public class ChildDetails implements Parcelable {

    private String certNo;
    private String fullname;
    private String doB;
    private String gender;
    private String fathersName;
    private String mothersName;
    private String address;
    private String contact;
    private String weight;

    public String childId;

    // Default constructor required by Firebase
    public ChildDetails() {
        // Default constructor required for Firebase
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
    protected ChildDetails(Parcel in) {
        childId = in.readString();
        certNo = in.readString();
        fullname = in.readString();
        doB = in.readString();
        gender = in.readString();
        fathersName = in.readString();
        mothersName = in.readString();
        address = in.readString();
        contact = in.readString();
        weight = in.readString();
    }

    public static final Creator<ChildDetails> CREATOR = new Creator<ChildDetails>() {
        @Override
        public ChildDetails createFromParcel(Parcel in) {
            return new ChildDetails(in);
        }

        @Override
        public ChildDetails[] newArray(int size) {
            return new ChildDetails[size];
        }
    };

    public ChildDetails(String childId, String certNo, String fullname, String doB, String gender, String fathersName, String mothersName, String address, String contact, String weight) {
        this.childId = childId;
        this.certNo = certNo;
        this.fullname = fullname;
        this.doB = doB;
        this.gender = gender;
        this.fathersName = fathersName;
        this.mothersName = mothersName;
        this.address = address;
        this.contact = contact;
        this.weight = weight;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDoB() {
        return doB;
    }

    public void setDoB(String doB) {
        this.doB = doB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChildId() {
        return childId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(childId);
        dest.writeString(certNo);
        dest.writeString(fullname);
        dest.writeString(doB);
        dest.writeString(gender);
        dest.writeString(fathersName);
        dest.writeString(mothersName);
        dest.writeString(address);
        dest.writeString(contact);
        dest.writeString(weight);
    }
}
