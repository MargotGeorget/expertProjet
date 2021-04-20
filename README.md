# expertProjet
Projet réalisé durant ma licence 2 de MIASHS
Objectif : réalisé un jeu utilisant une messagerie JAVA 

Principe du jeu : 
• Deux joueurs collabore contre un serveur.
• Le serveur choisit un mot-but et il l’affiche au joueur 1.
• Joueur 1 doit communiquer le mot-but au joueur 2 via des messages qui sont altérés par le serveur.

Règles :
• maximum 5+5 messages, 
• maximum 50 caractères par message,
• chaque message est délivré en supprimant 30% des caractères (ou 30% des mots).
• chaque inclusion du mot-but ou d'un mot très similaire avec le mot-but serait supprimé par des messages du joueur 1.

Conditions de victoire :
• Si le mot-but est parmi le texte d'un message du joueur 2, la partie est gagnée. 
• Si le joueur 2 n'arrive pas à deviner ce mot après ses 5 messages, la partie est perdue.

 Objectifs techniques : 
• Le serveur sera capable de se connecter et jouer contre plusieurs pairs des clients simultanément.
• Le serveur aura accès à une base de mots-buts à jouer.
• Les clients échange des messages via le serveur.
• Le serveur doit pouvoir détecter de mots similaires avec le mot but.

 Objectifs détaillés : 4 étapes 
• Threads
• Sockets
• Messagerie Java
• Algorithmique 
