-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2021. Máj 19. 23:05
-- Kiszolgáló verziója: 10.4.6-MariaDB
-- PHP verzió: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `library`
--
CREATE DATABASE IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `library`;

DELIMITER $$
--
-- Eljárások
--
DROP PROCEDURE IF EXISTS `addNewAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewAuthor` (IN `surname_in` VARCHAR(100), IN `lastname_in` VARCHAR(100), IN `status_in` TINYINT(1))  NO SQL
INSERT INTO `author` (`author`.`surname`, `author`.`lastname`, `author`.`status`) VALUES (surname_in, lastname_in, status_in)$$

DROP PROCEDURE IF EXISTS `addNewBook`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewBook` (IN `isbn_in` BIGINT(13), IN `title_in` VARCHAR(200), IN `description_in` TEXT, IN `published_in` SMALLINT(4), IN `bookCondition_in` VARCHAR(10), IN `status_in` TINYINT(1))  NO SQL
INSERT INTO `book` (`book`.`isbn`, `book`.`title`, `book`.`discription`, `book`.`published`, `book`.`bookCondition`,`book`.`status`) VALUES (isbn_in, title_in, description_in, published_in, bookCondition_in, status_in)$$

DROP PROCEDURE IF EXISTS `addNewBookAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewBookAuthor` (IN `bookISBN_in` BIGINT(13), IN `authorID_in` INT(8), IN `status_in` TINYINT(1))  NO SQL
INSERT INTO `bookauthor` (`bookauthor`.`bookISBN`,`bookauthor`.`authorID`, `bookauthor`.`status`) VALUES (bookISBN_in, authorID_in, status_in)$$

DROP PROCEDURE IF EXISTS `addNewMember`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewMember` (IN `surname_in` VARCHAR(100), IN `lastname_in` VARCHAR(100), IN `startOfMembership_in` DATE, IN `paidMembershipFee_in` INT(8), IN `status_in` TINYINT(1))  NO SQL
INSERT INTO `member` (`member`.`surname`, `member`.`lastname`, `member`.`startOfMembership`, `member`.`paidMembershipFee`, `member`.`status`) VALUES (surname_in, lastname_in, startOfMembership_in, paidMembershipFee_in, status_in)$$

DROP PROCEDURE IF EXISTS `addNewRental`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addNewRental` (IN `memberID_in` INT(8), IN `bookISBN_in` BIGINT(13), IN `rentalDate_in` DATE, IN `active_in` TINYINT(0), IN `status_in` TINYINT(1))  NO SQL
INSERT INTO `rental` (`rental`.`memberID`, `rental`.`bookISBN`, `rental`.`rentalDate`, `rental`.`active`, `rental`.`status`) VALUES (memberID_in, bookISBN_in, rentalDate_in, active_in, status_in)$$

DROP PROCEDURE IF EXISTS `EndOfRental`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `EndOfRental` (IN `rentalID_in` INT(8), IN `rentalEndDate_in` DATE)  NO SQL
UPDATE `rental` set `rental`.`rentalEndDate` = rentalEndDate_in
WHERE `rental`.`rentalID` = rentalID_in$$

DROP PROCEDURE IF EXISTS `getAllAccessabbleBook`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllAccessabbleBook` ()  NO SQL
SELECT `book`.`isbn`, `book`.`title`, `author`.`surname`, `author`.`lastname`
 FROM `book`, `author`, `bookauthor`
 WHERE  `bookauthor`.`authorID` = `author`.`authorID` AND
 		`book`.`isbn` = `bookauthor`.`bookISBN` AND
 		`book`.`isbn` NOT IN (           
            SELECT `book`.`isbn`            
            FROM `book`, `rental`            
            WHERE `book`.`isbn` = `rental`.`bookISBN` AND
                   `book`.`status` = 1 AND
                   `rental`.`active` = 1 AND
                   `rental`.`status` = 1)$$

DROP PROCEDURE IF EXISTS `getAllAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllAuthor` ()  NO SQL
SELECT * FROM `author` WHERE `author`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllBook`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllBook` ()  NO SQL
SELECT * FROM `book` WHERE `book`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllBookAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllBookAuthor` ()  NO SQL
SELECT * FROM `bookauthor`
	WHERE `bookauthor`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllBookWithAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllBookWithAuthor` ()  NO SQL
SELECT `book`.`isbn`, `book`.`title`, `author`.`surname`, `author`.`lastname`, `book`.`discription`, `book`.`published`, `book`.`status`
FROM `book`, `bookauthor`, `author`
WHERE `book`.`isbn` = `bookauthor`.`bookISBN` AND
	  `author`.`authorID` = `bookauthor`.`authorID` AND
      `book`.`status` = 1 AND
      `bookauthor`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllMember`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMember` ()  NO SQL
SELECT * FROM `member` WHERE `member`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllMemberID`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMemberID` ()  NO SQL
SELECT `member`.`memberID` FROM `member`
WHERE `member`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAllRental`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllRental` ()  NO SQL
SELECT `rental`.`rentalID`, `rental`.`memberID`, `rental`.`bookISBN`, `rental`.`rentalDate`, `rental`.`rentalEndDate`, `rental`.`active`, `rental`.`status`
FROM `rental`
WHERE `rental`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAuthorIdList`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAuthorIdList` ()  NO SQL
SELECT `author`.`authorID` FROM `author`
WHERE `author`.`status` = 1$$

DROP PROCEDURE IF EXISTS `getAuthorListByPopularity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAuthorListByPopularity` ()  NO SQL
SELECT `author`.`authorID`, `author`.`surname`, `author`.`lastname`,                                     COUNT(`author`.`surname`) as popularityLevel

         FROM `rental`, `author`, `bookauthor` , `book`

         WHERE `rental`.`bookISBN` = `book`.`isbn` AND
               `bookauthor`.`bookISBN` = `book`.`isbn` AND
               `bookauthor`.`authorID` = `author`.`authorID` AND 
               `author`.`status` = 1

    GROUP BY `author`.`surname`, `author`.`lastname`
    ORDER BY popularityLevel DESC
    LIMIT 10$$

DROP PROCEDURE IF EXISTS `getBookIsbnList`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBookIsbnList` ()  NO SQL
SELECT `book`.`isbn` FROM `book`
WHERE `book`.`status` = 1$$

DROP PROCEDURE IF EXISTS `logicalDeleteOfAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `logicalDeleteOfAuthor` (IN `authorID_in` INT(8))  NO SQL
UPDATE `author` SET `author`.`status` = 0
	WHERE `author`.`authorID` = authorID_in$$

DROP PROCEDURE IF EXISTS `logicalDeleteOfBook`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `logicalDeleteOfBook` (IN `bookISBN_in` BIGINT(13))  NO SQL
UPDATE `book` SET `book`.`status` = 0
	WHERE `book`.`isbn` = bookISBN_in$$

DROP PROCEDURE IF EXISTS `logicalDeleteOfBookAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `logicalDeleteOfBookAuthor` (IN `bookauthorID_in` INT(8))  NO SQL
UPDATE `bookauthor` SET `bookauthor`.`status` = 0
WHERE `bookauthor`.`bookauthorID` = bookauthorID_in$$

DROP PROCEDURE IF EXISTS `logicalDeleteOfMember`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `logicalDeleteOfMember` (IN `memberID_in` INT(8))  NO SQL
UPDATE `member` SET `member`.`status` = 0
	WHERE `member`.`memberID` = memberID_in$$

DROP PROCEDURE IF EXISTS `logicalDeleteOfRental`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `logicalDeleteOfRental` (IN `rentalID_in` INT(8))  NO SQL
UPDATE `rental` SET `rental`.`status` = 0
	WHERE `rental`.`rentalID` = rentalID_in$$

DROP PROCEDURE IF EXISTS `memberIdListWithMoreThanFiveActiveRentals`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `memberIdListWithMoreThanFiveActiveRentals` ()  NO SQL
SELECT `rental`.`memberID`, COUNT(`rental`.`rentalID`) as osszes
FROM `rental`
WHERE `rental`.`active` = 1 AND
	  `rental`.`status` = 1
GROUP BY `rental`.`memberID`
HAVING osszes > 5$$

DROP PROCEDURE IF EXISTS `mostWantedBooks`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `mostWantedBooks` ()  NO SQL
SELECT `rental`.`bookISBN`, `book`.`title`,
COUNT(`rental`.`bookISBN`) as books

         FROM `rental`, `book`

         WHERE `rental`.`bookISBN` = `book`.`isbn` AND
         	   `rental`.`status` = 1

    GROUP BY `rental`.`bookISBN`
    ORDER BY books DESC
    Limit 5$$

DROP PROCEDURE IF EXISTS `updateAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateAuthor` (IN `authorID_in` INT(8), IN `surname_in` VARCHAR(100), IN `lastname_in` VARCHAR(100), IN `status_in` TINYINT(1))  NO SQL
UPDATE `author` SET
	`author`.`surname` = surname_in,
    `author`.`lastname` = lastname_in,
    `author`.`status` = status_in
WHERE `author`.`authorID` = authorID_in$$

DROP PROCEDURE IF EXISTS `updateBook`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBook` (IN `bookISBN_in` BIGINT(13), IN `title_in` VARCHAR(200), IN `description_in` TEXT, IN `published_in` DATE, IN `bookCondition_in` VARCHAR(10), IN `status_in` TINYINT(1))  NO SQL
UPDATE `book` SET
	`book`.`title` = title_in,
    `book`.`discription` = description_in,
    `book`.`published` = published_in,
    `book`.`bookCondition` = bookCondition_in,
    `book`.`status` = status_in
WHERE `book`.`isbn` = bookISBN_in$$

DROP PROCEDURE IF EXISTS `updateBookAuthor`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBookAuthor` (IN `bookauthorID_in` INT(8), IN `bookISBN_in` BIGINT(13), IN `authorID_in` INT(8), IN `status_in` TINYINT(1))  NO SQL
UPDATE `bookauthor` SET `bookauthor`.`bookISBN` = bookISBN_in,
						`bookauthor`.`authorID` = authorID_in,
                        `bookauthor`.`status` = status_in
WHERE bookauthorID_in = `bookauthor`.`bookauthorID`$$

DROP PROCEDURE IF EXISTS `updateMember`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateMember` (IN `memberID_in` INT(8), IN `surname_in` VARCHAR(100), IN `lastname_in` INT(100), IN `startOfMembership_in` DATE, IN `paidMembershipFee_in` INT(8), IN `status_in` TINYINT(1))  NO SQL
UPDATE `member` SET
	`member`.`surname` = surname_in,
    `member`.`lastname` = lastname_in,
    `member`.`startOfMembership` = startOfMembership_in,
    `member`.`paidMembershipFee` = paidMembershipFee_in,
    `member`.`status` = status_in
WHERE `member`.`memberID` = memberID_in$$

DROP PROCEDURE IF EXISTS `updateRental`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRental` (IN `rentalID_in` INT(8), IN `memberID_in` INT(8), IN `bookISBN_in` BIGINT(13), IN `rentalDate_in` DATE, IN `rentalEndDate_in` DATE, IN `active_in` TINYINT(1), IN `status_in` TINYINT(1))  NO SQL
UPDATE `rental` SET
	`rental`.`memberID` = memberID_in,
    `rental`.`bookISBN` = bookISBN_in,
    `rental`.`rentalDate` = rentalDate_in,
    `rental`.`rentalEndDate` = rentalEndDate_in,
    `rental`.`active` = active_in,
    `rental`.`status` = status_in
WHERE `rental`.`rentalID` = rentalID_in$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `authorID` int(8) NOT NULL,
  `surname` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastname` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- TÁBLA KAPCSOLATAI `author`:
--

--
-- A tábla adatainak kiíratása `author`
--

INSERT INTO `author` (`authorID`, `surname`, `lastname`, `status`) VALUES
(1, 'Jane', 'Austen', 1),
(2, 'Saifedean', 'Ammous', 1),
(3, 'Stephen', 'King', 1),
(4, 'Beth', 'Coates', 1),
(5, ' Elizabeth', 'Foley', 1),
(6, 'J. K.', 'Rowling', 1),
(7, 'Daniel', 'Steel', 1),
(8, 'J. R. R.', 'Tolkien', 1),
(9, 'Ernest', 'Hemingway', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `isbn` bigint(13) NOT NULL,
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `discription` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `published` smallint(4) NOT NULL,
  `bookCondition` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- TÁBLA KAPCSOLATAI `book`:
--

--
-- A tábla adatainak kiíratása `book`
--

INSERT INTO `book` (`isbn`, `title`, `discription`, `published`, `bookCondition`, `status`) VALUES
(899000409902, 'Sorvadj el!', 'Megszokott amerikai kisváros, szokványos középosztálybeli jómód, unalmas élet, melynek minden lépése előre tudható, előre kiszámítható. Vagy mégsem? Mert közbejön egy városi pánik, annak a közepébe pedig váratlanul berobban egy egyáltalán nem kívánatos cigánykaraván. Amíg csak zsonglőrködnek, tenyérből jósolnak, akit tudnak, szemfényvesztéssel megkopasztanak, addig még hagyján...de aztán kopjanak le idejében! Ez a cigánybanda azonban más, ez még ősi erőkkel rendelkezik, ősi hagyományokat ápol, s ha valaki keresztezi az útját, azt megátkozza, méghozzá úgy, hogy az átok meg is fogan. Már csak ez hiányzott a kisváros menő ügyvédjének, aki véletlenül, de leginkább felesége áldásos közreműködésével, elüti a cigányvajda lányát. Az Isten legyen hozzá irgalmas... De lesz-e?', 1996, 'sérült', 1),
(1749876584352, 'Büszkeség és Balítélet', 'Hosszú szoknyás film.', 1813, 'sérült', 1),
(9789630787819, 'Fekete Ház', 'Baromi jó könyv.', 2009, 'jó', 1),
(9789632034218, 'Fordulópont', 'Kalifornia rangos kórházaiból négy kiváló traumatológust ér az a megtiszteltetés, hogy egy tömegkatasztrófára felkészít? orvosi képzési program keretében együtt dolgozhatnak Párizsban francia kollégáikkal.\nBill Browning a San Francisco Közkórház és Traumatológiai Központ legforgalmasabb részlegét, a sürg?sségi osztályt vezeti. Mivel elvált, és Londonban él? lányait csak ritkán láthatja, minden idejét és energiáját a munkájának szenteli. Stephanie Lawrence-t, a UCSF egyetemi klinika munkatársát örökös lelkifurdalás gyötri: kétségbeesetten ?rl?dik ígéretes karrierje és a családja között. Wendy Jones, a Stanford elhivatott baleseti sebésze, kilátástalan kapcsolatban verg?dik egy n?s szívsebésszel. Olyan csapdába esett, amelyb?l nem tud, s talán nem is akar szabadulni. Tom Wylie naponta bizonyítja mesterségbeli tudását az oaklandi Alta Bates magán-egészségügyi központban. A sármos férfi vitathatatlanul népszer? a hölgyek körében, mégis elszántan ?rzi függetlenségét.\nA négy orvos izgalmas kihívásokkal szembesül a képzés során, és miközben egy tömeges er?szakba torkolló váratlan tragédia együtt cselekv?, önzetlen csapattá kovácsolja ?ket, lassan rádöbbennek, hogy az átmenetire tervezett párizsi tartózkodás életük jelent?s fordulópontjává vált.', 2021, 'jó', 1),
(9789635091195, 'Bitcoin Standard - A központi bankok decentralizált alternatívája', 'Amikor egy álnév mögé bújó programozó bemutatott egy új elektronikus készpénzrendszert egy jelentéktelen levelezőlistán 2008-ban, csupán nagyon kevesen figyeltek oda rá. Tíz év elteltével ez a semmiből jött, független, decentralizált szoftver minden várakozás ellenére a modern központi bankok alternatívája lett. Ismerjük meg a Bitcoin felemelkedésének történetét, tulajdonságait, amelyek lehetővé tették gyors növekedését, és várható gazdasági, politikai és társadalmi hatásait!', 2020, 'jó', 1),
(9789635430567, 'Mit tenne Frida Kahlo? - Kivételes nők, akik nem ismertek akadályt', 'Sorolj fel tíz tudóst, aki nő volt! És Marie Curie-t most nem dobhatod be... Az megvan, hogy az első számítógépes algoritmust Byron lánya, Ada Lovelace alkotta meg? Vagy hogy Telkes Mária nem a két szép szeméről kapta a Napkirálynő becenevet?Mennyit tudsz Coco Chanel kihívásokkal teli életéről? Wollstonecraft, Earhart, Hopper - ismerősen csengő nevek? A való élet szerencsére nem egy kvízműsor - de azért nem árt, ha tájékozottak vagyunk. Az elmúlt száz évben sok dolog megváltozott: közeledni kezdtek egymáshoz a nők és férfiak jogai, számos helyen kialakult az egyenlőségre törekvő szemlélet, de nem szabad elfelejtenünk, milyen hosszú és göröngyös utat jártak be a történelem nőalakjai - ahogyan azt sem: van még hova fejlődnünk. A szerzőpáros időt és energiát nem kímélve kutatta fel a történelem olyan nőalakjait, akiket senki sem ültethetett a sarokba... Könyvük egyszerre inspiráció, ismeretterjesztő kézikönyv és tanácsadókötet.Nagy szerencsénkre a szerzők a humort sem spórolták ki a történetekből, így remek társaság lehet borús napjainkra is.', 2021, 'jó', 1),
(9789639492608, 'Az öreg halász és a tenger', 'Az embert el lehet pusztítani, de legy?zni nem lehet soha - írta Hemingway. Az öreg halász és a tenger ennek a meggy?z?désnek a h? foglalata és diadalmas tanúságtétele.', 2005, 'jó', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `bookauthor`
--

DROP TABLE IF EXISTS `bookauthor`;
CREATE TABLE `bookauthor` (
  `bookauthorID` int(8) NOT NULL,
  `bookISBN` bigint(13) NOT NULL,
  `authorID` int(8) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- TÁBLA KAPCSOLATAI `bookauthor`:
--

--
-- A tábla adatainak kiíratása `bookauthor`
--

INSERT INTO `bookauthor` (`bookauthorID`, `bookISBN`, `authorID`, `status`) VALUES
(1, 1749876584352, 1, 1),
(2, 9789635091195, 2, 1),
(3, 899000409902, 3, 1),
(4, 9789635430567, 4, 1),
(5, 9789635430567, 5, 1),
(6, 9789632034218, 7, 1),
(7, 9789630787819, 3, 1),
(10, 9789639492608, 9, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `member`
--

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `memberID` int(8) NOT NULL,
  `surname` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastname` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `startOfMembership` date NOT NULL,
  `paidMembershipFee` int(8) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- TÁBLA KAPCSOLATAI `member`:
--

--
-- A tábla adatainak kiíratása `member`
--

INSERT INTO `member` (`memberID`, `surname`, `lastname`, `startOfMembership`, `paidMembershipFee`, `status`) VALUES
(1, 'Dorottya', 'Vass', '2020-08-11', 600, 1),
(2, 'Péter', 'Falkai', '2021-01-30', 600, 1),
(3, 'Károly', 'Ka', '2021-04-04', 0, 1),
(4, 'Kata', 'Kis', '2018-06-20', 3000, 1),
(5, 'Sára', 'Kőszegi', '2017-02-02', 1800, 1),
(6, 'Margit', 'Rókás', '2019-01-05', 0, 1),
(7, 'Alma', 'Barna', '2020-01-20', 600, 1),
(8, 'Karola', 'Lola', '2021-06-23', 0, 1),
(9, 'Clara', 'Varga', '2021-05-10', 0, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `rental`
--

DROP TABLE IF EXISTS `rental`;
CREATE TABLE `rental` (
  `rentalID` int(8) NOT NULL,
  `memberID` int(8) NOT NULL,
  `bookISBN` bigint(13) NOT NULL,
  `rentalDate` date NOT NULL,
  `rentalEndDate` date NOT NULL,
  `active` tinyint(1) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- TÁBLA KAPCSOLATAI `rental`:
--

--
-- A tábla adatainak kiíratása `rental`
--

INSERT INTO `rental` (`rentalID`, `memberID`, `bookISBN`, `rentalDate`, `rentalEndDate`, `active`, `status`) VALUES
(1, 3, 1749876584352, '2020-08-02', '0000-00-00', 1, 1),
(2, 2, 9789632034218, '2021-04-14', '2021-05-12', 0, 1),
(3, 3, 9789635091195, '2020-08-02', '0000-00-00', 1, 1),
(4, 6, 9789635091195, '2019-04-02', '2019-07-01', 0, 1),
(5, 3, 9789635430567, '2021-02-10', '0000-00-00', 1, 1),
(6, 3, 9789630787819, '2021-01-09', '0000-00-00', 1, 1),
(7, 3, 899000409902, '2021-03-01', '0000-00-00', 1, 1);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`authorID`);

--
-- A tábla indexei `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`isbn`);

--
-- A tábla indexei `bookauthor`
--
ALTER TABLE `bookauthor`
  ADD PRIMARY KEY (`bookauthorID`),
  ADD KEY `bookISBN_id` (`bookISBN`),
  ADD KEY `author_id` (`authorID`);

--
-- A tábla indexei `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberID`);

--
-- A tábla indexei `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`rentalID`),
  ADD KEY `memberID` (`memberID`),
  ADD KEY `bookISBN` (`bookISBN`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `author`
--
ALTER TABLE `author`
  MODIFY `authorID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT a táblához `bookauthor`
--
ALTER TABLE `bookauthor`
  MODIFY `bookauthorID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `member`
--
ALTER TABLE `member`
  MODIFY `memberID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT a táblához `rental`
--
ALTER TABLE `rental`
  MODIFY `rentalID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
