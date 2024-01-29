package transactionmanagementservice.service;

import transactionmanagementservice.exception.InvalidInputException;
import transactionmanagementservice.exception.UnsupportedTransactionTypeException;
import transactionmanagementservice.model.constants.TransactionTypeEnum;
import transactionmanagementservice.transactiontableoperations.AdjustOperation;
import transactionmanagementservice.transactiontableoperations.DepositOperation;
import transactionmanagementservice.transactiontableoperations.DepositVoidOperation;
import transactionmanagementservice.transactiontableoperations.WithdrawOperation;

public class TransactionServiceImpl implements TransactionService{
    @Override
    public void transactionOperation(TransactionTypeEnum transactionTypeEnum, Integer amount, Integer personId, Integer transactionSequence) throws InvalidInputException, UnsupportedTransactionTypeException {
        switch (transactionTypeEnum) {
            case DEPOSIT:
                DepositOperation depositOperation = new DepositOperation();
                depositOperation.depositOperation(transactionTypeEnum,amount,personId,transactionSequence);
                break;

            case WITHDRAW:
                WithdrawOperation withdrawOperation = new WithdrawOperation();
                withdrawOperation.withDraw(transactionTypeEnum,amount,personId,transactionSequence);
                break;

            case ADJUST:
                AdjustOperation adjustOperation = new AdjustOperation();
                adjustOperation.adjust(transactionTypeEnum,amount,personId,transactionSequence);
                break;

            case DEPOSIT_VOID:
                DepositVoidOperation depositVoidOperation = new DepositVoidOperation();
                depositVoidOperation.depositVoid(transactionTypeEnum,amount,personId,transactionSequence);
                break;

            default:
                throw new UnsupportedTransactionTypeException("Unsupported Transaction Type");
        }

    }
}
