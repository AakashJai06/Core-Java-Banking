Console Based Banking System
1.	Executive Summary 
We are building a prototype for a new core banking backend. The system needs to manage customer accounts and process financial transactions. 
Critical Requirement: Since this system will eventually be connected to ATMs and Mobile Apps simultaneously, the engine must be 100% thread safe. Data consistency is the highest priority.
2.	Technical Specifications 
1.	Technology Stack: Java 17 or higher. 
2.	Constraints: 
2.1.	Core Java Only. No frameworks allowed (No Spring, No Hibernate, No Database). 
2.2.	Storage: Data must be stored in memory using efficient Java Data Structures. 
2.3.	Design Pattern: The code must follow Object-Oriented Programming (OOP) principles.
3.	Functional Requirements 
The application is a console-based backend system. It does not need a GUI, but it needs a menu to simulate operations.

Sample Menu:
================================================= 

      CONSOLE BANKING SYSTEM 

================================================= 

[1] Create New Account 

[2] Deposit Money 

[3] Withdraw Money 

[4] Transfer Funds (Instant) 

[5] Schedule Transfer (Future/Async) 

[6] View Account Details 

[7] Reporting Dashboard 

[8] Run Concurrency Stress Test (Simulation) 

[9] Exit 

================================================= 

Enter your choice: 


3.1 Account Management 
The system must support multiple types of accounts with specific business rules: 
1.	Savings Account: 
a.	Users earn interest. 
b.	They cannot withdraw more money than they have (no negative balance allowed). 
2.	Current (Checking) Account: 
a.	Users have an Overdraft Limit. 
b.	They can withdraw into the negative up to that limit.
Storage: Since we are not using a database yet, all accounts must be stored efficiently in memory.
3.2 Core Banking Services 
The system must support the following operations: 
1.	Account Creation:
a.	Ability to create an account using a name, email, account type, initial balance, overdraft limit (Optional) and phone number. Generate a unique account number and print in the console.
b.	Validation for email and phone numbers is important.
2.	Account Lookup: 
a.	Ability to retrieve an account detail using a unique Account ID. This lookup must be highly efficient (Time Complexity: O (1)).
3.	Transaction Processing:
a.	Deposit: Add funds to an account. 
b.	Withdraw: Deduct funds (respecting the rules of Savings vs. Current). 
c.	Transfer: Move funds from Account A to Account B.
i.	 Constraint: This must be atomic. If the deduction succeeds but the deposit fails, the money must be returned to Account A.
3.3 Future Payment Scheduling
Users must be able to schedule a transfer to occur in the future.
Requirement: The user inputs the details and delays (e.g., "Pay User B 500 in 30 seconds").
Non-Blocking: The main application menu must not freeze while waiting for the timer. The user should be able to continue performing other operations immediately.
Execution: The transaction must be executed automatically when the time expires using the modern java.time API for calculations.
3.4 Audit Logging 
Every single transaction (successful or failed) must be permanently recorded. The system must write a log entry to a text file (bank_log.txt) immediately after a transaction occurs. 
Format: [Timestamp] [AccountID] [TransactionType] [Amount] [Status]
3.5 Reporting 
Management needs to view statistics. 
Provide features to: 
1.	Filter and list all accounts with a balance exceeding a specific value.
2.	Sort accounts by Balance (Ascending/Descending).
3.	Count the total number of accounts currently in "Overdraft" (Negative balance).
This search functionality should be efficient.
3.6 Automated Interest Accrual	
Savings Accounts earn interest over time. The system must automatically calculate and credit this interest without human intervention.
1.	Interest Rate: 5% Per Annum.
2.	Simulation: Since we cannot wait 24 hours to test "Daily" interest, you must simulate the time. Every 10 seconds of real-time represents 1 Day of banking time.
3.	Log: This system-generated deposit must be logged into the bank_log.txt with the type as INTEREST.
4. Non-Functional Requirements (Technical Constraints)
4.1 Concurrency & Thread Safety (High Priority) 
The system will be subjected to a Stress Test. Scenario: 20"Virtual Users" (Threads) will attempt to deposit and withdraw money from the same account at the exact same time. 
Simulation steps:
1.	Programmatically spawn 10 to 20 concurrent threads.
2.	Half of the threads must perform deposits.
3.	Half of the threads must perform withdrawals.
4.	All threads must target the same account object simultaneously.
Requirement: You must ensure that no money is lost or created out of thin air due to race conditions. The final balance must be perfect. 
4.2 Error Handling 
The application should never crash. If a user tries to withdraw too much money, the system should fail gracefully with a clear error message (not a generic system error). If an account ID is not found, the system should inform the user clearly. 
4.3 Code Standards
1.	The code must be modular. Do not write everything in main. 
2.	Variable names should be meaningful (e.g., balance instead of b).
