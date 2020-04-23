-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2020 at 08:17 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e_attendance`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendances`
--

CREATE TABLE `attendances` (
  `_id` bigint(20) UNSIGNED NOT NULL,
  `attend` varchar(128) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `createdAt` datetime NOT NULL,
  `employeeCode` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendances`
--

INSERT INTO `attendances` (`_id`, `attend`, `date`, `createdAt`, `employeeCode`) VALUES
(26, 'CheckIn', '2020-04-22', '2020-04-22 14:25:58', 'e123'),
(27, 'CheckOut', '2020-04-22', '2020-04-22 14:27:37', 'e123'),
(28, 'CheckIn', '2020-04-22', '2020-04-22 14:27:42', 'e123'),
(29, 'CheckOut', '2020-04-22', '2020-04-22 14:27:44', 'e123'),
(30, 'CheckIn', '2020-04-22', '2020-04-22 14:33:42', 'e123'),
(31, 'CheckIn', '2020-04-22', '2020-04-22 14:33:45', '1234'),
(32, 'CheckOut', '2020-04-22', '2020-04-22 14:33:53', '1234'),
(33, 'CheckOut', '2020-04-22', '2020-04-22 14:33:59', 'e123'),
(34, 'CheckIn', '2020-04-22', '2020-04-22 14:34:03', 'e123'),
(35, 'CheckIn', '2020-04-22', '2020-04-22 14:34:06', '1234'),
(36, 'CheckOut', '2020-04-22', '2020-04-22 14:34:14', 'e123'),
(37, 'CheckOut', '2020-04-22', '2020-04-22 14:34:19', '1234'),
(38, 'CheckIn', '2020-04-22', '2020-04-22 15:07:30', 'e123'),
(39, 'CheckOut', '2020-04-22', '2020-04-22 15:07:38', 'e123'),
(40, 'CheckIn', '2020-04-22', '2020-04-22 15:07:40', 'ert'),
(41, 'CheckIn', '2020-04-22', '2020-04-22 15:07:47', 'ert'),
(42, 'CheckIn', '2020-04-22', '2020-04-22 15:07:49', 'ert'),
(43, 'CheckIn', '2020-04-22', '2020-04-22 15:07:58', '1234'),
(44, 'CheckOut', '2020-04-22', '2020-04-22 15:08:00', '1234'),
(45, 'CheckOut', '2020-04-22', '2020-04-22 15:08:06', 'ert'),
(46, 'CheckIn', '2020-04-22', '2020-04-22 15:08:10', 'e123'),
(47, 'CheckIn', '2020-04-22', '2020-04-22 15:08:14', 'ert'),
(48, 'CheckIn', '2020-04-22', '2020-04-22 15:08:16', '1234'),
(49, 'CheckOut', '2020-04-22', '2020-04-22 15:08:18', 'ert'),
(50, 'CheckOut', '2020-04-22', '2020-04-22 15:08:20', '1234'),
(51, 'CheckOut', '2020-04-22', '2020-04-22 15:08:23', 'e123'),
(52, 'CheckIn', '2020-04-22', '2020-04-22 15:08:46', 'ert'),
(53, 'CheckOut', '2020-04-22', '2020-04-22 15:19:13', 'ert'),
(54, 'CheckIn', '2020-04-22', '2020-04-22 15:19:21', 'ert'),
(55, 'CheckOut', '2020-04-22', '2020-04-22 15:19:29', 'ert'),
(56, 'CheckIn', '2020-04-22', '2020-04-22 15:19:32', '1234'),
(57, 'CheckIn', '2020-04-22', '2020-04-22 15:19:46', 'ert'),
(58, 'CheckOut', '2020-04-22', '2020-04-22 15:19:50', 'ert'),
(59, 'CheckIn', '2020-04-22', '2020-04-22 15:19:55', 'ert'),
(60, 'CheckIn', '2020-04-22', '2020-04-22 15:19:57', 'e123'),
(61, 'CheckOut', '2020-04-22', '2020-04-22 15:20:00', '1234'),
(62, 'CheckOut', '2020-04-22', '2020-04-22 15:20:01', 'ert'),
(63, 'CheckOut', '2020-04-22', '2020-04-22 15:20:03', 'e123');

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `_id` bigint(20) UNSIGNED NOT NULL,
  `companyName` varchar(64) DEFAULT NULL,
  `username` varchar(128) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`_id`, `companyName`, `username`, `password`) VALUES
(1, 'shine', 'sahilthakar', '$2a$10$PmUPIJLgItXdUI4d0iQj.en.xmrn21vKxWKWPoJAbsznD8RmEbHb2'),
(2, 'SyncByte', 'sahilthakar10', '$2a$10$Elm.a9KvDIkpvjxFrMccceV.foiC9ajoel8pcABxveYUSw/rGAk5.'),
(3, 'io', 'sahil', '$2a$10$6RPp1638f.wxgRTJdeO2SOv4dW/9rnjrduWEQvWbCz958XNRjsE1m');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `_id` bigint(20) UNSIGNED NOT NULL,
  `fullName` varchar(64) DEFAULT NULL,
  `mobile` bigint(10) UNSIGNED NOT NULL,
  `DOB` date DEFAULT NULL,
  `employeeCode` varchar(128) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fingerPrint` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`_id`, `fullName`, `mobile`, `DOB`, `employeeCode`, `password`, `fingerPrint`) VALUES
(14, 'Thakar Sahil M.', 79326, '1999-10-05', 'e123', '$2a$10$Xb.MYZEeWjW1Xckk3VyZ3O0GcbELNis8bqiN3qBozkOEhMOCz.Ul.', 1),
(15, 'Sahil', 6532, '1999-10-10', '1234', '$2a$10$KEUyCNUyZTkllGeFdd6DAuIFn6g0GuJ.ydpL9IX8LlcnWrOgLCgxm', 1),
(16, 'Sahil(2nd', 7936363, '2019-12-12', 'ert', '$2a$10$y6MD/pBBQtdMBpGJbUBcFeIRSuOdf7BWGtrRqe83tkbVt5XTN95WK', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendances`
--
ALTER TABLE `attendances`
  ADD PRIMARY KEY (`_id`),
  ADD KEY `employeeCode` (`employeeCode`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `username_2` (`username`),
  ADD UNIQUE KEY `username_3` (`username`),
  ADD UNIQUE KEY `username_4` (`username`),
  ADD UNIQUE KEY `username_5` (`username`),
  ADD UNIQUE KEY `username_6` (`username`),
  ADD UNIQUE KEY `username_7` (`username`),
  ADD UNIQUE KEY `username_8` (`username`),
  ADD UNIQUE KEY `username_9` (`username`),
  ADD UNIQUE KEY `username_10` (`username`),
  ADD UNIQUE KEY `username_11` (`username`),
  ADD UNIQUE KEY `username_12` (`username`),
  ADD UNIQUE KEY `username_13` (`username`),
  ADD UNIQUE KEY `username_14` (`username`),
  ADD UNIQUE KEY `username_15` (`username`),
  ADD UNIQUE KEY `username_16` (`username`),
  ADD UNIQUE KEY `username_17` (`username`),
  ADD UNIQUE KEY `username_18` (`username`),
  ADD UNIQUE KEY `username_19` (`username`),
  ADD UNIQUE KEY `username_20` (`username`),
  ADD UNIQUE KEY `username_21` (`username`),
  ADD UNIQUE KEY `username_22` (`username`),
  ADD UNIQUE KEY `username_23` (`username`),
  ADD UNIQUE KEY `username_24` (`username`),
  ADD UNIQUE KEY `username_25` (`username`),
  ADD UNIQUE KEY `username_26` (`username`),
  ADD UNIQUE KEY `username_27` (`username`),
  ADD UNIQUE KEY `username_28` (`username`),
  ADD UNIQUE KEY `username_29` (`username`),
  ADD UNIQUE KEY `username_30` (`username`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `mobile` (`mobile`),
  ADD UNIQUE KEY `mobile_2` (`mobile`),
  ADD UNIQUE KEY `mobile_3` (`mobile`),
  ADD UNIQUE KEY `mobile_4` (`mobile`),
  ADD UNIQUE KEY `mobile_5` (`mobile`),
  ADD UNIQUE KEY `mobile_6` (`mobile`),
  ADD UNIQUE KEY `mobile_7` (`mobile`),
  ADD UNIQUE KEY `mobile_8` (`mobile`),
  ADD UNIQUE KEY `mobile_9` (`mobile`),
  ADD UNIQUE KEY `mobile_10` (`mobile`),
  ADD UNIQUE KEY `mobile_11` (`mobile`),
  ADD UNIQUE KEY `mobile_12` (`mobile`),
  ADD UNIQUE KEY `mobile_13` (`mobile`),
  ADD UNIQUE KEY `mobile_14` (`mobile`),
  ADD UNIQUE KEY `mobile_15` (`mobile`),
  ADD UNIQUE KEY `mobile_16` (`mobile`),
  ADD UNIQUE KEY `mobile_17` (`mobile`),
  ADD UNIQUE KEY `mobile_18` (`mobile`),
  ADD UNIQUE KEY `mobile_19` (`mobile`),
  ADD UNIQUE KEY `mobile_20` (`mobile`),
  ADD UNIQUE KEY `mobile_21` (`mobile`),
  ADD UNIQUE KEY `mobile_22` (`mobile`),
  ADD UNIQUE KEY `mobile_23` (`mobile`),
  ADD UNIQUE KEY `mobile_24` (`mobile`),
  ADD UNIQUE KEY `mobile_25` (`mobile`),
  ADD UNIQUE KEY `mobile_26` (`mobile`),
  ADD UNIQUE KEY `mobile_27` (`mobile`),
  ADD UNIQUE KEY `mobile_28` (`mobile`),
  ADD UNIQUE KEY `mobile_29` (`mobile`),
  ADD UNIQUE KEY `mobile_30` (`mobile`),
  ADD UNIQUE KEY `mobile_31` (`mobile`),
  ADD UNIQUE KEY `mobile_32` (`mobile`),
  ADD UNIQUE KEY `employeeCode` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_2` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_3` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_4` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_5` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_6` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_7` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_8` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_9` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_10` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_11` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_12` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_13` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_14` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_15` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_16` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_17` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_18` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_19` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_20` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_21` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_22` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_23` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_24` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_25` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_26` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_27` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_28` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_29` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_30` (`employeeCode`),
  ADD UNIQUE KEY `employeeCode_31` (`employeeCode`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendances`
--
ALTER TABLE `attendances`
  MODIFY `_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendances`
--
ALTER TABLE `attendances`
  ADD CONSTRAINT `attendances_ibfk_1` FOREIGN KEY (`employeeCode`) REFERENCES `employees` (`employeeCode`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
