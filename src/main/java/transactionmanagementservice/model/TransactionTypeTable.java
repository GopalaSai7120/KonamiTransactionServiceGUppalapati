package transactionmanagementservice.model;

import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;

public class TransactionTypeTable {
    private Integer transactionTypeId;
    private TransactionTypeEnum transactionTypeEnum;

    public TransactionTypeTable(Integer transactionTypeId, TransactionTypeEnum transactionTypeEnum) throws InvalidInputException {
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeEnum = transactionTypeEnum;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public TransactionTypeEnum getTransactionTypeEnum() {
        return transactionTypeEnum;
    }
}
