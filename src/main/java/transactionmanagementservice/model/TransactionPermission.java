package transactionmanagementservice.model;

public class TransactionPermission {
    private Integer personId;
    private Integer transactionTypeId;

    public Integer getPersonId() {
        return personId;
    }
    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public TransactionPermission(Integer personId, Integer transactionTypeId)  {
            this.personId = personId;
            this.transactionTypeId = transactionTypeId;
    }
}
