-- H2-compatible seed data (runs after JPA schema creation)
-- Users are seeded by DataSeeder.java; extra demo users here:
MERGE INTO utilisateur (id, nom, email, password, role)
  KEY(email)
  VALUES (10, 'Jean Dupont', 'jean.dupont@fst.ma', '$2a$10$8.UnVuG9HLDAjJzEE8NqseEEMJQk/5vB/m60j8bX.v3/v.Yh8/lH2', 'FORMATEUR');
MERGE INTO utilisateur (id, nom, email, password, role)
  KEY(email)
  VALUES (11, 'Alice Martin', 'alice.martin@fst.ma', '$2a$10$8.UnVuG9HLDAjJzEE8NqseEEMJQk/5vB/m60j8bX.v3/v.Yh8/lH2', 'APPRENANT');

-- Sample Courses
MERGE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation)
  KEY(id)
  VALUES (1, 'Introduction à Spring Boot 3', 'Apprenez les bases de Spring Boot 3 avec ce cours complet pour débutants.', 'Développement Web', 'DEBUTANT', true, 10, NOW());
MERGE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation)
  KEY(id)
  VALUES (2, 'Maîtriser JPA et Hibernate', 'Plongez au cœur de l''accès aux données en Java avec Hibernate et JPA.', 'Bases de données', 'INTERMEDIAIRE', true, 10, NOW());
MERGE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation)
  KEY(id)
  VALUES (3, 'Architecture Microservices', 'Concevez, déployez et sécurisez des architectures distribuées.', 'Architecture', 'AVANCE', true, 10, NOW());

-- Modules
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (1, 'Les fondamentaux de Spring Boot', 1, 1);
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (2, 'Création d''API RESTful', 2, 1);
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (3, 'Sécurité avec Spring Security', 3, 1);
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (4, 'Introduction à JPA', 1, 2);
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (5, 'Relations et Fetching', 2, 2);
MERGE INTO module (id, titre, ordre, cours_id) KEY(id) VALUES (6, 'Introduction aux Microservices', 1, 3);

-- Lessons
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (1, 'C''est quoi Spring Boot ?', 'Spring Boot simplifie la création d''applications basées sur Spring...', 1, 15, 1);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (2, 'Configuration et Auto-Configuration', 'L''auto-configuration est le cœur de Spring Boot...', 2, 20, 1);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (3, 'Le fichier application.properties', 'Toutes vos configurations centralisées...', 3, 10, 1);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (4, 'Les contrôleurs REST', 'Utilisation de @RestController...', 1, 25, 2);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (5, 'Gestion des exceptions', 'L''utilisation de @ExceptionHandler...', 2, 15, 2);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (6, 'Qu''est-ce que l''ORM ?', 'Le mapping objet-relationnel expliqué...', 1, 20, 4);
MERGE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) KEY(id) VALUES (7, 'Les annotations de base', '@Entity, @Id, @Column...', 2, 15, 4);

-- Quizzes
MERGE INTO quiz (id, titre, module_id) KEY(id) VALUES (1, 'Quiz Fondamentaux Spring Boot', 1);
MERGE INTO quiz (id, titre, module_id) KEY(id) VALUES (2, 'Quiz API RESTful', 2);
MERGE INTO quiz (id, titre, module_id) KEY(id) VALUES (3, 'Quiz Fondamentaux JPA', 4);

-- Questions
MERGE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) KEY(id) VALUES (1, 'Quel est l''avantage principal de Spring Boot ?', 'Code manuel obligatoire', 'Auto-configuration', 'Nécessite XML', 'Pas de serveur intégré', 'B', 1);
MERGE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) KEY(id) VALUES (2, 'Quelle annotation remplace @Controller et @ResponseBody ?', '@Service', '@RestController', '@Component', '@Repository', 'B', 1);
MERGE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) KEY(id) VALUES (3, 'Comment s''appelle le serveur web par défaut de Spring Boot ?', 'GlassFish', 'JBoss', 'Tomcat', 'Jetty', 'C', 1);
MERGE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) KEY(id) VALUES (4, 'Quelle annotation permet de lire un paramètre d''URL ?', '@RequestParam', '@PathVariable', '@QueryParam', '@Header', 'B', 2);
MERGE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) KEY(id) VALUES (5, 'Quelle annotation marque une classe comme persistante en JPA ?', '@Entity', '@Table', '@Persistent', '@Database', 'A', 3);

-- Update cours with prix and dureeHeures (H2 MERGE doesn't support partial update, use UPDATE)
UPDATE cours SET prix = 0.00, duree_heures = 8  WHERE id = 1;
UPDATE cours SET prix = 99.00, duree_heures = 12 WHERE id = 2;
UPDATE cours SET prix = 149.00, duree_heures = 20 WHERE id = 3;
