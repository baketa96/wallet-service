# wallet-service
Simple Spring boot app

@GET
wallet/balance/player/{playerId} -> get current player balance

@POST
transaction/player/{playerId} -> process Transaction, with Transaction in request body

@GET
wallet/transaction/player/{playerId} - get all transactions for player