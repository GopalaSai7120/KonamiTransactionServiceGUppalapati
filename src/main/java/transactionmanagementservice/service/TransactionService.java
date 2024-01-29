package transactionmanagementservice.service;

import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.exception.UnsupportedTransactionTypeException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;

public interface TransactionService {

    void transactionOperation(TransactionTypeEnum transactionTypeEnum, Integer amount, Integer personId, Integer transactionSequence) throws InvalidInputException, UnsupportedTransactionTypeException;
}
