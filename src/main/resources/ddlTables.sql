CREATE TABLE `User` (
  `Nome` varchar(25) NOT NULL,
  `Cognome` varchar(25) NOT NULL,
  PRIMARY KEY (`Cognome`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `NomeCognome` (
  `Item` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;