package transactionmanagementservice.storageserviceoperation;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.PersonTable;

import java.util.Optional;

public class PersonService {

    public void addPerson(Integer personId, String personName) throws InvalidInputException, DataInvalidException {
        if(personId == null || personId<=0 ){
            throw new InvalidInputException("Please provide a valid PERSON_ID");
        }
        if(personName == null || personName.isEmpty()){
            throw new InvalidInputException("Please provide a valid PERSON_NAME");
        }

        Optional<PersonTable> foundPerson = HeapMemoryStorage.PERSON_TABLE.stream()
                .filter(personTableObj -> personTableObj.getPersonID() == personId)
                .findFirst();
        if(foundPerson.isPresent()){
            throw new DataInvalidException("Failed to insert "+personName+" PERSON_ID already exists,Please provide a valid PERSON_ID");
        }
        PersonTable personTable = new PersonTable(personId, personName);
        HeapMemoryStorage.PERSON_TABLE.add(personTable);
    }
}
