package transactionmanagementservice.model;

import transactionmanagementservice.exception.InvalidInputException;

public class PersonTable {
    private Integer personID;
    private String personName;

    public PersonTable(int personID, String personName){
        this.personID = personID;
        this.personName = personName;
    }

    public Integer getPersonID() {
        return personID;
    }

    public String getPersonName() {
        return personName;
    }
}
