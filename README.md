2048-AI
=======

An attempt at creating an artificial intelligence that plays 2048, with a graphic interface.

Since the game contains a part of randomness, I have chosen to base the AI on an expectimax tree. This means that it explores the board up to 4 or 5 turns depending on the next move made, and it makes the move that is in the expected best branch, according to a simple fitness function that tries to build a serpentine.

After a few tries, I realised that it was a good idea to have a simple fitness that can be computed fast, so as to explore the tree faster and go deeper.
By comparing my AI to other existing propositions, I realised that what it lacked was an efficient board representation, making it possible to explore up to 8 turns.

I have finally stopped developing it. The current version wins 3 times out of 4. 
