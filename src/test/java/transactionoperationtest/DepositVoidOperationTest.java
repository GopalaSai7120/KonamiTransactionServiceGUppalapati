package transactionoperationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.storage.HeapMemoryStorage;
import transactionmanagementservice.storage.StorageHelper;
import transactionmanagementservice.storageserviceoperation.PermissionService;
import transactionmanagementservice.transactiontableoperations.DepositOperation;
import transactionmanagementservice.transactiontableoperations.DepositVoidOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositVoidOperationTest {

    DepositVoidOperation depositVoidOperationTest =new DepositVoidOperation();

    @BeforeEach
    public void setupData() throws InvalidInputException, DataInvalidException {
        StorageHelper.populateTestData();
    }

    @AfterEach
    public void cleanUp(){
        StorageHelper.clearAllData();
    }

    @Test
    void depositVoidOperationSuccess() throws InvalidInputException, DataInvalidException {
        Integer personId = 1;
        Integer amount = 500;
        Integer transactionSequence = 1;
        PermissionService permissionService = new PermissionService();
        permissionService.givePermissionstoUser(1,4);
        DepositOperation depositOperation = new DepositOperation();
        depositOperation.depositOperation(TransactionTypeEnum.DEPOSIT, amount, personId, transactionSequence);
        depositVoidOperationTest.depositVoid(TransactionTypeEnum.DEPOSIT_VOID, amount, personId, transactionSequence);

        assertEquals(2,HeapMemoryStorage.TRANSACTION_TABLE.size());

        HeapMemoryStorage.TRANSACTION_TABLE.stream().findFirst().ifPresent(transactionTable -> {
            assertEquals(transactionTable.getAmount(),amount);
            assertEquals(transactionTable.getPersonId(),personId);
            assertEquals(transactionTable.getTransactionTypeEnum(),TransactionTypeEnum.DEPOSIT);
            assertEquals(transactionTable.getUniqueTransactionSequence(),transactionSequence);
        });

    }
}
