package transactionmanagementservice.transactiontableoperations;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.UserAccessException;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.TransactionTable;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.transactiontableoperations.helper.CommonOperations;

public class DepositOperation {

    /**
     *DESIGN APPROACH:
     *DEPOSIT Transaction:
     * 1)Verify Input Parameters are passed correctly in CORRECT format (Data type validation)
     * 2)Verify if PERSON exists in the system by using PERSON_ID in PERSON_TABLE
     * 3)Verify TRANSACTION_TYPE by Transaction Name in TRANSACTION_TYPE_TABLE
     *   if User is trying to perform valid transaction
     * 4)Verify if Person has access to perform DEPOSIT transaction in PERSON_CAN_DO Table
     * 5)if all conditions are met proceed with Inserting DEPOSIT_VOID Transaction
     * */

    CommonOperations commonOperations = new CommonOperations();

    public void depositOperation(TransactionTypeEnum transactionTypeEnum, Integer amount, Integer personId, Integer transactionSequence) throws InvalidInputException {
        try {
            commonOperations.inputIsInvalid(amount, personId, transactionSequence);
            commonOperations.validatePerson(personId);

            Integer transactionTypeId = commonOperations.getTransactionTypeId(transactionTypeEnum);
            if (transactionTypeId == null) {
                throw new IllegalStateException("Transaction: "+transactionTypeEnum+" type not found.");
            }

            commonOperations.hasAccesstoPermission(personId, transactionTypeId);

            Integer transactionId = HeapMemoryStorage.TRANSACTION_TABLE.size();
            TransactionTable transactionTable = new TransactionTable(100 + transactionId, TransactionTypeEnum.DEPOSIT, amount, personId, transactionSequence);
            HeapMemoryStorage.TRANSACTION_TABLE.add(transactionTable);
            System.out.println("Operation Success: PERSON_ID:" + personId + " has access to perform DEPOSIT operation");

        } catch (UserAccessException | DataInvalidException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

}
