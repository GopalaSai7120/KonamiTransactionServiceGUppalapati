package transactionmanagementservice.exception;

public class UnsupportedTransactionTypeException extends Exception{
    public UnsupportedTransactionTypeException(String message){
        super(message);
    }
}
