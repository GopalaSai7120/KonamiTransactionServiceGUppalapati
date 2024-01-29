package transactionmanagementservice.transactiontableoperations.helper;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.UserAccessException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.PersonTable;
import transactionmanagementservice.model.TransactionPermission;
import transactionmanagementservice.model.TransactionTable;
import transactionmanagementservice.model.TransactionTypeTable;
import transactionmanagementservice.model.constants.TransactionTypeEnum;

import java.util.Optional;

public class CommonOperations {
    public void validatePerson(Integer personId) throws DataInvalidException {
        Optional<PersonTable> foundPerson = HeapMemoryStorage.PERSON_TABLE.stream()
                .filter(personTableObj -> personTableObj.getPersonID().equals(personId))
                .findFirst();

        if (!foundPerson.isPresent()) {
            throw new DataInvalidException("PERSON_ID:"+personId+" not found in the System. Please provide a valid PERSON_ID.");
        }
    }

    public Integer getTransactionTypeId(TransactionTypeEnum transactionTypeEnum) {
        Optional<TransactionTypeTable> getTransactionTypeObj = HeapMemoryStorage.TRANSACTION_TYPE_TABLE.stream()
                .filter(transactionTypeTableObj -> transactionTypeTableObj.getTransactionTypeEnum() == transactionTypeEnum)
                .findFirst();

        return getTransactionTypeObj.map(TransactionTypeTable::getTransactionTypeId).orElse(null);
    }

    public void hasAccesstoPermission(Integer personId, Integer transactionTypeId) throws UserAccessException {
        Optional<TransactionPermission> userPermission = HeapMemoryStorage.PERSON_CAN_DO.stream()
                .filter(userPermissionObj -> userPermissionObj.getPersonId().equals(personId) && userPermissionObj.getTransactionTypeId().equals(transactionTypeId))
                .findFirst();

        Optional<PersonTable> foundPerson = HeapMemoryStorage.PERSON_TABLE.stream()
                .filter(personTableObj -> personTableObj.getPersonID().equals(personId))
                .findFirst();

        Optional<TransactionTypeTable> getTransactionTypeObj = HeapMemoryStorage.TRANSACTION_TYPE_TABLE.stream()
                .filter(transactionTypeTableObj -> transactionTypeTableObj.getTransactionTypeId() == transactionTypeId)
                .findFirst();
        String personName = foundPerson.map(PersonTable::getPersonName).orElse("Person");
        String transactionName = String.valueOf(getTransactionTypeObj.map(TransactionTypeTable::getTransactionTypeEnum).orElse(null));

        if (!userPermission.isPresent()) {
            throw new UserAccessException(personName+" does not have permission to perform "+transactionName+" operation.");
        }
    }

    public void inputIsInvalid(Integer amount, Integer personId, Integer transactionSequence) throws InvalidInputException {
        if(amount == null || amount<=0){
            throw new InvalidInputException("Operation failed: please provide valid input-Amount cant be null or less than zero");
        }
        if(personId == null || personId<=0){
            throw new InvalidInputException("Operation failed: please provide valid PERSON_ID cant be null or less than zero");
        }
        if(transactionSequence == null || transactionSequence<=0){
            throw new InvalidInputException("Operation failed: please provide valid Transaction sequence cant be null or less than zero");
        }
    }



    public void checkifTransactionSequenceIsvalidForVoid(Integer transactionSequence) throws DataInvalidException {
        Optional<TransactionTable> transactionTable = HeapMemoryStorage.TRANSACTION_TABLE.stream()
                .filter(transactionTableObj -> (transactionTableObj.getUniqueTransactionSequence() == transactionSequence && transactionTableObj.getTransactionTypeEnum() == TransactionTypeEnum.DEPOSIT))
                .findFirst();
        if(!transactionTable.isPresent()){
            throw new DataInvalidException("Operation failed: please provide valid Unique Transaction Sequence:"+transactionSequence+" cannot find in transaction history");
        }
    }
}
