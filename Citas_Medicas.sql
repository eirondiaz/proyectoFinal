/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.11-MariaDB : Database - citas_medicas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`citas_medicas` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `citas_medicas`;

/*Table structure for table `centro_medico` */

DROP TABLE IF EXISTS `centro_medico`;

CREATE TABLE `centro_medico` (
  `IdCentro_Medico` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(60) NOT NULL,
  PRIMARY KEY (`IdCentro_Medico`),
  UNIQUE KEY `IdxNombre_Centro_Medico` (`Nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `centro_medico` */

/*Table structure for table `centro_medico_medico` */

DROP TABLE IF EXISTS `centro_medico_medico`;

CREATE TABLE `centro_medico_medico` (
  `IdCentro_Medico_Medico` int(11) NOT NULL AUTO_INCREMENT,
  `Hora_Entrada` time NOT NULL,
  `Hora_Salida` time NOT NULL,
  `IdMedico` int(11) NOT NULL,
  `IdCentro_Medico` int(11) NOT NULL,
  PRIMARY KEY (`IdCentro_Medico_Medico`),
  KEY `FK_CENTROMEDICOMEDICO_CENTROMEDICO` (`IdCentro_Medico`),
  KEY `FK_CENTROMEDICOMEDICO_MEDICO` (`IdMedico`),
  CONSTRAINT `FK_CENTROMEDICOMEDICO_CENTROMEDICO` FOREIGN KEY (`IdCentro_Medico`) REFERENCES `centro_medico` (`IdCentro_Medico`) ON DELETE CASCADE,
  CONSTRAINT `FK_CENTROMEDICOMEDICO_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `centro_medico_medico` */

/*Table structure for table `cita` */

DROP TABLE IF EXISTS `cita`;

CREATE TABLE `cita` (
  `IdCita` int(11) NOT NULL AUTO_INCREMENT,
  `Tipo_cita` varchar(30) NOT NULL,
  `Hora` time NOT NULL,
  `Observaciones` text NOT NULL,
  `Fecha` date NOT NULL,
  `duracion_maxima` varchar(20) NOT NULL,
  `duracion_mimima` varchar(20) NOT NULL,
  `IdCita_Status` int(11) NOT NULL,
  `IdMedico` int(11) NOT NULL,
  `IdPaciente` int(11) NOT NULL,
  PRIMARY KEY (`IdCita`),
  KEY `FK_CITA_STATUS` (`IdCita_Status`),
  KEY `FK_CITA_MEDICO` (`IdMedico`),
  KEY `FK_CITA_PACIENTE` (`IdPaciente`),
  CONSTRAINT `FK_CITA_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE,
  CONSTRAINT `FK_CITA_PACIENTE` FOREIGN KEY (`IdPaciente`) REFERENCES `pacientes` (`IdPacientes`) ON DELETE CASCADE,
  CONSTRAINT `FK_CITA_STATUS` FOREIGN KEY (`IdCita_Status`) REFERENCES `cita_estatus` (`IdCita_Status`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cita` */

/*Table structure for table `cita_estatus` */

DROP TABLE IF EXISTS `cita_estatus`;

CREATE TABLE `cita_estatus` (
  `IdCita_Status` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`IdCita_Status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cita_estatus` */

/*Table structure for table `medico` */

DROP TABLE IF EXISTS `medico`;

CREATE TABLE `medico` (
  `IdMedico` int(11) NOT NULL AUTO_INCREMENT,
  `Nombres` varchar(45) NOT NULL,
  `Apellidos` varchar(45) NOT NULL,
  `Telefono` int(10) NOT NULL,
  `Sexo` varchar(1) DEFAULT NULL CHECK (`Sexo` = ('M' or 'F')),
  `Fecha_Nacimiento` date NOT NULL,
  `Fotografia` mediumblob DEFAULT NULL,
  `Exequatur` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico`),
  UNIQUE KEY `IdxTelefono_Medico` (`Telefono`),
  KEY `IdxNombre_Medico` (`Nombres`),
  KEY `IdxApellidos_Medico` (`Apellidos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico` */

/*Table structure for table `medico_descripcion` */

DROP TABLE IF EXISTS `medico_descripcion`;

CREATE TABLE `medico_descripcion` (
  `IdMedico_Descripcion` int(11) NOT NULL AUTO_INCREMENT,
  `Comentario` text NOT NULL,
  `IdMedico` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico_Descripcion`),
  KEY `FK_MEDICODESCRIPCION_MEDICO` (`IdMedico`),
  CONSTRAINT `FK_MEDICODESCRIPCION_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_descripcion` */

/*Table structure for table `medico_dias_laborales` */

DROP TABLE IF EXISTS `medico_dias_laborales`;

CREATE TABLE `medico_dias_laborales` (
  `IdMedico_Dias_laborales` int(11) NOT NULL AUTO_INCREMENT,
  `lunes` tinyint(1) NOT NULL,
  `martes` tinyint(1) NOT NULL,
  `miercoles` tinyint(1) NOT NULL,
  `jueves` tinyint(1) NOT NULL,
  `viernes` tinyint(1) NOT NULL,
  `sabado` tinyint(1) NOT NULL,
  `domingo` tinyint(1) NOT NULL,
  `IdMedico` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico_Dias_laborales`),
  KEY `FK_MEDICODIASLABORALES_MEDICO` (`IdMedico`),
  CONSTRAINT `FK_MEDICODIASLABORALES_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_dias_laborales` */

/*Table structure for table `medico_especialidad` */

DROP TABLE IF EXISTS `medico_especialidad`;

CREATE TABLE `medico_especialidad` (
  `IdMedico_Especialidad` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(60) NOT NULL,
  PRIMARY KEY (`IdMedico_Especialidad`),
  KEY `Idxnombre_Medico_Especialidad` (`IdMedico_Especialidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_especialidad` */

/*Table structure for table `medico_especialidad_medico` */

DROP TABLE IF EXISTS `medico_especialidad_medico`;

CREATE TABLE `medico_especialidad_medico` (
  `IdMedico_Especialidad_Medico` int(11) NOT NULL AUTO_INCREMENT,
  `IdMedico` int(11) NOT NULL,
  `IdMedico_Especialidad` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico_Especialidad_Medico`),
  KEY `FK_MEDICOESPECIALIDADMEDICO_MEDICO` (`IdMedico`),
  KEY `FK_MEDICOESPECIALIDADMEDICO_ESPECIALIDAD` (`IdMedico_Especialidad`),
  CONSTRAINT `FK_MEDICOESPECIALIDADMEDICO_ESPECIALIDAD` FOREIGN KEY (`IdMedico_Especialidad`) REFERENCES `medico_especialidad` (`IdMedico_Especialidad`) ON DELETE CASCADE,
  CONSTRAINT `FK_MEDICOESPECIALIDADMEDICO_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_especialidad_medico` */

/*Table structure for table `medico_seguro` */

DROP TABLE IF EXISTS `medico_seguro`;

CREATE TABLE `medico_seguro` (
  `IdMedico_Seguro` int(11) NOT NULL AUTO_INCREMENT,
  `IdMedico` int(11) NOT NULL,
  `IdSeguro` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico_Seguro`),
  KEY `FK_MEDICOSEGURO_MEDICO` (`IdMedico`),
  KEY `FK_MEDICOSEGURO_SEGURO` (`IdSeguro`),
  CONSTRAINT `FK_MEDICOSEGURO_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE,
  CONSTRAINT `FK_MEDICOSEGURO_SEGURO` FOREIGN KEY (`IdSeguro`) REFERENCES `seguro` (`IdSeguro`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_seguro` */

/*Table structure for table `medico_vacaciones` */

DROP TABLE IF EXISTS `medico_vacaciones`;

CREATE TABLE `medico_vacaciones` (
  `IdMedico_vacaciones` int(11) NOT NULL AUTO_INCREMENT,
  `Fecha_Inicio` date NOT NULL,
  `Fecha_final` date NOT NULL,
  `IdMedico` int(11) NOT NULL,
  PRIMARY KEY (`IdMedico_vacaciones`),
  KEY `FK_MEDICOVACACIONES_MEDICO` (`IdMedico`),
  CONSTRAINT `FK_MEDICOVACACIONES_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `medico_vacaciones` */

/*Table structure for table `pacientes` */

DROP TABLE IF EXISTS `pacientes`;

CREATE TABLE `pacientes` (
  `IdPacientes` int(11) NOT NULL AUTO_INCREMENT,
  `Nombres` varchar(45) NOT NULL,
  `Apellidos` varchar(45) NOT NULL,
  `Telefono` int(10) NOT NULL,
  `Sexo` varchar(1) DEFAULT NULL CHECK (`Sexo` = ('M' or 'F')),
  `Fecha_Nacimiento` date NOT NULL,
  `Fotografia` mediumblob DEFAULT NULL,
  PRIMARY KEY (`IdPacientes`),
  UNIQUE KEY `IdxTelefono_Pacientes` (`Telefono`),
  KEY `IdxNombre_Pacientes` (`Nombres`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `pacientes` */

/*Table structure for table `seguro` */

DROP TABLE IF EXISTS `seguro`;

CREATE TABLE `seguro` (
  `IdSeguro` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`IdSeguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `seguro` */

/*Table structure for table `user_medico` */

DROP TABLE IF EXISTS `user_medico`;

CREATE TABLE `user_medico` (
  `Correo` varchar(60) NOT NULL,
  `Contraseña` varchar(60) NOT NULL,
  `IdMedico` int(11) NOT NULL,
  PRIMARY KEY (`Correo`),
  KEY `FK_USERMEDICO_MEDICO` (`IdMedico`),
  CONSTRAINT `FK_USERMEDICO_MEDICO` FOREIGN KEY (`IdMedico`) REFERENCES `medico` (`IdMedico`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_medico` */

/*Table structure for table `user_paciente` */

DROP TABLE IF EXISTS `user_paciente`;

CREATE TABLE `user_paciente` (
  `Correo` varchar(60) NOT NULL,
  `Contraseña` varchar(60) NOT NULL,
  `IdPacientes` int(11) NOT NULL,
  PRIMARY KEY (`Correo`),
  KEY `FK_USERPACIENTE_PACIENTE` (`IdPacientes`),
  CONSTRAINT `FK_USERPACIENTE_PACIENTE` FOREIGN KEY (`IdPacientes`) REFERENCES `pacientes` (`IdPacientes`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_paciente` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
