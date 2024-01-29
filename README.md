# Konami Transaction Service
Transaction Service Management

## Problem Statement

Given sample input tables:

#### PERSON_TABLE
| PERSON_ID | PERSON_NAME |
|-----------|-------------|
| 1         | Joe         |
| 2         | Tom         |
| 3         | Steve       |

#### TRANSACTION_TYPE_TABLE
| TRANSACTION_TYPE_ID | TRANSACTION_NAME |
|---------------------|------------------|
| 1                   | DEPOSIT          |
| 2                   | WITHDRAWAL       |
| 3                   | ADJUST           |
| 4                   | DEPOSIT_VOID     |

#### PERSON_CAN_DO_TABLE
| PERSON_ID | TRANSACTION_TYPE_ID |
|-----------|---------------------|
| 1         | 1                   |
| 1         | 2                   |
| 1         | 3                   |

### TRANSACTION_TABLE

| Transaction ID | Transaction Name | Amount | Person ID | Unique Transaction Sequence |
|-----------------|-------------------|--------|-----------|-----------------------------|
| 100             | DEPOSIT           | 100    | 1         | 1                           |
| 101             | DEPOSIT           | 120    | 2         | 2                           |
| 102             | ADJUST            | 50     | 1         | 1                           |
| 103             | WITHDRAW          | 1000   | 3         | 1                           |
| 104             | DEPOSIT_VOID      | 120    | 2         | 2                           |

Write functions required for the TRANSACTION_TABLE:

1. Write a business function to correctly insert the green record(104) here. The business use case is that the user of the application made a mistake and would like to "Void" one of the existing transactions they just made in the system
2. Make sure to provide ample exception handling for any incoming parameters you request.

## Design Approach

### DEPOSIT_VOID Transaction:

1. Verify Input Parameters are passed correctly in the correct format (Data type validation).
2. Verify if PERSON exists in the system by using PERSON_ID in PERSON_TABLE.
3. Verify TRANSACTION_TYPE by Transaction Name in TRANSACTION_TYPE_TABLE if the user is trying to perform a valid transaction.
4. Verify if the Person has access to perform the transaction in PERSON_CAN_DO_TABLE.
5. Verify if the Unique Transaction Sequence exists in history by checking sequence number, DEPOSIT transaction type, and amount.
6. If all conditions are met, proceed with Inserting DEPOSIT_VOID Transaction.
