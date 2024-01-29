package transactionmanagementservice;

import transactionmanagementservice.exception.DataInvalidException;
import transactionmanagementservice.exception.UnsupportedTransactionTypeException;
import transactionmanagementservice.service.TransactionService;
import transactionmanagementservice.service.TransactionServiceImpl;
import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.storage.StorageHelper;
import transactionmanagementservice.storageserviceoperation.PermissionService;

public class Main {

    /**
     *PROBLEM STATEMENT:
     * Given Sample Input Tables-
     * PERSON_TABLE:
     * _______________________________
     * | PERSON_ID    | PERSON_NAME  |
     * |--------------|--------------|
     * | 1            | Joe          |
     * | 2            | Tom          |
     * | 3            | Steve        |
     * -------------------------------
     * TRANSACTION_TYPE_TABLE:
     * ______________________________________________
     * | TRANSACTION_TYPE_ID    | TRANSACTION_NAME  |
     * |------------------------|-------------------|
     * | 1                      | DEPOSIT           |
     * | 2                      | WITHDRAWAL        |
     * | 3                      | ADJUST            |
     * | 4                      | DEPOSIT_VOID      |
     * ----------------------------------------------
     * PERSON_CAN_DO_TABLE:
     * ______________________________________
     * | PERSON_ID    | TRANSACTION_TYPE_ID |
     * |--------------|---------------------|
     * | 1            | 1                   |
     * | 1            | 2                   |
     * | 1            | 3                   |
     * --------------------------------------
     * Write funtions required for TRANSACTION_TABLE
     * 1) Write a business function to correctly insert the green record(104) here.
     *    The business use case is that the user of the application made a mistake and
     *    would like to "Void" one of the existing transactions they just made in the system
     * 2) Make sure to provide ample exception handling for any incoming parameters you request.
     * __________________________________________________________________________________________
     * | Transaction ID  | Transaction Name  | Amount | Person ID | Unique Transaction Sequence |
     * |-----------------|-------------------|--------|-----------|-----------------------------|
     * | 100             | DEPOSIT           | 100    | 1         | 1                           |
     * | 101             | DEPOSIT           | 120    | 2         | 2                           |
     * | 102             | ADJUST            |  50    | 1         | 1                           |
     * | 103             | WITHDRAW          | 1000   | 3         | 1                           |
     * | 104             | DEPOSIT_VOID      | 120    | 2         | 2                           |
     * ------------------------------------------------------------------------------------------
     *
     * DESIGN APPROACH:
     * DEPOSIT_VOID Transaction:
     * 1)Verify Input Parameters are passed correctly in CORRECT format (Data type validation)
     * 2)Verify if PERSON exists in the system by using PERSON_ID in PERSON_TABLE
     * 3)Verify TRANSACTION_TYPE by Transaction Name in TRANSACTION_TYPE_TABLE
     *   if User is trying to perform valid transaction
     * 4)Verify if Person has access to perform transaction in PERSON_CAN_DO Table
     * 5)Verify if Unique Transaction Sequence exists in history by checking
     *   sequence number , DEPOSIT transaction type and amount
     * 6)if all conditions are met proceed with Inserting DEPOSIT_VOID Transaction
    * */

    public static void main(String[] args) throws InvalidInputException, UnsupportedTransactionTypeException, DataInvalidException {
        StorageHelper.populateTestData();

        /**
         * TESTING ASSUMPTIONS:
         * Based on user data
         * User:1 Joe cant Adjust transactions
         * User:2 Tom cant Deposit or void transactions
         * User:3 Steve cant Withdraw transactions
         * Giving user permissions
         * */
        setupValidPermissions();

        /**
         * USE_CASE:
         * Simulate all Transactions
         * 100,101,102,103,104
         * **/
        TransactionService transactionService = new TransactionServiceImpl();
        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT,100,1,1);
        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT,120,2,2);
        transactionService.transactionOperation(TransactionTypeEnum.ADJUST,50,1,1);
        transactionService.transactionOperation(TransactionTypeEnum.WITHDRAW,1000,3,1);
        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT_VOID,120,2,2);

        StorageHelper.printDataTables();
        StorageHelper.transactionTable();

        /**
         * ERROR_CASE:
         * 1) Person doesnt exist
         * 2) Invalid permission/access rights - Steve only has withdrawl access, cant perform deposit
         * 3) Transaction sequence history doesnt exist (No deposit was done)
         * **/

        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT,100,100,1);
        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT,120,3,2);
        transactionService.transactionOperation(TransactionTypeEnum.DEPOSIT_VOID,120,3,2);

    }

    private static void setupValidPermissions() throws InvalidInputException, DataInvalidException {
        PermissionService permissionService = new PermissionService();
        permissionService.givePermissionstoUser(1,3);
        permissionService.givePermissionstoUser(2,1);
        permissionService.givePermissionstoUser(2,4);
        permissionService.givePermissionstoUser(3,2);
    }

}