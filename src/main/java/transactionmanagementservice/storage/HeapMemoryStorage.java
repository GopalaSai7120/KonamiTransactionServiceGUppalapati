package transactionmanagementservice.storage;

import transactionmanagementservice.model.PersonTable;
import transactionmanagementservice.model.TransactionPermission;
import transactionmanagementservice.model.TransactionTable;
import transactionmanagementservice.model.TransactionTypeTable;

import java.util.ArrayList;
import java.util.List;

public class HeapMemoryStorage {
    public static List<PersonTable> PERSON_TABLE = new ArrayList<>();
    public static List<TransactionTypeTable> TRANSACTION_TYPE_TABLE = new ArrayList<>();
    public static List<TransactionPermission> PERSON_CAN_DO = new ArrayList<>();
    public static List<TransactionTable> TRANSACTION_TABLE = new ArrayList<>();
}
