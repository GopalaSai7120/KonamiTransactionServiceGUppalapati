package transactionmanagementservice.transactiontableoperations;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.UserAccessException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.TransactionTable;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.transactiontableoperations.helper.CommonOperations;

import java.util.Optional;

public class DepositVoidOperation {

    /**
     *DESIGN APPROACH:
     *DEPOSIT_VOID Transaction:
     * 1)Verify Input Parameters are passed correctly in CORRECT format (Data type validation)
     * 2)Verify if PERSON exists in the system by using PERSON_ID in PERSON_TABLE
     * 3)Verify TRANSACTION_TYPE by Transaction Name in TRANSACTION_TYPE_TABLE
     *   if User is trying to perform valid transaction
     * 4)Verify if Person has access to perform DEPOSIT_VOID transaction in PERSON_CAN_DO Table
     * 5)Verify if Unique Transaction Sequence exists in history by checking
     *   sequence number , DEPOSIT transaction type and amount
     * 6)if all conditions are met proceed with Inserting DEPOSIT_VOID Transaction
     * */

    CommonOperations commonOperations = new CommonOperations();

    public void depositVoid(TransactionTypeEnum transactionTypeEnum, Integer amount, Integer personId, Integer transactionSequence) throws InvalidInputException {
        try {
            //Check input parameters
            commonOperations.inputIsInvalid(amount, personId, transactionSequence);
            //Check if Person exists in DB
            commonOperations.validatePerson(personId);

            //Check Transaction Type
            Integer transactionTypeId = commonOperations.getTransactionTypeId(transactionTypeEnum);
            if (transactionTypeId == null) {
                throw new IllegalStateException("Transaction: "+transactionTypeEnum+" type not found.");
            }

            //Check User access
            commonOperations.hasAccesstoPermission(personId, transactionTypeId);

            //Check if sequence number is valid and a deposit was made with that amount
            checkifTransactionSequenceIsvalidforVoid(transactionSequence,amount);

            Integer transactionId = HeapMemoryStorage.TRANSACTION_TABLE.size();
            TransactionTable transactionTable = new TransactionTable(100 + transactionId, TransactionTypeEnum.DEPOSIT_VOID, amount, personId, transactionSequence);
            HeapMemoryStorage.TRANSACTION_TABLE.add(transactionTable);
            System.out.println("Operation Success: PERSON_ID:" + personId + " has access to perform DEPOSIT_VOID operation");

        } catch (DataInvalidException | UserAccessException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }

    }

    //Check if sequence number is valid and a deposit was made with that amount
    private void checkifTransactionSequenceIsvalidforVoid(Integer transactionSequence,Integer amount) throws DataInvalidException {
        Optional<TransactionTable> transactionTable = HeapMemoryStorage.TRANSACTION_TABLE.stream()
                .filter(transactionTableObj -> (transactionTableObj.getUniqueTransactionSequence() == transactionSequence &&
                        transactionTableObj.getTransactionTypeEnum() == TransactionTypeEnum.DEPOSIT &&
                        transactionTableObj.getAmount() == amount))
                .findFirst();
        if(!transactionTable.isPresent()){
            throw new DataInvalidException("Operation failed: please provide valid Unique Transaction Sequence:"+transactionSequence+" cannot find in transaction history fro deposit");
        }
    }
}
