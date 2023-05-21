## Table of Contents
- [Blockchain Node Operations](#blockchain-node-operations)
  - [1. Initialize](#1-initialize)
  - [2. Validate Transaction](#2-validate-transaction)
  - [3. Validate Block](#3-validate-block)
  - [4. Propagate Data](#4-propagate-data)
  - [5. Synchronize Blockchain](#5-synchronize-blockchain)
  - [6. Consensus Participation](#6-consensus-participation)
  - [7. Execute Smart Contracts](#7-execute-smart-contracts)
  - [8. Repeat](#8-repeat)
- [Block Class](#block-class)
  - [Class Structure](#class-structure)
    - [Fields](#fields)
    - [Constructors](#constructors)
    - [Methods](#methods)
- [Transaction Class](#transaction-class)
  - [Class Structure](#class-structure-1)
    - [Fields](#fields-1)
    - [Constructors](#constructors-1)
    - [Methods](#methods-1)
- [Blockchain Class](#blockchain-class)
  - [Class Structure](#class-structure-2)
    - [Fields](#fields-2)
    - [Constructors](#constructors-2)
    - [Methods](#methods-2)

# Blockchain Node Operations

This document outlines the various operations performed by a blockchain node. It describes the steps involved in initializing the node, validating transactions and blocks, propagating data, synchronizing the blockchain, participating in consensus, executing smart contracts, and repeating these processes.

## 1. Initialize
   - [ ] Connect to the network and obtain a copy of the blockchain.
   - [ ] Start listening for new transactions and blocks from other nodes.

## 2. Validate Transaction
   - [ ] Upon receiving a new transaction:
     - [ ] Verify the transaction's digital signature.
     - [ ] Check if the transaction inputs are valid and not spent.
     - [ ] Ensure the transaction adheres to the predefined rules (e.g., transaction format, gas limit, etc.).
     - [ ] If the transaction is valid, propagate it to other nodes in the network.

## 3. Validate Block
   - [ ] Upon receiving a new block:
     - [ ] Verify the block's hash and signature.
     - [ ] Validate the transactions included in the block using the validation rules from step 2.
     - [ ] Check that the block adheres to the blockchain's consensus rules (e.g., difficulty, block size, etc.).
     - [ ] If the block is valid, update the local copy of the blockchain.

## 4. Propagate Data
   - [ ] Upon receiving a new transaction or block:
     - [ ] Relay the data to other connected nodes in the network.
     - [ ] Ensure the timely dissemination of the data to facilitate network-wide consensus.

## 5. Synchronize Blockchain
   - [ ] Regularly check for updates to the blockchain:
     - [ ] Download and verify new blocks received from other nodes.
     - [ ] Update the local copy of the blockchain to reflect the latest state.

## 6. Consensus Participation
   - [ ] Participate in consensus-related activities:
     - [ ] Vote or signal support for proposed changes or upgrades.
     - [ ] Express opinions on protocol adjustments or network governance.
     - [ ] Engage in community discussions and decision-making processes.

## 7. Execute Smart Contracts
   - [ ] If the blockchain supports smart contracts:
     - [ ] Execute smart contract code for incoming transactions or contract interactions.
     - [ ] Validate the outputs of the smart contract execution.
     - [ ] Store and update the contract state if necessary.

## 8. Repeat
   - [ ] Continue listening for new transactions and blocks.
   - [ ] Perform the above steps to validate, propagate, synchronize, and participate in consensus activities.
  
---

# Block Class

The `Block` class represents a block in a blockchain. It contains information such as the block index, timestamp, transactions, nonce, hash, and the hash of the previous block.

## Class Structure

### Fields

- `index` (int): The index of the block.
- `timestamp` (long): The timestamp when the block was created.
- `transactions` (ArrayList<Transaction>): The list of transactions included in the block.
- `nonce` (int): The nonce value used in mining the block.
- `hash` (String): The hash value of the block.
- `previousHash` (String): The hash value of the previous block.

### Constructors

- `Block(int index, String previousHash, ArrayList<Transaction> transactions)`: Initializes a new instance of the `Block` class with the given index, previous block hash, and list of transactions. It sets the timestamp, calculates the hash value, and initializes the nonce.

### Methods

- `getTransactions(): ArrayList<Transaction>`: Returns the list of transactions included in the block.
- `getIndex(): int`: Returns the index of the block.
- `getHash(): String`: Returns the hash value of the block.
- `getTimestamp(): long`: Returns the timestamp of the block.
- `getNonce(): int`: Returns the nonce value of the block.
- `getPreviousHash(): String`: Returns the hash value of the previous block.
- `calculateHashData(): String`: Calculates and returns the hash value of the block data, including the index, timestamp, previous hash, nonce, and transactions. It uses the SHA-256 hash function to generate the hash.
- `mineBlock(int difficulty): void`: Mines the block by finding a hash value that meets the specified difficulty level. The difficulty level determines the number of leading zeros required in the hash. It increments the nonce and recalculates the hash until a suitable hash is found.
- `addTransaction(Transaction transaction): boolean`: Adds a transaction to the block if it is valid. It verifies the validity of the transaction and adds it to the list of transactions. Returns `true` if the transaction is added successfully; otherwise, returns `false`.



# Transaction Class

The `Transaction` class represents a transaction in a blockchain. It contains information about the sender, recipient, and the transaction amount.

## Class Structure

### Fields

- `sender` (String): The address of the transaction sender.
- `recipient` (String): The address of the transaction recipient.
- `amount` (float): The amount of the transaction.

### Constructors

- `Transaction(String sender, String recipient, float amount)`: Initializes a new instance of the `Transaction` class with the given sender, recipient, and amount.

### Methods

- `getSender(): String`: Returns the address of the transaction sender.
- `getRecipient(): String`: Returns the address of the transaction recipient.
- `getAmount(): float`: Returns the amount of the transaction.
- `toString(): String`: Returns a string representation of the transaction.
- `processTransaction(): boolean`: Performs checks and processes the transaction. It validates the sender and recipient addresses, ensures the transaction amount is positive, and performs additional custom validation checks. Returns `true` if the transaction is valid; otherwise, returns `false`.

Note: The `isValidAddress()` and `hasSufficientFunds()` methods are private and used internally for transaction validation. You may implement your own custom logic in these methods to suit your application's requirements.

# Blockchain Class

The `Blockchain` class represents a blockchain that consists of multiple blocks. It provides functionality for adding blocks, validating the chain, and retrieving the blockchain.

## Class Structure

### Fields

- `difficulty` (int): The difficulty level used for mining blocks in the blockchain.
- `blockchain` (ArrayList<Block>): The list of blocks in the blockchain.

### Constructors

- `Blockchain(int difficulty)`: Initializes a new instance of the `Blockchain` class with the specified difficulty. It creates the genesis block and adds it to the blockchain.

### Methods

- `getBlockchain(): ArrayList<Block>`: Returns the list of blocks in the blockchain.
- `getLatestBlock(): Block`: Returns the latest block in the blockchain.
- `addBlock(Block newBlock): void`: Adds a new block to the blockchain. It mines the block and adds it to the chain.
- `isChainValid(): boolean`: Checks if the blockchain is valid by verifying the hash and previous hash of each block and ensuring the difficulty level is met.



---

1. Initialize:
   - Connect to the network and obtain a copy of the blockchain.
   - Start listening for new transactions and blocks from other nodes.

2. Validate Transaction:
   - Upon receiving a new transaction:
     - Verify the transaction's digital signature.
     - Check if the transaction inputs are valid and not spent.
     - Ensure the transaction adheres to the predefined rules (e.g., transaction format, gas limit, etc.).
     - If the transaction is valid, propagate it to other nodes in the network.

3. Validate Block:
   - Upon receiving a new block:
     - Verify the block's hash and signature.
     - Validate the transactions included in the block using the validation rules from step 2.
     - Check that the block adheres to the blockchain's consensus rules (e.g., difficulty, block size, etc.).
     - If the block is valid, update the local copy of the blockchain.

4. Propagate Data:
   - Upon receiving a new transaction or block:
     - Relay the data to other connected nodes in the network.
     - Ensure the timely dissemination of the data to facilitate network-wide consensus.

5. Synchronize Blockchain:
   - Regularly check for updates to the blockchain:
     - Download and verify new blocks received from other nodes.
     - Update the local copy of the blockchain to reflect the latest state.

6. Consensus Participation:
   - Participate in consensus-related activities:
     - Vote or signal support for proposed changes or upgrades.
     - Express opinions on protocol adjustments or network governance.
     - Engage in community discussions and decision-making processes.

7. Execute Smart Contracts:
   - If the blockchain supports smart contracts:
     - Execute smart contract code for incoming transactions or contract interactions.
     - Validate the outputs of the smart contract execution.
     - Store and update the contract state if necessary.

8. Repeat:
   - Continue listening for new transactions and blocks.
   - Perform the above steps to validate, propagate, synchronize, and participate in consensus activities.

