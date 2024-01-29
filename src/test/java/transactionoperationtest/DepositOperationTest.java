package transactionoperationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.storage.StorageHelper;
import transactionmanagementservice.transactiontableoperations.DepositOperation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepositOperationTest {

    DepositOperation depositOperation = new DepositOperation();

    @BeforeEach
    public void setupData() throws InvalidInputException, DataInvalidException {
        StorageHelper.populateTestData();
    }

    @AfterEach
    public void cleanUp(){
        StorageHelper.clearAllData();
    }

    @Test
    void depositOperationSuccess() throws InvalidInputException {
        Integer personId = 1;
        Integer amount = 500;
        Integer transactionSequence = 1;
        depositOperation.depositOperation(TransactionTypeEnum.DEPOSIT, amount, personId, transactionSequence);
        assertEquals(HeapMemoryStorage.TRANSACTION_TABLE.size(),1);

        HeapMemoryStorage.TRANSACTION_TABLE.stream().findFirst().ifPresent(transactionTable -> {
            assertEquals(transactionTable.getAmount(),amount);
            assertEquals(transactionTable.getPersonId(),personId);
            assertEquals(transactionTable.getTransactionTypeEnum(),TransactionTypeEnum.DEPOSIT);
            assertEquals(transactionTable.getUniqueTransactionSequence(),transactionSequence);
        });

    }
}
