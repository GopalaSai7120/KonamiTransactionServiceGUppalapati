package transactionmanagementservice.storageserviceoperation;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.PersonTable;
import transactionmanagementservice.model.TransactionPermission;
import transactionmanagementservice.model.TransactionTypeTable;

import java.util.Optional;

public class PermissionService {

    public void givePermissionstoUser(Integer personId, Integer transactionTypeId) throws InvalidInputException, DataInvalidException {
        if(personId<=0 || personId == null){
            throw new InvalidInputException("Please provide a valid PERSON_ID");
        }
        if(transactionTypeId == null || transactionTypeId <= 0){
            throw new InvalidInputException("Please provide a valid TRANSACTION_ID");
        }
        Optional<TransactionPermission> userPermission = HeapMemoryStorage.PERSON_CAN_DO.stream()
                .filter(userPermissionObj -> (userPermissionObj.getPersonId() == personId && userPermissionObj.getTransactionTypeId() == transactionTypeId))
                .findFirst();

        if(userPermission.isPresent()){
            System.out.println("USER already has Permission");
            return;
        }

        Optional<PersonTable> foundPerson = HeapMemoryStorage.PERSON_TABLE.stream()
                .filter(personTableObj -> personTableObj.getPersonID() == personId)
                .findFirst();
        Optional<TransactionTypeTable> transactionTypefound = HeapMemoryStorage.TRANSACTION_TYPE_TABLE.stream()
                .filter(transactionTypeTableObj -> transactionTypeTableObj.getTransactionTypeId() == transactionTypeId)
                .findFirst();

        if(foundPerson.isPresent() && transactionTypefound.isPresent()){
            TransactionPermission transactionPermission = new TransactionPermission(personId,transactionTypeId);
            HeapMemoryStorage.PERSON_CAN_DO.add(transactionPermission);
        }else {
           throw new DataInvalidException("Failed to set Access : PERSON / TRANSACTION_TYPE is not present please give valid permissions to set");
        }

    }
}
