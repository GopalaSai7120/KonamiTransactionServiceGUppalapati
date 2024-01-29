package transactionmanagementservice.storageserviceoperation;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.model.TransactionTypeTable;

import java.util.Optional;

public class TransactionTypeTableService {

    public void addTransactionType(Integer transactionTypeId, TransactionTypeEnum transactionTypeEnum) throws InvalidInputException, DataInvalidException {
        if(transactionTypeId==null || transactionTypeId <= 0){
            throw new InvalidInputException("Please provide valid input for TRANSACTION_TYPE_ID");
        }

        Optional<TransactionTypeTable> transactionTypefound = HeapMemoryStorage.TRANSACTION_TYPE_TABLE.stream()
                .filter(transactionTypeTableObj -> transactionTypeTableObj.getTransactionTypeId() == transactionTypeId)
                .findFirst();

        if(transactionTypefound.isPresent()){
            throw new DataInvalidException("Failed to insert "+transactionTypeEnum+" TRANSACTION_TYPE_ID already exists,Please provide a valid TRANSACTION_TYPE_ID");
        }
        TransactionTypeTable transactionTypeTable = new TransactionTypeTable(transactionTypeId,transactionTypeEnum);
        HeapMemoryStorage.TRANSACTION_TYPE_TABLE.add(transactionTypeTable);
    }
}
