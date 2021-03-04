--
-- Δομή πίνακα για τον πίνακα `countries`
--

CREATE TABLE `countries` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `countryType` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `interDescription` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `countries`
--

INSERT INTO `countries` (`id`, `active`, `code`, `countryType`, `description`, `interDescription`) VALUES
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
-- Δομή πίνακα για τον πίνακα `databasechangelog`
--

CREATE TABLE `databasechangelog` (
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
-- Δομή πίνακα για τον πίνακα `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `doy`
--

CREATE TABLE `doy` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `doy`
--

INSERT INTO `doy` (`id`, `active`, `code`, `name`) VALUES
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
-- Δομή πίνακα για τον πίνακα `permissions`
--

CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL,
  `permissionDisplayName` varchar(255) DEFAULT NULL,
  `permissionName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `permissions`
--

INSERT INTO `permissions` (`id`, `permissionDisplayName`, `permissionName`) VALUES
(1, 'Διαχείριση χρηστών', 'USER_MANAGEMENT');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `roleName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `roles`
--

INSERT INTO `roles` (`id`, `roleName`) VALUES
(1, 'admin');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `role_permission`
--

CREATE TABLE `role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `role_permission`
--

INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `settings`
--

CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL,
  `companyId` int(11) NOT NULL,
  `settingName` varchar(255) DEFAULT NULL,
  `settingValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `settings`
--

INSERT INTO `settings` (`id`, `companyId`, `settingName`, `settingValue`) VALUES
(1, -1, 'product_version', '0.0.170'),
(2, -1, 'mitroo_username', ''),
(3, -1, 'mitroo_password', ''),
(4, -1, 'mitroo_reprvat', '');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `tablesettings`
--

CREATE TABLE `tablesettings` (
  `id` bigint(20) NOT NULL,
  `tableKey` varchar(255) DEFAULT NULL,
  `tableValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `users`
--

INSERT INTO `users` (`id`, `active`, `name`, `password`, `username`) VALUES
(1, b'1', 'admin', '', 'admin');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Άδειασμα δεδομένων του πίνακα `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Ευρετήρια για πίνακα `doy`
--
ALTER TABLE `doy`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `role_permission`
--
ALTER TABLE `role_permission`
  ADD KEY `FK2xn8qv4vw30i04xdxrpvn3bdi` (`permission_id`),
  ADD KEY `FKtfgq8q9blrp0pt1pvggyli3v9` (`role_id`);

--
-- Ευρετήρια για πίνακα `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `tablesettings`
--
ALTER TABLE `tablesettings`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKt7e7djp752sqn6w22i6ocqy6q` (`role_id`),
  ADD KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `countries`
--
ALTER TABLE `countries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT για πίνακα `doy`
--
ALTER TABLE `doy`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=120;

--
-- AUTO_INCREMENT για πίνακα `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT για πίνακα `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT για πίνακα `settings`
--
ALTER TABLE `settings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `tablesettings`
--
ALTER TABLE `tablesettings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `FK2xn8qv4vw30i04xdxrpvn3bdi` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  ADD CONSTRAINT `FKtfgq8q9blrp0pt1pvggyli3v9` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Περιορισμοί για πίνακα `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKt7e7djp752sqn6w22i6ocqy6q` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;