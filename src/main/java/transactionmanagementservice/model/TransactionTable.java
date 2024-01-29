package transactionmanagementservice.model;

import transactionmanagementservice.model.constants.TransactionTypeEnum;

public class TransactionTable {
    private Integer uniqueTransactionId;
    private TransactionTypeEnum transactionTypeEnum;
    private Integer amount;
    private Integer personId;
    private Integer uniqueTransactionSequence;

    public TransactionTable(Integer uniqueTransactionId, TransactionTypeEnum transactionTypeEnum, Integer amount, Integer personId, Integer uniqueTransactionSequence) {
        this.uniqueTransactionId = uniqueTransactionId;
        this.transactionTypeEnum = transactionTypeEnum;
        this.amount = amount;
        this.personId = personId;
        this.uniqueTransactionSequence = uniqueTransactionSequence;
    }

    public Integer getUniqueTransactionId() {
        return uniqueTransactionId;
    }

    public TransactionTypeEnum getTransactionTypeEnum() {
        return transactionTypeEnum;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getPersonId() {
        return personId;
    }

    public Integer getUniqueTransactionSequence() {
        return uniqueTransactionSequence;
    }
}
