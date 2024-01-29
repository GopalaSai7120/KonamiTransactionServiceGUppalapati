package transactionmanagementservice.storage;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.PersonTable;
import transactionmanagementservice.model.TransactionPermission;
import transactionmanagementservice.model.TransactionTable;
import transactionmanagementservice.model.TransactionTypeTable;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.storageserviceoperation.PermissionService;
import transactionmanagementservice.storageserviceoperation.PersonService;
import transactionmanagementservice.storageserviceoperation.TransactionTypeTableService;

import java.util.List;

public class StorageHelper {

    public static void populateTestData() throws InvalidInputException, DataInvalidException {
        PersonService personService = new PersonService();
        TransactionTypeTableService transactionTypeTableService = new TransactionTypeTableService();
        PermissionService permissionService = new PermissionService();
        personService.addPerson(1,"Joe");
        personService.addPerson(2,"Tom");
        personService.addPerson(3,"Steve");

        transactionTypeTableService.addTransactionType(1, TransactionTypeEnum.DEPOSIT);
        transactionTypeTableService.addTransactionType(2, TransactionTypeEnum.WITHDRAW);
        transactionTypeTableService.addTransactionType(3, TransactionTypeEnum.ADJUST);
        transactionTypeTableService.addTransactionType(4, TransactionTypeEnum.DEPOSIT_VOID);

        permissionService.givePermissionstoUser(1,1);
        permissionService.givePermissionstoUser(1,2);
        permissionService.givePermissionstoUser(2,3);
    }

    public static void clearAllData() {
        HeapMemoryStorage.PERSON_TABLE.clear();
        HeapMemoryStorage.PERSON_CAN_DO.clear();
        HeapMemoryStorage.TRANSACTION_TABLE.clear();
        HeapMemoryStorage.TRANSACTION_TYPE_TABLE.clear();
    }

    public static void printDataTables() {

        List<PersonTable> PERSON_TABLE = HeapMemoryStorage.PERSON_TABLE;
        List<TransactionTypeTable> TRANSACTION_TYPE_TABLE = HeapMemoryStorage.TRANSACTION_TYPE_TABLE;
        List<TransactionPermission> PERSON_CAN_DO = HeapMemoryStorage.PERSON_CAN_DO;

        System.out.println("PERSON_TABLE:");
        System.out.println("_______________________________");
        System.out.println("| PERSON_ID    | PERSON_NAME  |");
        System.out.println("|--------------|--------------|");
        PERSON_TABLE.stream().forEach(personObj -> System.out.printf("| %-12s | %-12s |\n", personObj.getPersonID(), personObj.getPersonName()));
        System.out.println("-------------------------------");

        System.out.println("TRANSACTION_TYPE_TABLE:");
        System.out.println("______________________________________________");
        System.out.println("| TRANSACTION_TYPE_ID    | TRANSACTION_NAME  |");
        System.out.println("|------------------------|-------------------|");
        TRANSACTION_TYPE_TABLE.stream().forEach(transactionTypeTableObj -> System.out.printf("| %-22s | %-17s |\n", transactionTypeTableObj.getTransactionTypeId(), transactionTypeTableObj.getTransactionTypeEnum()));
        System.out.println("----------------------------------------------");

        System.out.println("PERSON_CAN_DO_TABLE:");
        System.out.println("______________________________________");
        System.out.println("| PERSON_ID    | TRANSACTION_TYPE_ID |");
        System.out.println("|--------------|---------------------|");
        PERSON_CAN_DO.stream().forEach(transactionPermissionObj -> System.out.printf("| %-12s | %-19s |\n", transactionPermissionObj.getPersonId(), transactionPermissionObj.getTransactionTypeId()));
        System.out.println("--------------------------------------");
    }

    public static void transactionTable() {

        List<TransactionTable> TRANSACTION_TABLE = HeapMemoryStorage.TRANSACTION_TABLE;
        System.out.println("TRANSACTION_TABLE:");
        System.out.println("__________________________________________________________________________________________");
        System.out.println("| Transaction ID  | Transaction Name  | Amount | Person ID | Unique Transaction Sequence |");
        System.out.println("|-----------------|-------------------|--------|-----------|-----------------------------|");
        TRANSACTION_TABLE.stream().forEach(transactionTableObj -> System.out.printf("| %-15s | %-17s | %-6s | %-9s | %-27s |\n",
                transactionTableObj.getUniqueTransactionId(),
                transactionTableObj.getTransactionTypeEnum(),
                transactionTableObj.getAmount(),
                transactionTableObj.getPersonId(),
                transactionTableObj.getUniqueTransactionSequence()));
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
