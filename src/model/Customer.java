package model;

public final class Customer{
    
    private int customerID;
    private String username;
    private String passwordHash;
    private String citizenID;
    private String fullName;
    private String dateOfBirth;
    private String sex;
    private String nationality;
    private String placeOfOrigin;
    private String placeOfResidence;
    private String email;
    private String phoneNumber;
    private String userStatus;
    
    public Customer(){}
    
    public Customer(int customerID, String username, String passwordHash, String citizenID, String fullName, String dateOfBirth, String sex, String nationality, String placeOfOrigin, String placeOfResidence, String email, String phoneNumber){
        this.customerID = customerID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.citizenID = citizenID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.placeOfOrigin = placeOfOrigin;
        this.placeOfResidence = placeOfResidence;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public int getCustomerID(){ 
        return customerID; 
    }
    
    public void setCustomerID(int customerID){ 
        this.customerID = customerID; 
    }

    public String getUsername(){ 
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }

    public String getPasswordHash(){ 
        return passwordHash; 
    }
    
    public void setPasswordHash(String passwordHash){ 
        this.passwordHash = passwordHash;
    }

    public String getCitizenID(){ 
        return citizenID; 
    }
    
    public void setCitizenID(String citizenID){ 
        this.citizenID = citizenID; 
    }

    public String getFullName(){ 
        return fullName; 
    }
    
    public void setFullName(String fullName){ 
        this.fullName = fullName; 
    }

    public String getDateOfBirth(){ 
        return dateOfBirth; 
    }
    
    public void setDateOfBirth(String dateOfBirth){ 
        this.dateOfBirth = dateOfBirth; 
    }

    public String getSex(){ 
        return sex; 
    }
    
    public void setSex(String sex){ 
        this.sex = sex; 
    }

    public String getNationality(){ 
        return nationality;
    }
    
    public void setNationality(String nationality){ 
        this.nationality = nationality; 
    }

    public String getPlaceOfOrigin(){
        return placeOfOrigin; 
    }
    
    public void setPlaceOfOrigin(String placeOfOrigin){ 
        this.placeOfOrigin = placeOfOrigin; 
    }

    public String getPlaceOfResidence(){ 
        return placeOfResidence; 
    }
    
    public void setPlaceOfResidence(String placeOfResidence){ 
        this.placeOfResidence = placeOfResidence; 
    }

    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){ 
        this.email = email; 
    }

    public String getPhone(){ 
        return phoneNumber; 
    }
    
    public void setPhone(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public String getUserStatus(){
        return userStatus;
    }
    
    public void setUserStatus(String userStatus){
        this.userStatus = userStatus;
    }
    
    @Override
    public String toString(){
        return "Customer{" + "customerID=" + customerID + ", username='" + username + '\'' + ", passwordHash='[HIDDEN]'" + ", citizenID='" + citizenID + '\'' + ", fullName='" + fullName + '\'' + ", dateOfBirth='" + dateOfBirth + '\'' + ", sex='" + sex + '\'' + ", nationality='" + nationality + '\'' + ", placeOfOrigin='" + placeOfOrigin + '\'' + ", placeOfResidence='" + placeOfResidence + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + ", userStatus='" + userStatus + '\'' + '}';
    }
}
    
