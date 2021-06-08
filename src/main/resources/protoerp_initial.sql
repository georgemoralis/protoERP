--
-- Δομή πίνακα για τον πίνακα `protoerp_company`
--

CREATE TABLE `protoerp_company` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `companyEidos` int(11) DEFAULT NULL,
  `companyMorfi` int(11) DEFAULT NULL,
  `dateEnded` date DEFAULT NULL,
  `dateStarted` date DEFAULT NULL,
  `demoCompany` bit(1) DEFAULT NULL,
  `demoMyDataEnabled` bit(1) DEFAULT NULL,
  `demoPassMyData` varchar(255) DEFAULT NULL,
  `demoUserMyData` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job` text DEFAULT NULL,
  `mitroo_password` varchar(255) DEFAULT NULL,
  `mitroo_username` varchar(255) DEFAULT NULL,
  `mitroo_vatRepresentant` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `name` text DEFAULT NULL,
  `passMyData` varchar(255) DEFAULT NULL,
  `registeredName` varchar(255) DEFAULT NULL,
  `userMyData` varchar(255) DEFAULT NULL,
  `vatNumber` varchar(255) DEFAULT NULL,
  `vatStatus` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_company`
--

INSERT INTO `protoerp_company` (`id`, `active`, `companyEidos`, `companyMorfi`, `dateEnded`, `dateStarted`, `demoCompany`, `demoMyDataEnabled`, `demoPassMyData`, `demoUserMyData`, `email`, `job`, `mitroo_password`, `mitroo_username`, `mitroo_vatRepresentant`, `mobilePhone`, `name`, `passMyData`, `registeredName`, `userMyData`, `vatNumber`, `vatStatus`) VALUES
(1, b'1', NULL, NULL, NULL, NULL, b'1', b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'DEMO', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_company_kad`
--

CREATE TABLE `protoerp_company_kad` (
  `id` bigint(20) NOT NULL,
  `kadType` int(11) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `kad_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_company_plants`
--

CREATE TABLE `protoerp_company_plants` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tk` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `doy_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_countries`
--

CREATE TABLE `protoerp_countries` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `countryType` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `interDescription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_countries`
--

INSERT INTO `protoerp_countries` (`id`, `active`, `code`, `countryType`, `description`, `interDescription`) VALUES
(1, b'1', 'GR', 1, 'ΕΛΛΑΔΑ', 'GREECE'),
(2, b'1', 'AT', 1, 'ΑΥΣΤΡΙΑ', 'AUSTRIA'),
(3, b'1', 'BE', 1, 'ΒΕΛΓΙΟ', 'BELGIUM'),
(4, b'1', 'BG', 1, 'ΒΟΥΛΓΑΡΙΑ', 'BULGARIA'),
(5, b'1', 'FR', 1, 'ΓΑΛΛΙΑ', 'FRANCE'),
(6, b'1', 'DE', 1, 'ΓΕΡΜΑΝΙΑ', 'GERMANY'),
(7, b'1', 'DK', 1, 'ΔΑΝΙΑ', 'DENMARK'),
(8, b'1', 'EE', 1, 'ΕΣΘΟΝΙΑ', 'ESTONIA'),
(9, b'1', 'GB', 2, 'ΗΝΩΜ.ΒΑΣΙΛΕΙΟ', 'UNITED KINGDOM'),
(10, b'1', 'IE', 1, 'ΙΡΛΑΝΔΙΑ', 'IRELAND'),
(11, b'1', 'ES', 1, 'ΙΣΠΑΝΙΑ', 'SPAIN'),
(12, b'1', 'IT', 1, 'ΙΤΑΛΙΑ', 'ITALY'),
(13, b'1', 'NL', 1, 'ΚΑΤΩ ΧΩΡΕΣ (ΟΛΛΑΝΔΙΑ)', 'THE NETHERLANDS'),
(14, b'1', 'HR', 1, 'ΚΡΟΑΤΙΑ', 'CROATIA'),
(15, b'1', 'CY', 1, 'ΚΥΠΡΟΣ', 'CYPRUS'),
(16, b'1', 'LV', 1, 'ΛΕΤΟΝΙΑ', 'LATVIA'),
(17, b'1', 'LT', 1, 'ΛΙΘΟΥΑΝΙΑ', 'LITHUANIA'),
(18, b'1', 'LU', 1, 'ΛΟΥΞΕΜΒΟΥΡΓΟ', 'LUXEMBOURG'),
(19, b'1', 'MT', 1, 'ΜΑΛΤΑ', 'MALTA'),
(20, b'1', 'HU', 1, 'ΟΥΓΓΑΡΙΑ', 'HUNGARY'),
(21, b'1', 'PL', 1, 'ΠΟΛΩΝΙΑ', 'POLAND'),
(22, b'1', 'PT', 1, 'ΠΟΡΤΟΓΑΛΙΑ', 'PORTUGAL'),
(23, b'1', 'RO', 1, 'ΡΟΥΜΑΝΙΑ', 'ROMANIA'),
(24, b'1', 'SK', 1, 'ΣΛΟΒΑΚΙΑ', 'SLOVAKIA'),
(25, b'1', 'SI', 1, 'ΣΛΟΒΕΝΙΑ', 'SLOVENIA'),
(26, b'1', 'SE', 1, 'ΣΟΥΗΔΙΑ', 'SWEDEN'),
(27, b'1', 'CZ', 1, 'ΤΣΕΧΙΑ', 'CZECH REPUBLIC'),
(28, b'1', 'FI', 1, 'ΦΙΝΛΑΝΔΙΑ', 'FINLAND'),
(29, b'1', 'TR', 2, 'ΤΟΥΡΚΙΑ', 'REPUBLIC OF TURKEY'),
(30, b'1', 'RS', 2, 'ΣΕΡΒΙΑ', 'REPUBLIC OF SERBIA'),
(31, b'1', 'AL', 2, 'ΑΛΒΑΝΙΑ', 'ALBANIA');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_databasechangelog`
--

CREATE TABLE `protoerp_databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_databasechangeloglock`
--

CREATE TABLE `protoerp_databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_databasechangeloglock`
--

INSERT INTO `protoerp_databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_doy`
--

CREATE TABLE `protoerp_doy` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_doy`
--

INSERT INTO `protoerp_doy` (`id`, `active`, `code`, `name`) VALUES
(1, b'1', '1101', 'Α ΑΘΗΝΩΝ'),
(2, b'1', '1104', 'Δ ΑΘΗΝΩΝ'),
(3, b'1', '1106', 'ΣΤ ΑΘΗΝΩΝ'),
(4, b'1', '1112', 'ΙΒ ΑΘΗΝΩΝ'),
(5, b'1', '1113', 'ΙΓ ΑΘΗΝΩΝ'),
(6, b'1', '1114', 'ΙΔ ΑΘΗΝΩΝ'),
(7, b'1', '1117', 'ΙΖ ΑΘΗΝΩΝ'),
(8, b'1', '1118', 'ΚΕΝΤΡΟ ΕΛΕΓΧΟΥ ΜΕΓΑΛΩΝ ΕΠΙΧΕΙΡΗΣΕΩΝ (Κ.Ε.ΜΕ.ΕΠ)'),
(9, b'1', '1125', 'ΚΑΤΟΙΚΩΝ ΕΞΩΤΕΡΙΚΟΥ'),
(10, b'1', '1129', 'ΑΓΙΟΥ ΔΗΜΗΤΡΙΟΥ'),
(11, b'1', '1130', 'ΚΑΛΛΙΘΕΑΣ'),
(12, b'1', '1131', 'ΝΕΑΣ ΙΩΝΙΑΣ'),
(13, b'1', '1132', 'ΝΕΑΣ ΣΜΥΡΝΗΣ'),
(14, b'1', '1133', 'ΠΑΛΑΙΟΥ ΦΑΛΗΡΟΥ'),
(15, b'1', '1134', 'ΧΑΛΑΝΔΡΙΟΥ'),
(16, b'1', '1135', 'ΑΜΑΡΟΥΣΙΟΥ'),
(17, b'1', '1136', 'ΑΓΙΩΝ ΑΝΑΡΓΥΡΩΝ'),
(18, b'1', '1137', 'ΑΙΓΑΛΕΩ'),
(19, b'1', '1138', 'Α ΠΕΡΙΣΤΕΡΙΟΥ'),
(20, b'1', '1139', 'ΓΛΥΦΑΔΑΣ'),
(21, b'1', '1145', 'Ν.ΗΡΑΚΛΕΙΟΥ'),
(22, b'1', '1151', 'ΧΟΛΑΡΓΟΥ'),
(23, b'1', '1152', 'ΒΥΡΩΝΟΣ'),
(24, b'1', '1153', 'ΚΗΦΙΣΙΑΣ'),
(25, b'1', '1157', 'Β ΠΕΡΙΣΤΕΡΙΟΥ'),
(26, b'1', '1159', 'Φ.Α.Ε. ΑΘΗΝΩΝ'),
(27, b'1', '1173', 'ΗΛΙΟΥΠΟΛΗΣ'),
(28, b'1', '1175', 'ΨΥΧΙΚΟΥ'),
(29, b'1', '1179', 'ΓΑΛΑΤΣΙΟΥ'),
(30, b'1', '1201', 'Α ΠΕΙΡΑΙΑ'),
(31, b'1', '1203', 'Γ ΠΕΙΡΑΙΑ'),
(32, b'1', '1204', 'Δ ΠΕΙΡΑΙΑ'),
(33, b'1', '1205', 'Ε ΠΕΙΡΑΙΑ'),
(34, b'1', '1206', 'ΦΑΕ ΠΕΙΡΑΙΑ'),
(35, b'1', '1207', 'ΠΛΟΙΩΝ ΠΕΙΡΑΙΑ'),
(36, b'1', '1211', 'ΜΟΣΧΑΤΟΥ'),
(37, b'1', '1220', 'ΝΙΚΑΙΑΣ'),
(38, b'1', '1302', 'ΑΧΑΡΝΩΝ'),
(39, b'1', '1303', 'ΕΛΕΥΣΙΝΑΣ'),
(40, b'1', '1304', 'ΚΟΡΩΠΙΟΥ'),
(41, b'1', '1312', 'ΠΑΛΛΗΝΗΣ'),
(42, b'1', '1411', 'ΘΗΒΩΝ'),
(43, b'1', '1421', 'ΛΙΒΑΔΕΙΑΣ'),
(44, b'1', '1531', 'ΜΕΣΟΛΟΓΓΙΟΥ'),
(45, b'1', '1552', 'ΑΓΡΙΝΙΟΥ'),
(46, b'1', '1611', 'ΚΑΡΠΕΝΗΣΙΟΥ'),
(47, b'1', '1722', 'ΚΥΜΗΣ'),
(48, b'1', '1732', 'ΧΑΛΚΙΔΑΣ'),
(49, b'1', '1832', 'ΛΑΜΙΑΣ'),
(50, b'1', '1912', 'ΑΜΦΙΣΣΑΣ'),
(51, b'1', '2111', 'ΑΡΓΟΥΣ'),
(52, b'1', '2131', 'ΝΑΥΠΛΙΟΥ'),
(53, b'1', '2231', 'ΤΡΙΠΟΛΗΣ'),
(54, b'1', '2311', 'ΑΙΓΙΟΥ'),
(55, b'1', '2331', 'Α ΠΑΤΡΩΝ'),
(56, b'1', '2334', 'Γ ΠΑΤΡΩΝ'),
(57, b'1', '2411', 'ΑΜΑΛΙΑΔΑΣ'),
(58, b'1', '2412', 'ΠΥΡΓΟΥ'),
(59, b'1', '2513', 'ΚΟΡΙΝΘΟΥ'),
(60, b'1', '2632', 'ΣΠΑΡΤΗΣ'),
(61, b'1', '2711', 'ΚΑΛΑΜΑΤΑΣ'),
(62, b'1', '3111', 'ΚΑΡΔΙΤΣΑΣ'),
(63, b'1', '3231', 'Α ΛΑΡΙΣΑΣ'),
(64, b'1', '3232', 'Β ΛΑΡΙΣΑΣ'),
(65, b'1', '3321', 'ΒΟΛΟΥ'),
(66, b'1', '3323', 'Ν.ΙΩΝΙΑΣ ΒΟΛΟΥ'),
(67, b'1', '3412', 'ΤΡΙΚΑΛΩΝ'),
(68, b'1', '4112', 'ΒΕΡΟΙΑΣ'),
(69, b'1', '4211', 'Α ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(70, b'1', '4214', 'Δ ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(71, b'1', '4215', 'Ε ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(72, b'1', '4216', 'ΣΤ ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(73, b'1', '4217', 'Ζ ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(74, b'1', '4222', 'ΛΑΓΚΑΔΑ'),
(75, b'1', '4224', 'ΦΑΕ ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(76, b'1', '4228', 'Η ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(77, b'1', '4232', 'ΚΑΛΑΜΑΡΙΑΣ'),
(78, b'1', '4233', 'ΑΜΠΕΛΟΚΗΠΩΝ'),
(79, b'1', '4234', 'ΙΩΝΙΑΣ ΘΕΣΣΑΛΟΝΙΚΗΣ'),
(80, b'1', '4311', 'ΚΑΣΤΟΡΙΑΣ'),
(81, b'1', '4411', 'ΚΙΛΚΙΣ'),
(82, b'1', '4521', 'ΓΡΕΒΕΝΩΝ'),
(83, b'1', '4531', 'ΠΤΟΛΕΜΑΙΔΑΣ'),
(84, b'1', '4541', 'ΚΟΖΑΝΗΣ'),
(85, b'1', '4621', 'ΓΙΑΝΝΙΤΣΩΝ'),
(86, b'1', '4631', 'ΕΔΕΣΣΑΣ'),
(87, b'1', '4711', 'ΚΑΤΕΡΙΝΗΣ'),
(88, b'1', '4812', 'ΦΛΩΡΙΝΑΣ'),
(89, b'1', '4922', 'ΠΟΛΥΓΥΡΟΥ'),
(90, b'1', '4923', 'ΝΕΩΝ ΜΟΥΔΑΝΙΩΝ'),
(91, b'1', '5111', 'ΔΡΑΜΑΣ'),
(92, b'1', '5211', 'ΑΛΕΞΑΝΔΡΟΥΠΟΛΗΣ'),
(93, b'1', '5231', 'ΟΡΕΣΤΙΑΔΑΣ'),
(94, b'1', '5321', 'ΚΑΒΑΛΑΣ'),
(95, b'1', '5411', 'ΞΑΝΘΗΣ'),
(96, b'1', '5511', 'ΚΟΜΟΤΗΝΗΣ'),
(97, b'1', '5621', 'ΣΕΡΡΩΝ'),
(98, b'1', '6111', 'ΑΡΤΑΣ'),
(99, b'1', '6211', 'ΗΓΟΥΜΕΝΙΤΣΑΣ'),
(100, b'1', '6311', 'ΙΩΑΝΝΙΝΩΝ'),
(101, b'1', '6411', 'ΠΡΕΒΕΖΑΣ'),
(102, b'1', '7121', 'ΘΗΡΑΣ'),
(103, b'1', '7151', 'ΝΑΞΟΥ'),
(104, b'1', '7161', 'ΠΑΡΟΥ'),
(105, b'1', '7171', 'ΣΥΡΟΥ'),
(106, b'1', '7172', 'ΜΥΚΟΝΟΥ'),
(107, b'1', '7231', 'ΜΥΤΙΛΗΝΗΣ'),
(108, b'1', '7322', 'ΣΑΜΟΥ'),
(109, b'1', '7411', 'ΧΙΟΥ'),
(110, b'1', '7531', 'ΚΩ'),
(111, b'1', '7542', 'ΡΟΔΟΥ'),
(112, b'1', '8110', 'ΗΡΑΚΛΕΙΟΥ'),
(113, b'1', '8221', 'ΑΓΙΟΥ ΝΙΚΟΛΑΟΥ'),
(114, b'1', '8341', 'ΡΕΘΥΜΝΟΥ'),
(115, b'1', '8431', 'ΧΑΝΙΩΝ'),
(116, b'1', '9111', 'ΖΑΚΥΝΘΟΥ'),
(117, b'1', '9211', 'ΚΕΡΚΥΡΑΣ'),
(118, b'1', '9311', 'ΑΡΓΟΣΤΟΛΙΟΥ'),
(119, b'1', '9421', 'ΛΕΥΚΑΔΑΣ');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_invoicelines`
--

CREATE TABLE `protoerp_invoicelines` (
  `id` bigint(20) NOT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `discount` decimal(19,2) DEFAULT NULL,
  `measureShortName` varchar(255) DEFAULT NULL,
  `percentDisc` decimal(19,2) DEFAULT NULL,
  `posIndex` int(11) DEFAULT NULL,
  `quantity` decimal(19,2) DEFAULT NULL,
  `total` decimal(19,2) DEFAULT NULL,
  `totalNoDisc` decimal(19,2) DEFAULT NULL,
  `unitPrice` decimal(19,2) DEFAULT NULL,
  `vatRate` decimal(19,2) DEFAULT NULL,
  `invoice_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `measunit_id` bigint(20) NOT NULL,
  `vat_id` bigint(20) NOT NULL,
  `vatexemp_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_invoices`
--

CREATE TABLE `protoerp_invoices` (
  `id` bigint(20) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `invoiceNumber` int(11) DEFAULT NULL,
  `invoiceStatus` int(11) DEFAULT NULL,
  `relativeInvoices` varchar(255) DEFAULT NULL,
  `totalDiscount` decimal(19,2) DEFAULT NULL,
  `totalNoVatAfterDiscValue` decimal(19,2) DEFAULT NULL,
  `totalNoVatValue` decimal(19,2) DEFAULT NULL,
  `totalPayValue` decimal(19,2) DEFAULT NULL,
  `totalValue` decimal(19,2) DEFAULT NULL,
  `totalVatValue` decimal(19,2) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `invoiceType_id` bigint(20) DEFAULT NULL,
  `paymethod_id` bigint(20) DEFAULT NULL,
  `traderPlant_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_invoice_types`
--

CREATE TABLE `protoerp_invoice_types` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `printFormVer` varchar(255) DEFAULT NULL,
  `seira` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  `company_plant_id` bigint(20) DEFAULT NULL,
  `mydata_invoiceType_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_items`
--

CREATE TABLE `protoerp_items` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `itemType` int(11) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `sellPrice` decimal(19,2) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `measunit_id` bigint(20) DEFAULT NULL,
  `vatexemp_id` bigint(20) DEFAULT NULL,
  `vatsell_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_kad`
--

CREATE TABLE `protoerp_kad` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_measurement_units`
--

CREATE TABLE `protoerp_measurement_units` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `mydata_measunit_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_mydata_invoicetype`
--

CREATE TABLE `protoerp_mydata_invoicetype` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_mydata_invoicetype`
--

INSERT INTO `protoerp_mydata_invoicetype` (`id`, `active`, `description`, `type`) VALUES
(1, b'1', 'Τιμολόγιο Πώλησης', '1.1'),
(2, b'1', 'Τιμολόγιο Πώλησης / Ενδοκοινοτικές Παραδόσεις', '1.2'),
(3, b'1', 'Τιμολόγιο Πώλησης / Παραδόσεις Τρίτων Χωρών', '1.3'),
(4, b'1', 'Τιμολόγιο Πώλησης / Πώληση για Λογαριασμό Τρίτων', '1.4'),
(5, b'1', 'Τιμολόγιο Πώλησης / Εκκαθάριση Πωλήσεων Τρίτων - Αμοιβή από Πωλήσεις Τρίτων', '1.5'),
(6, b'1', 'Τιμολόγιο Πώλησης / Συμπληρωματικό Παραστατικό', '1.6'),
(7, b'1', 'Τιμολόγιο Παροχής Υπηρεσιών', '2.1'),
(8, b'1', 'Τιμολόγιο Παροχής / Ενδοκοινοτική Παροχή Υπηρεσιών', '2.2'),
(9, b'1', 'Τιμολόγιο Παροχής / Παροχή Υπηρεσιών Τρίτων Χωρών', '2.3'),
(10, b'1', 'Τιμολόγιο Παροχής / Συμπληρωματικό Παραστατικό', '2.4'),
(11, b'1', 'Τίτλος Κτήσης (μη υπόχρεος Εκδότης)', '3.1'),
(12, b'1', 'Τίτλος Κτήσης (άρνηση έκδοσης από υπόχρεο Εκδότη)', '3.2'),
(13, b'1', 'Πιστωτικό Τιμολόγιο / Συσχετιζόμενο', '5.1'),
(14, b'1', 'Πιστωτικό Τιμολόγιο / Μη Συσχετιζόμενο', '5.2'),
(15, b'1', 'Στοιχείο Αυτοπαράδοσης', '6.1'),
(16, b'1', 'Στοιχείο Ιδιοχρησιμοποίησης', '6.2'),
(17, b'1', 'Συμβόλαιο - Έσοδο', '7.1'),
(18, b'1', 'Ενοίκια - Έσοδο', '8.1'),
(19, b'1', 'Ειδικό Στοιχείο – Απόδειξης Είσπραξης Φόρου Διαμονής', '8.2'),
(20, b'1', 'ΑΛΠ', '11.1'),
(21, b'1', 'ΑΠΥ', '11.2'),
(22, b'1', 'Απλοποιημένο Τιμολόγιο', '11.3'),
(23, b'1', 'Πιστωτικό Στοιχ. Λιανικής', '11.4'),
(24, b'1', 'Απόδειξη Λιανικής Πώλησης για Λογ/σμό Τρίτων', '11.5');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_mydata_measureunit`
--

CREATE TABLE `protoerp_mydata_measureunit` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_mydata_measureunit`
--

INSERT INTO `protoerp_mydata_measureunit` (`id`, `active`, `code`, `description`) VALUES
(1, b'1', -1, 'Χωρίς'),
(2, b'1', 1, 'Τεμάχια'),
(3, b'1', 2, 'Κιλά'),
(4, b'1', 3, 'Λίτρα');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_mydata_paymentmethod`
--

CREATE TABLE `protoerp_mydata_paymentmethod` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_mydata_paymentmethod`
--

INSERT INTO `protoerp_mydata_paymentmethod` (`id`, `active`, `code`, `description`) VALUES
(1, b'1', 1, 'Επαγ. Λογαριασμός Πληρωμών Ημεδαπής'),
(2, b'1', 2, 'Επαγ. Λογαριασμός Πληρωμών Αλλοδαπής'),
(3, b'1', 3, 'Μετρητά'),
(4, b'1', 4, 'Επιταγή'),
(5, b'1', 5, 'Επί Πιστώσει');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_mydata_vat`
--

CREATE TABLE `protoerp_mydata_vat` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `vatRate` decimal(19,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_mydata_vat`
--

INSERT INTO `protoerp_mydata_vat` (`id`, `active`, `code`, `description`, `vatRate`) VALUES
(1, b'1', 1, 'ΦΠΑ συντελεστής 24%', '24.00'),
(2, b'1', 2, 'ΦΠΑ συντελεστής 13%', '13.00'),
(3, b'1', 3, 'ΦΠΑ συντελεστής 6%', '6.00'),
(4, b'1', 4, 'ΦΠΑ συντελεστής 17%', '17.00'),
(5, b'1', 5, 'ΦΠΑ συντελεστής 9%', '9.00'),
(6, b'1', 6, 'ΦΠΑ συντελεστής 4%', '4.00'),
(7, b'1', 7, 'Άνευ Φ.Π.Α.', '0.00'),
(8, b'1', 8, 'Εγγραφές χωρίς ΦΠΑ', '-1.00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_mydata_vatexemption`
--

CREATE TABLE `protoerp_mydata_vatexemption` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_mydata_vatexemption`
--

INSERT INTO `protoerp_mydata_vatexemption` (`id`, `active`, `code`, `description`) VALUES
(1, b'1', 1, 'Χωρίς ΦΠΑ - άρθρο 2 και 3  του Κώδικα ΦΠΑ'),
(2, b'1', 2, 'Χωρίς ΦΠΑ - άρθρο 5 του Κώδικα ΦΠΑ'),
(3, b'1', 3, 'Χωρίς ΦΠΑ - άρθρο 13 του Κώδικα ΦΠΑ'),
(4, b'1', 4, 'Χωρίς ΦΠΑ - άρθρο 14 του Κώδικα ΦΠΑ'),
(5, b'1', 5, 'Χωρίς ΦΠΑ - άρθρο 16 του Κώδικα ΦΠΑ'),
(6, b'1', 6, 'Χωρίς ΦΠΑ - άρθρο 19 του Κώδικα ΦΠΑ'),
(7, b'1', 7, 'Χωρίς ΦΠΑ - άρθρο 22 του Κώδικα ΦΠΑ'),
(8, b'1', 8, 'Χωρίς ΦΠΑ - άρθρο 24 του Κώδικα ΦΠΑ'),
(9, b'1', 9, 'Χωρίς ΦΠΑ - άρθρο 25 του Κώδικα ΦΠΑ'),
(10, b'1', 10, 'Χωρίς ΦΠΑ - άρθρο 26 του Κώδικα ΦΠΑ'),
(11, b'1', 11, 'Χωρίς ΦΠΑ - άρθρο 27 του Κώδικα ΦΠΑ'),
(12, b'1', 12, 'Χωρίς ΦΠΑ - άρθρο 27 - Πλοία Ανοικτής Θαλάσσης του Κώδικα ΦΠΑ'),
(13, b'1', 13, 'Χωρίς ΦΠΑ - άρθρο 27.1.γ - Πλοία Ανοικτής Θαλάσσης του Κώδικα ΦΠΑ'),
(14, b'1', 14, 'Χωρίς ΦΠΑ - άρθρο 28 του Κώδικα ΦΠΑ'),
(15, b'1', 15, 'Χωρίς ΦΠΑ - άρθρο 39 του Κώδικα ΦΠΑ'),
(16, b'1', 16, 'Χωρίς ΦΠΑ - άρθρο 39α του Κώδικα ΦΠΑ'),
(17, b'1', 17, 'Χωρίς ΦΠΑ - άρθρο 40 του Κώδικα ΦΠΑ'),
(18, b'1', 18, 'Χωρίς ΦΠΑ - άρθρο 41 του Κώδικα ΦΠΑ'),
(19, b'1', 19, 'Χωρίς ΦΠΑ - άρθρο 47 του Κώδικα ΦΠΑ'),
(20, b'1', 20, 'ΦΠΑ εμπεριεχόμενος - άρθρο 43 του Κώδικα ΦΠΑ'),
(21, b'1', 21, 'ΦΠΑ εμπεριεχόμενος - άρθρο 44 του Κώδικα ΦΠΑ'),
(22, b'1', 22, 'ΦΠΑ εμπεριεχόμενος - άρθρο 45 του Κώδικα ΦΠΑ'),
(23, b'1', 23, 'ΦΠΑ εμπεριεχόμενος - άρθρο 46 του Κώδικα ΦΠΑ'),
(24, b'1', 24, 'Χωρίς ΦΠΑ - άρθρο 6 του Κώδικα ΦΠΑ');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_paymentmethods`
--

CREATE TABLE `protoerp_paymentmethods` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `autoPayment` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `mydata_paymethod_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_permissions`
--

CREATE TABLE `protoerp_permissions` (
  `id` bigint(20) NOT NULL,
  `permissionDisplayName` varchar(255) DEFAULT NULL,
  `permissionName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_permissions`
--

INSERT INTO `protoerp_permissions` (`id`, `permissionDisplayName`, `permissionName`) VALUES
(1, 'Διαχείριση χρηστών', 'USER_MANAGEMENT'),
(2, 'Εμφάνιση Κωδικών', 'SHOW_PASSWORDS');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_roles`
--

CREATE TABLE `protoerp_roles` (
  `id` bigint(20) NOT NULL,
  `roleName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_roles`
--

INSERT INTO `protoerp_roles` (`id`, `roleName`) VALUES
(1, 'admin');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_role_permission`
--

CREATE TABLE `protoerp_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_role_permission`
--

INSERT INTO `protoerp_role_permission` (`role_id`, `permission_id`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_settings`
--

CREATE TABLE `protoerp_settings` (
  `id` bigint(20) NOT NULL,
  `companyId` int(11) NOT NULL,
  `settingName` varchar(255) DEFAULT NULL,
  `settingValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_settings`
--

INSERT INTO `protoerp_settings` (`id`, `companyId`, `settingName`, `settingValue`) VALUES
(1, -1, 'product_version', '0.0.339'),
(2, -1, 'valuesDecimal', '2'),
(3, -1, 'unitDecimal', '2'),
(4, -1, 'quantityDecimal', '2'),
(5, -1, 'percentDiscountDecimal', '2'),
(6, -1, 'percentVatDecimal', '2');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_tablesettings`
--

CREATE TABLE `protoerp_tablesettings` (
  `id` bigint(20) NOT NULL,
  `tableKey` varchar(255) DEFAULT NULL,
  `tableValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_traders`
--

CREATE TABLE `protoerp_traders` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `registeredName` varchar(255) DEFAULT NULL,
  `vatNumber` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `doy_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_trader_plants`
--

CREATE TABLE `protoerp_trader_plants` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tk` varchar(255) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `trader_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_users`
--

CREATE TABLE `protoerp_users` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_users`
--

INSERT INTO `protoerp_users` (`id`, `active`, `name`, `password`, `username`) VALUES
(1, b'1', 'admin', NULL, 'admin');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_user_role`
--

CREATE TABLE `protoerp_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `protoerp_user_role`
--

INSERT INTO `protoerp_user_role` (`user_id`, `role_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `protoerp_vat`
--

CREATE TABLE `protoerp_vat` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `vatRate` decimal(19,2) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `mydata_vat_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `protoerp_company`
--
ALTER TABLE `protoerp_company`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_company_kad`
--
ALTER TABLE `protoerp_company_kad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrv70ivm6p2bs2vao8dgrgbnpg` (`company_id`),
  ADD KEY `FKugxc0ltm01b7j360h1gemuei` (`kad_id`);

--
-- Ευρετήρια για πίνακα `protoerp_company_plants`
--
ALTER TABLE `protoerp_company_plants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6sc3stwc4l9rnwnab7ekb2eri` (`company_id`),
  ADD KEY `FK8h3cdj42fn0yj2ajms1yxwwt5` (`country_id`),
  ADD KEY `FK1pmdomijnyvce64xw621cbc9s` (`doy_id`);

--
-- Ευρετήρια για πίνακα `protoerp_countries`
--
ALTER TABLE `protoerp_countries`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_databasechangeloglock`
--
ALTER TABLE `protoerp_databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Ευρετήρια για πίνακα `protoerp_doy`
--
ALTER TABLE `protoerp_doy`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_invoicelines`
--
ALTER TABLE `protoerp_invoicelines`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh98dwddddo9997d4qtfmhe5wm` (`invoice_id`),
  ADD KEY `FKq51eomwkksdta0phy9pn6urnl` (`item_id`),
  ADD KEY `FKmfib0ti0c2fk9qsvwj63vww4e` (`measunit_id`),
  ADD KEY `FK3qnkfi6tih4dxj9puaogunvt3` (`vat_id`),
  ADD KEY `FK17f3hjpsx7akj19311jyv7uxm` (`vatexemp_id`);

--
-- Ευρετήρια για πίνακα `protoerp_invoices`
--
ALTER TABLE `protoerp_invoices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl9oxvccl7ysbqv9mhj6fo7hn5` (`company_id`),
  ADD KEY `FKih9ph8j2srn54fusjdkkldrvg` (`invoiceType_id`),
  ADD KEY `FKt60bhbcwotm13tvpfsbn2pvmt` (`paymethod_id`),
  ADD KEY `FKd6h3s5rgy4umlnkekm293wvbh` (`traderPlant_id`);

--
-- Ευρετήρια για πίνακα `protoerp_invoice_types`
--
ALTER TABLE `protoerp_invoice_types`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq7b8k91m78jawi6q7tdakloyf` (`company_plant_id`),
  ADD KEY `FKvjfog4vul5q7m35yf9e9plan` (`mydata_invoiceType_id`);

--
-- Ευρετήρια για πίνακα `protoerp_items`
--
ALTER TABLE `protoerp_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn4bp5gl2t7if1pwn1a8vb98v` (`company_id`),
  ADD KEY `FKcivukyq713enwjeh6jp7itoex` (`measunit_id`),
  ADD KEY `FK5qb9ksow28dbqaiu5m6urx8kg` (`vatexemp_id`),
  ADD KEY `FK7b2rqadcwomthnqvey0rv59mf` (`vatsell_id`);

--
-- Ευρετήρια για πίνακα `protoerp_kad`
--
ALTER TABLE `protoerp_kad`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_measurement_units`
--
ALTER TABLE `protoerp_measurement_units`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK62oo337g92y93piy7039eym3p` (`company_id`),
  ADD KEY `FKloxf7undmtdorqg6ftibmpw35` (`mydata_measunit_id`);

--
-- Ευρετήρια για πίνακα `protoerp_mydata_invoicetype`
--
ALTER TABLE `protoerp_mydata_invoicetype`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_mydata_measureunit`
--
ALTER TABLE `protoerp_mydata_measureunit`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_mydata_paymentmethod`
--
ALTER TABLE `protoerp_mydata_paymentmethod`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_mydata_vat`
--
ALTER TABLE `protoerp_mydata_vat`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_mydata_vatexemption`
--
ALTER TABLE `protoerp_mydata_vatexemption`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_paymentmethods`
--
ALTER TABLE `protoerp_paymentmethods`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKo12l9b51ytmes932y0j9yqmaf` (`company_id`),
  ADD KEY `FKnogvff2escr7damu5f4mpgckh` (`mydata_paymethod_id`);

--
-- Ευρετήρια για πίνακα `protoerp_permissions`
--
ALTER TABLE `protoerp_permissions`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_roles`
--
ALTER TABLE `protoerp_roles`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_role_permission`
--
ALTER TABLE `protoerp_role_permission`
  ADD KEY `FKlvufidbugij3e7a2ey1t6yl16` (`permission_id`),
  ADD KEY `FKtp4q3md5soh6d6o8ty6k2847h` (`role_id`);

--
-- Ευρετήρια για πίνακα `protoerp_settings`
--
ALTER TABLE `protoerp_settings`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_tablesettings`
--
ALTER TABLE `protoerp_tablesettings`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_traders`
--
ALTER TABLE `protoerp_traders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp5c71v5stfujmntwpw1n7sp5b` (`company_id`),
  ADD KEY `FK9271l0hu6tf9bdj6uxch3h9vp` (`doy_id`);

--
-- Ευρετήρια για πίνακα `protoerp_trader_plants`
--
ALTER TABLE `protoerp_trader_plants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKf6wr4om3o3qpwt6g3nwc7ub3b` (`country_id`),
  ADD KEY `FKp5o3r7871jstkwmktkgn6ic` (`trader_id`);

--
-- Ευρετήρια για πίνακα `protoerp_users`
--
ALTER TABLE `protoerp_users`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `protoerp_user_role`
--
ALTER TABLE `protoerp_user_role`
  ADD KEY `FKp79ceuhf3thhumkno5a4mb3o2` (`role_id`),
  ADD KEY `FK2ir0bcgqsp839ephsey4j3ty2` (`user_id`);

--
-- Ευρετήρια για πίνακα `protoerp_vat`
--
ALTER TABLE `protoerp_vat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgbum9rokb6vvbxkq5ihkufprk` (`company_id`),
  ADD KEY `FKhtrr2mpe5vun9lfgdyqe2oygx` (`mydata_vat_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `protoerp_company`
--
ALTER TABLE `protoerp_company`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT για πίνακα `protoerp_company_kad`
--
ALTER TABLE `protoerp_company_kad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_company_plants`
--
ALTER TABLE `protoerp_company_plants`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_countries`
--
ALTER TABLE `protoerp_countries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT για πίνακα `protoerp_doy`
--
ALTER TABLE `protoerp_doy`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=120;

--
-- AUTO_INCREMENT για πίνακα `protoerp_invoicelines`
--
ALTER TABLE `protoerp_invoicelines`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_invoices`
--
ALTER TABLE `protoerp_invoices`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_invoice_types`
--
ALTER TABLE `protoerp_invoice_types`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_items`
--
ALTER TABLE `protoerp_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_kad`
--
ALTER TABLE `protoerp_kad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_measurement_units`
--
ALTER TABLE `protoerp_measurement_units`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_mydata_invoicetype`
--
ALTER TABLE `protoerp_mydata_invoicetype`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT για πίνακα `protoerp_mydata_measureunit`
--
ALTER TABLE `protoerp_mydata_measureunit`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `protoerp_mydata_paymentmethod`
--
ALTER TABLE `protoerp_mydata_paymentmethod`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `protoerp_mydata_vat`
--
ALTER TABLE `protoerp_mydata_vat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT για πίνακα `protoerp_mydata_vatexemption`
--
ALTER TABLE `protoerp_mydata_vatexemption`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT για πίνακα `protoerp_paymentmethods`
--
ALTER TABLE `protoerp_paymentmethods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_permissions`
--
ALTER TABLE `protoerp_permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT για πίνακα `protoerp_roles`
--
ALTER TABLE `protoerp_roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT για πίνακα `protoerp_settings`
--
ALTER TABLE `protoerp_settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `protoerp_tablesettings`
--
ALTER TABLE `protoerp_tablesettings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_traders`
--
ALTER TABLE `protoerp_traders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_trader_plants`
--
ALTER TABLE `protoerp_trader_plants`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `protoerp_users`
--
ALTER TABLE `protoerp_users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT για πίνακα `protoerp_vat`
--
ALTER TABLE `protoerp_vat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `protoerp_company_kad`
--
ALTER TABLE `protoerp_company_kad`
  ADD CONSTRAINT `FKrv70ivm6p2bs2vao8dgrgbnpg` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`),
  ADD CONSTRAINT `FKugxc0ltm01b7j360h1gemuei` FOREIGN KEY (`kad_id`) REFERENCES `protoerp_kad` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_company_plants`
--
ALTER TABLE `protoerp_company_plants`
  ADD CONSTRAINT `FK1pmdomijnyvce64xw621cbc9s` FOREIGN KEY (`doy_id`) REFERENCES `protoerp_doy` (`id`),
  ADD CONSTRAINT `FK6sc3stwc4l9rnwnab7ekb2eri` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`),
  ADD CONSTRAINT `FK8h3cdj42fn0yj2ajms1yxwwt5` FOREIGN KEY (`country_id`) REFERENCES `protoerp_countries` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_invoicelines`
--
ALTER TABLE `protoerp_invoicelines`
  ADD CONSTRAINT `FK17f3hjpsx7akj19311jyv7uxm` FOREIGN KEY (`vatexemp_id`) REFERENCES `protoerp_mydata_vatexemption` (`id`),
  ADD CONSTRAINT `FK3qnkfi6tih4dxj9puaogunvt3` FOREIGN KEY (`vat_id`) REFERENCES `protoerp_vat` (`id`),
  ADD CONSTRAINT `FKh98dwddddo9997d4qtfmhe5wm` FOREIGN KEY (`invoice_id`) REFERENCES `protoerp_invoices` (`id`),
  ADD CONSTRAINT `FKmfib0ti0c2fk9qsvwj63vww4e` FOREIGN KEY (`measunit_id`) REFERENCES `protoerp_measurement_units` (`id`),
  ADD CONSTRAINT `FKq51eomwkksdta0phy9pn6urnl` FOREIGN KEY (`item_id`) REFERENCES `protoerp_items` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_invoices`
--
ALTER TABLE `protoerp_invoices`
  ADD CONSTRAINT `FKd6h3s5rgy4umlnkekm293wvbh` FOREIGN KEY (`traderPlant_id`) REFERENCES `protoerp_trader_plants` (`id`),
  ADD CONSTRAINT `FKih9ph8j2srn54fusjdkkldrvg` FOREIGN KEY (`invoiceType_id`) REFERENCES `protoerp_invoice_types` (`id`),
  ADD CONSTRAINT `FKl9oxvccl7ysbqv9mhj6fo7hn5` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`),
  ADD CONSTRAINT `FKt60bhbcwotm13tvpfsbn2pvmt` FOREIGN KEY (`paymethod_id`) REFERENCES `protoerp_paymentmethods` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_invoice_types`
--
ALTER TABLE `protoerp_invoice_types`
  ADD CONSTRAINT `FKq7b8k91m78jawi6q7tdakloyf` FOREIGN KEY (`company_plant_id`) REFERENCES `protoerp_company_plants` (`id`),
  ADD CONSTRAINT `FKvjfog4vul5q7m35yf9e9plan` FOREIGN KEY (`mydata_invoiceType_id`) REFERENCES `protoerp_mydata_invoicetype` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_items`
--
ALTER TABLE `protoerp_items`
  ADD CONSTRAINT `FK5qb9ksow28dbqaiu5m6urx8kg` FOREIGN KEY (`vatexemp_id`) REFERENCES `protoerp_mydata_vatexemption` (`id`),
  ADD CONSTRAINT `FK7b2rqadcwomthnqvey0rv59mf` FOREIGN KEY (`vatsell_id`) REFERENCES `protoerp_vat` (`id`),
  ADD CONSTRAINT `FKcivukyq713enwjeh6jp7itoex` FOREIGN KEY (`measunit_id`) REFERENCES `protoerp_measurement_units` (`id`),
  ADD CONSTRAINT `FKn4bp5gl2t7if1pwn1a8vb98v` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_measurement_units`
--
ALTER TABLE `protoerp_measurement_units`
  ADD CONSTRAINT `FK62oo337g92y93piy7039eym3p` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`),
  ADD CONSTRAINT `FKloxf7undmtdorqg6ftibmpw35` FOREIGN KEY (`mydata_measunit_id`) REFERENCES `protoerp_mydata_measureunit` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_paymentmethods`
--
ALTER TABLE `protoerp_paymentmethods`
  ADD CONSTRAINT `FKnogvff2escr7damu5f4mpgckh` FOREIGN KEY (`mydata_paymethod_id`) REFERENCES `protoerp_mydata_paymentmethod` (`id`),
  ADD CONSTRAINT `FKo12l9b51ytmes932y0j9yqmaf` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_role_permission`
--
ALTER TABLE `protoerp_role_permission`
  ADD CONSTRAINT `FKlvufidbugij3e7a2ey1t6yl16` FOREIGN KEY (`permission_id`) REFERENCES `protoerp_permissions` (`id`),
  ADD CONSTRAINT `FKtp4q3md5soh6d6o8ty6k2847h` FOREIGN KEY (`role_id`) REFERENCES `protoerp_roles` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_traders`
--
ALTER TABLE `protoerp_traders`
  ADD CONSTRAINT `FK9271l0hu6tf9bdj6uxch3h9vp` FOREIGN KEY (`doy_id`) REFERENCES `protoerp_doy` (`id`),
  ADD CONSTRAINT `FKp5c71v5stfujmntwpw1n7sp5b` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_trader_plants`
--
ALTER TABLE `protoerp_trader_plants`
  ADD CONSTRAINT `FKf6wr4om3o3qpwt6g3nwc7ub3b` FOREIGN KEY (`country_id`) REFERENCES `protoerp_countries` (`id`),
  ADD CONSTRAINT `FKp5o3r7871jstkwmktkgn6ic` FOREIGN KEY (`trader_id`) REFERENCES `protoerp_traders` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_user_role`
--
ALTER TABLE `protoerp_user_role`
  ADD CONSTRAINT `FK2ir0bcgqsp839ephsey4j3ty2` FOREIGN KEY (`user_id`) REFERENCES `protoerp_users` (`id`),
  ADD CONSTRAINT `FKp79ceuhf3thhumkno5a4mb3o2` FOREIGN KEY (`role_id`) REFERENCES `protoerp_roles` (`id`);

--
-- Περιορισμοί για πίνακα `protoerp_vat`
--
ALTER TABLE `protoerp_vat`
  ADD CONSTRAINT `FKgbum9rokb6vvbxkq5ihkufprk` FOREIGN KEY (`company_id`) REFERENCES `protoerp_company` (`id`),
  ADD CONSTRAINT `FKhtrr2mpe5vun9lfgdyqe2oygx` FOREIGN KEY (`mydata_vat_id`) REFERENCES `protoerp_mydata_vat` (`id`);
COMMIT;