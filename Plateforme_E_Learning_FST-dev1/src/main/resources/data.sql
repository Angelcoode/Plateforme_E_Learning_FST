-- Create a Formateur for testing
INSERT IGNORE INTO utilisateur (id, nom, email, password, role) VALUES (10, 'Jean Dupont', 'jean.dupont@fst.ma', '$2a$10$8.UnVuG9HLDAjJzEE8NqseEEMJQk/5vB/m60j8bX.v3/v.Yh8/lH2', 'FORMATEUR');
-- Create an Apprenant for testing
INSERT IGNORE INTO utilisateur (id, nom, email, password, role) VALUES (11, 'Alice Martin', 'alice.martin@fst.ma', '$2a$10$8.UnVuG9HLDAjJzEE8NqseEEMJQk/5vB/m60j8bX.v3/v.Yh8/lH2', 'APPRENANT');
-- Create an Admin for testing
INSERT IGNORE INTO utilisateur (id, nom, email, password, role) VALUES (12, 'Admin FST', 'admin.fst@fst.ma', '$2a$10$8.UnVuG9HLDAjJzEE8NqseEEMJQk/5vB/m60j8bX.v3/v.Yh8/lH2', 'ADMIN');

-- Insert Sample Courses
INSERT IGNORE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation) VALUES (1, 'Introduction à Spring Boot 3', 'Apprenez les bases de Spring Boot 3 avec ce cours complet pour débutants.', 'Développement Web', 'DEBUTANT', true, 10, NOW());
INSERT IGNORE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation) VALUES (2, 'Maîtriser JPA et Hibernate', 'Plongez au cœur de l''accès aux données en Java avec Hibernate et JPA.', 'Bases de données', 'INTERMEDIAIRE', true, 10, NOW());
INSERT IGNORE INTO cours (id, titre, description, categorie, niveau, actif, formateur_id, date_creation) VALUES (3, 'Architecture Microservices avec Spring Cloud', 'Concevez, déployez et sécurisez des architectures distribuées.', 'Architecture', 'AVANCE', true, 10, NOW());

-- Insert Modules for Course 1
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (1, 'Les fondamentaux de Spring Boot', 1, 1);
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (2, 'Création d''API RESTful', 2, 1);
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (3, 'Sécurité avec Spring Security', 3, 1);

-- Insert Modules for Course 2
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (4, 'Introduction à JPA', 1, 2);
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (5, 'Relations et Fetching', 2, 2);

-- Insert Modules for Course 3
INSERT IGNORE INTO module (id, titre, ordre, cours_id) VALUES (6, 'Introduction aux Microservices', 1, 3);

-- Insert Lessons for Module 1
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (1, 'C''est quoi Spring Boot ?', 'Spring Boot simplifie la création d''applications basées sur Spring...', 1, 15, 1);
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (2, 'Configuration et Auto-Configuration', 'L''auto-configuration est le cœur de Spring Boot...', 2, 20, 1);
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (3, 'Le fichier application.properties', 'Toutes vos configurations centralisées...', 3, 10, 1);

-- Insert Lessons for Module 2
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (4, 'Les contrôleurs REST', 'Utilisation de @RestController...', 1, 25, 2);
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (5, 'Gestion des exceptions', 'L''utilisation de @ExceptionHandler...', 2, 15, 2);

-- Insert Lessons for Module 4 (JPA)
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (6, 'Qu''est-ce que l''ORM ?', 'Le mapping objet-relationnel expliqué...', 1, 20, 4);
INSERT IGNORE INTO lecon (id, titre, contenu, ordre, duree_min, module_id) VALUES (7, 'Les annotations de base', '@Entity, @Id, @Column...', 2, 15, 4);

-- Insert Quizzes
INSERT IGNORE INTO quiz (id, titre, module_id) VALUES (1, 'Quiz Fondamentaux Spring Boot', 1);
INSERT IGNORE INTO quiz (id, titre, module_id) VALUES (2, 'Quiz API RESTful', 2);
INSERT IGNORE INTO quiz (id, titre, module_id) VALUES (3, 'Quiz Fondamentaux JPA', 4);

-- Insert Questions for Quiz 1
INSERT IGNORE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) VALUES (1, 'Quel est l''avantage principal de Spring Boot ?', 'Code manuel obligatoire', 'Auto-configuration', 'Nécessite XML', 'Pas de serveur intégré', 'B', 1);
INSERT IGNORE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) VALUES (2, 'Quelle annotation remplace @Controller et @ResponseBody ?', '@Service', '@RestController', '@Component', '@Repository', 'B', 1);
INSERT IGNORE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) VALUES (3, 'Comment s''appelle le serveur web par défaut de Spring Boot ?', 'GlassFish', 'JBoss', 'Tomcat', 'Jetty', 'C', 1);

-- Insert Questions for Quiz 2
INSERT IGNORE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) VALUES (4, 'Quelle annotation permet de lire un paramètre d''URL (/api/users/5) ?', '@RequestParam', '@PathVariable', '@QueryParam', '@Header', 'B', 2);

-- Insert Questions for Quiz 3
INSERT IGNORE INTO question (id, text, optiona, optionb, optionc, optiond, correct_answer, quiz_id) VALUES (5, 'Quelle annotation marque une classe comme persistante en JPA ?', '@Entity', '@Table', '@Persistent', '@Database', 'A', 3);
