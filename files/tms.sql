/*
SQLyog Community Edition- MySQL GUI v8.2 
MySQL - 5.0.41-community-nt : Database - timesheet_db
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`timesheet_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `timesheet_db`;

/*Table structure for table `accessgroups` */

DROP TABLE IF EXISTS `accessgroups`;

CREATE TABLE `accessgroups` (
  `no` int(3) NOT NULL,
  `groupid` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY  (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `accessgroups` */

insert  into `accessgroups`(`no`,`groupid`,`title`) values (1,'Widget_ADMIN','Administration'),(5,'Widget_ALERT','Alerts'),(8,'Widget_FORUM','Discussion Forum'),(4,'Widget_LEAVE','Leave & Attendance'),(7,'Widget_NOTICE','Noticeboard'),(3,'Widget_PAYMENT','Payment'),(6,'Widget_PERANT','Parent Portal'),(2,'Widget_QUICKLINKS','School Administration'),(2,'Widget_SCHOOL','School Administration');

/*Table structure for table `actions` */

DROP TABLE IF EXISTS `actions`;

CREATE TABLE `actions` (
  `actionid` int(5) NOT NULL default '0',
  `actiontype` varchar(256) NOT NULL default '',
  PRIMARY KEY  (`actionid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `actions` */

insert  into `actions`(`actionid`,`actiontype`) values (101,'User %s logged in successfully.');

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `activityid` varchar(40) NOT NULL,
  `activityname` varchar(100) default NULL,
  `activitycode` varchar(40) default NULL,
  `description` varchar(1000) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `deleted` smallint(1) default NULL,
  PRIMARY KEY  (`activityid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `activity` */

insert  into `activity`(`activityid`,`activityname`,`activitycode`,`description`,`createdby`,`createdon`,`modifiedby`,`modifiedon`,`deleted`) values ('1111','Requirement Analysis','REQ',NULL,NULL,NULL,NULL,'2014-10-17 09:01:12',NULL),('2222','Devlopment','A',NULL,NULL,NULL,NULL,'2014-10-17 09:01:21',NULL),('3333','Mantanance','NNT',NULL,NULL,NULL,NULL,'2014-10-17 09:01:25',NULL),('4444','Implementatin','IMPL',NULL,NULL,NULL,NULL,'2014-10-17 09:01:37',NULL),('5555','xyz','B',NULL,NULL,NULL,NULL,'2014-10-17 09:01:41',NULL),('6666','fdgfgg','C',NULL,NULL,NULL,NULL,'2014-10-17 09:08:22',NULL),('7777','dgd','D',NULL,NULL,NULL,NULL,'2014-10-17 09:08:25',NULL),('8888','sfsss','E',NULL,NULL,NULL,NULL,'2014-10-17 09:08:31',NULL),('9999','deter','F',NULL,NULL,NULL,NULL,'2014-10-17 09:08:39',NULL);

/*Table structure for table `audittrail` */

DROP TABLE IF EXISTS `audittrail`;

CREATE TABLE `audittrail` (
  `actionid` int(5) NOT NULL,
  `params` varchar(100) default NULL,
  `ipaddr` varchar(36) NOT NULL default '',
  `userid` varchar(36) NOT NULL default '',
  `ts` bigint(20) default NULL,
  KEY `audittrail_ibfk_1` (`actionid`),
  CONSTRAINT `audittrail_ibfk_1` FOREIGN KEY (`actionid`) REFERENCES `actions` (`actionid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `audittrail` */

insert  into `audittrail`(`actionid`,`params`,`ipaddr`,`userid`,`ts`) values (101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391524924046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391526225468),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391526301015),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391526318203),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391526765015),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391526974546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391527037796),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391527350828),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391528647937),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391532473171),(101,'Aaron Walls','127.0.0.1','c30e81a4-4cff-4e55-85bb-d286da716520',1391532561156),(101,'William Bass','127.0.0.1','8e779731-52e4-4a05-86c3-58ce6049a88f',1391532581015),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391532595687),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391534438531),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391535270562),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391535684500),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391535828953),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391535872046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391535923125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391536054171),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391536439312),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391536560359),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391536624703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391536673578),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391616212453),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391622949593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391622982093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391623601781),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391624678375),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391624697687),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391625257843),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391701209203),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391704680875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391704714281),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391704993312),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391705340703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391705432687),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391705541828),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391705868421),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391705896531),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391709070281),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391709423406),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391709807656),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391709865187),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711101828),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711453234),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711467781),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711565078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711603218),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711643578),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711766640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711826703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391711896015),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391715095890),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391715130156),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391715412078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391715715750),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391715745765),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391716286343),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391716326296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391717011375),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391717027390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391717354093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391717378390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391790124828),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391790584453),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391790643281),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391790850437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391793561500),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391793584875),(101,'William Bass','127.0.0.1','8e779731-52e4-4a05-86c3-58ce6049a88f',1391793651515),(101,'William Bass','127.0.0.1','8e779731-52e4-4a05-86c3-58ce6049a88f',1391793662890),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391793880281),(101,'William Bass','127.0.0.1','8e779731-52e4-4a05-86c3-58ce6049a88f',1391793900171),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391794573968),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391796380140),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391796395671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391797305765),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391797616640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391799619812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391830998718),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391831033062),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391831285812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391831337593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391913841328),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391914386812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391914497734),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391914584812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391915564000),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391915580375),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391916329218),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391916746328),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391917079812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391917113015),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391917327125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391918064906),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391918190437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391918630562),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919069531),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919195140),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919226296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919294468),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919326078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919471390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391919565296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921242953),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921259203),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921476781),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921733359),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921802593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391921958406),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391922079140),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391922213125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391922397609),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391922591875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391922931390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391923029890),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391923136093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391923197406),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391923323109),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391923376843),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391924597484),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391924630734),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391925092125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391925118546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391925926093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926020515),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926352437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926405406),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926540156),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926700562),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391926793703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391927777062),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391928047765),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391931842734),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391932759562),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391932776984),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391933226546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391934214593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391934253921),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391934274500),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391934992703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935022390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935755875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935783046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935809890),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935865953),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935886875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935907625),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935918812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935943750),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935967515),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391935988046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936013484),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936041984),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936063218),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936078781),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936126593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936149312),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391936167265),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391937142078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391937161640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391937190359),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391937206984),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391937229437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391938057484),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391938085046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939219312),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939248000),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939439234),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939647593),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939804062),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939887875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391939929187),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391940121640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391940320421),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391940417437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1391940530156),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392048056656),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392049969093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392049996234),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392050879968),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392050899546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392050919671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392051505093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392051520296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392051831296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392051847875),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392053433718),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392053474984),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392054254277),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392054272121),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392054389074),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392138786328),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392138818687),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392138961546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392138983437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139085437),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139169375),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139333234),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139538953),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139563125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392139896265),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140026640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140184562),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140296000),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140480078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140648546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392140725671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392141260281),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392141439453),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392142496203),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392142518093),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392142945921),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392142973718),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392143322156),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392143357640),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392143649703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392144443468),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392144454281),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145143343),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145234296),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145283968),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145702984),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145720046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145806578),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145881531),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392145925140),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392221256671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392221286390),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392221520265),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224529171),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224744859),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224783656),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224840265),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224923156),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392224969046),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225032140),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225196125),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225261218),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225312625),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225444546),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225628578),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225705328),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392225747968),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392226701625),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392307457796),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392398787187),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392399799500),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392399814765),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392400305703),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392403259421),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392403296812),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392433343218),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392433361609),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392444914921),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392444935625),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392445943671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392446085171),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392446192625),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392446336671),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392446351343),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1392446761109),(101,'Garriso\n;\n;\n\n\n Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1410533068078),(101,'Garrison Mcknight','127.0.0.1','35d3be72-77e4-4128-ab35-02baa8ba2a4d',1410533363953);

/*Table structure for table `calendar` */

DROP TABLE IF EXISTS `calendar`;

CREATE TABLE `calendar` (
  `calendar_id` int(2) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL,
  `description` varchar(250) default NULL,
  `color` varchar(10) default NULL,
  `hidden` bit(1) default NULL,
  PRIMARY KEY  (`calendar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `calendar` */

insert  into `calendar`(`calendar_id`,`title`,`description`,`color`,`hidden`) values (1,'Holiday','Holiday','','\0');

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `countryid` int(11) NOT NULL auto_increment,
  `NAME` varchar(40) NOT NULL,
  PRIMARY KEY  (`countryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `country` */

insert  into `country`(`countryid`,`NAME`) values (1,'India');

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `event_id` int(5) NOT NULL auto_increment,
  `calendar_id` int(3) NOT NULL,
  `title` varchar(100) default NULL,
  `start_date` bigint(20) default NULL,
  `end_date` bigint(20) default NULL,
  `location` varchar(100) default NULL,
  `notes` varchar(250) default NULL,
  `url` varchar(100) default NULL,
  `reminder` varchar(100) default NULL,
  `isAllDay` bit(1) default NULL,
  `isNew` bit(1) default NULL,
  UNIQUE KEY `event_id` (`event_id`),
  KEY `cal-events_1112` (`calendar_id`),
  CONSTRAINT `cal-events_1112` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`calendar_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `events` */

/*Table structure for table `forgotpasswordrequest` */

DROP TABLE IF EXISTS `forgotpasswordrequest`;

CREATE TABLE `forgotpasswordrequest` (
  `pid` varchar(40) NOT NULL,
  `userid` varchar(40) default NULL,
  `username` varchar(40) default NULL,
  `oldpassword` varchar(40) default NULL,
  `newpassword` varchar(40) default NULL,
  `createdon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `forgotpasswordrequest` */

insert  into `forgotpasswordrequest`(`pid`,`userid`,`username`,`oldpassword`,`newpassword`,`createdon`) values ('0b42c2b6-0522-4219-b173-51c58b365649','b223524a-cace-42e4-9404-6993efcde14b',NULL,'229a08024e06','e2190306cf59','2014-03-09 11:55:48'),('105cd8cb-0614-4a7e-ab4b-50efe27734ae','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'b6708420de5c','dfe375359f2a','2014-03-11 22:20:15'),('11ae4c35-bf53-41b3-b742-c12c24002db3','b223524a-cace-42e4-9404-6993efcde14b',NULL,'0e83b39412bd','f5ce2993c401','2014-03-09 20:20:22'),('1895e26e-6e3e-4ec4-9a5d-a0473fd98e47','b223524a-cace-42e4-9404-6993efcde14b',NULL,'d650b9b9af93','d5a41afa7f3f','2014-03-09 11:29:30'),('1c2d648b-ed4a-435d-8357-35750b5bdbda','b223524a-cace-42e4-9404-6993efcde14b',NULL,'29a44a6a0cf6','0e83b39412bd','2014-03-09 20:13:16'),('2359568c-0eab-4e1d-98e0-0cbd3ada9ae6','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'e675f5588ff2','72970a71295e','2014-03-11 22:40:42'),('2d6bece9-19da-4a8e-af24-bae74ad2f216','b223524a-cace-42e4-9404-6993efcde14b',NULL,'d5a41afa7f3f','229a08024e06','2014-03-09 11:39:00'),('308b501f-b224-4fa7-b31c-9a7481040721','b223524a-cace-42e4-9404-6993efcde14b',NULL,'21b66844afe2','d650b9b9af93','2014-03-09 11:27:43'),('3d031e90-b367-4a8e-abf4-771a69da7a1f','b223524a-cace-42e4-9404-6993efcde14b',NULL,'bc41924ee3e7','556992e17e29','2014-03-09 20:05:07'),('44a33a18-51cd-4792-a533-89c2e81846d8','b223524a-cace-42e4-9404-6993efcde14b',NULL,'c6d6a72c01ab','823c0622614a','2014-03-09 20:24:14'),('46e13fa7-3c81-4f49-b440-4be3c841075d','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'0299ae70f5c1','b8f55a955db1','2014-03-11 22:46:59'),('4c4664ad-0343-46a9-8408-17b8f5207c33','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'50d2c2d9b1cd','0299ae70f5c1','2014-03-11 22:42:51'),('749a4052-f5a2-4596-a2da-04f9bc4c7786','b223524a-cace-42e4-9404-6993efcde14b',NULL,'556992e17e29','e7a18084816e','2014-03-09 20:06:17'),('89ef999b-4279-4f4f-bde2-5ebe1572cdcd','b223524a-cace-42e4-9404-6993efcde14b',NULL,'2e8ed3a331ee','21b66844afe2','2014-03-09 11:03:27'),('95822155-5439-47f0-9f5d-6ace0c8dd054','b223524a-cace-42e4-9404-6993efcde14b',NULL,'6ecc42b8a127','99b61207e868','2014-03-09 21:06:58'),('99765ef7-03fc-41d7-a4c7-6997bdd0a30a','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'72970a71295e','50d2c2d9b1cd','2014-03-11 22:41:00'),('9e3eae35-678d-452c-b638-55a5608e444c','b223524a-cace-42e4-9404-6993efcde14b',NULL,'abc89f00972c','6ecc42b8a127','2014-03-09 20:32:12'),('a13ad3df-1443-409a-8d1a-1a1536dff4d2','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'d6853822f25d','da641ed30d1f','2014-03-11 22:11:17'),('a2bebf53-4750-4abc-bd42-b311df7eddf4','b223524a-cace-42e4-9404-6993efcde14b',NULL,'823c0622614a','6fae1b4a4f22','2014-03-09 20:24:24'),('a360522f-b83c-484c-8107-e83ca2c2af01','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'146fcc6c77bb','d6853822f25d','2014-03-11 22:07:37'),('a63daea1-dbab-4777-b9a4-01ef63ef9fbf','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'da641ed30d1f','b6708420de5c','2014-03-11 22:14:41'),('a7dbecea-eee8-4ff1-9b32-f98d20528c0a','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'admin','f60fdac6d270','2014-03-10 23:14:24'),('a8dd66f8-c05d-47d7-b5ae-3ed42bdb2652','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'f6388392ef0d','146fcc6c77bb','2014-03-11 22:04:32'),('ac2ae77d-1a50-4b29-a936-c110d2e00d86','b223524a-cace-42e4-9404-6993efcde14b',NULL,'e7a18084816e','29a44a6a0cf6','2014-03-09 20:08:38'),('b999676e-3649-4796-93c7-269130047244','b223524a-cace-42e4-9404-6993efcde14b',NULL,'85cbce4267d7','c6d6a72c01ab','2014-03-09 20:23:24'),('d7152648-9f25-4d3b-abd2-6e8bdcf64e07','b223524a-cace-42e4-9404-6993efcde14b',NULL,'68d12652c6e9','3cd5d8f0b648','2014-03-10 22:07:05'),('d88086e3-ddc5-4c35-a442-b20549feeba8','b223524a-cace-42e4-9404-6993efcde14b',NULL,'159d4aab7a36','a83e16e67b58','2014-03-10 21:54:03'),('d8a3b428-5a99-4541-b589-472525cfe3fe','b223524a-cace-42e4-9404-6993efcde14b',NULL,'a63f488d79d6','159d4aab7a36','2014-03-10 21:07:08'),('daad8415-f84d-4947-9e97-5f82d52ebc15','b223524a-cace-42e4-9404-6993efcde14b',NULL,'a83e16e67b58','68d12652c6e9','2014-03-10 22:01:23'),('e110c03c-5fec-4095-b995-9f53f9655636','b223524a-cace-42e4-9404-6993efcde14b',NULL,'56be977cc0f1','85cbce4267d7','2014-03-09 20:22:30'),('e5798980-e1e6-4f2d-9140-e616203cd331','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'admin','f6388392ef0d','2014-03-10 23:14:24'),('e8b138d2-33df-4d6e-8c67-6b2b124e504e','b223524a-cace-42e4-9404-6993efcde14b',NULL,'e2190306cf59','bc41924ee3e7','2014-03-09 11:56:36'),('e99c3b6d-5338-4b2e-b4b1-ed33b2f81ad6','b223524a-cace-42e4-9404-6993efcde14b',NULL,'99b61207e868','a63f488d79d6','2014-03-09 21:07:19'),('ec2a58d6-800b-4d3f-9312-ae02c44a1798','b223524a-cace-42e4-9404-6993efcde14b',NULL,'f5ce2993c401','56be977cc0f1','2014-03-09 20:21:15'),('f1d611c2-4375-4ff6-a57f-12c8ade90418','35d3be72-77e4-4128-ab35-02baa8ba2a4d',NULL,'dfe375359f2a','e675f5588ff2','2014-03-11 22:25:11'),('f60ad221-d91e-4da2-b8e4-865e5f0f688c','b223524a-cace-42e4-9404-6993efcde14b',NULL,'6fae1b4a4f22','040fdd238943','2014-03-09 20:26:29'),('f7c94451-ac32-4f90-8637-3989e93af923','b223524a-cace-42e4-9404-6993efcde14b',NULL,'040fdd238943','abc89f00972c','2014-03-09 20:29:31');

/*Table structure for table `mail_template` */

DROP TABLE IF EXISTS `mail_template`;

CREATE TABLE `mail_template` (
  `template_id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `subject` varchar(512) default NULL,
  `template` varchar(1024) default NULL,
  `comment` varchar(100) default NULL,
  `created_by` varchar(36) default NULL,
  `created_on` date default NULL,
  PRIMARY KEY  (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mail_template` */

insert  into `mail_template`(`template_id`,`name`,`subject`,`template`,`comment`,`created_by`,`created_on`) values (1,'User Creation','New USer Created','Hello New Users',NULL,NULL,NULL),(101,'SEND ADMISSION LINK TO PARENT','Online Addmision Form Link','Greeting <b>%s</b>,<br>Thank you for registering with us.<b>Welcome </b>to Online Student admission process , Please click below link to apply online & fill your student detail<br><b>%s</b>','This is for sending admision link to parent','ADMIN','2014-02-05'),(102,'SEND INTERVIEW/EXAMINATION DATE TO PARENT','Scheduled Intervew and Examination date','Greeting <b>%s</b>,<br>Thank you for registering with us.<b>Welcome </b>to Online admission Process. Please find below examination and interview date scheduled for your child <br> Examination Date : <b>%s</b><br> Interview Date : <b>%s</b> <br> <b>Thank You .<br>Administrator</b>','Send Interview and Examination date to parent','ADMIN','2014-02-07'),(103,'CONTACT ADMIN','Contact Admin','Greeting Admin,<br><b>%s</b> , Has trying to contact You.<br><b>Title :</b>%s<br><b>Message :</b>%s<br><br>Thanks.','Email Send to admin','ADMIN','2014-02-12'),(104,'FORGOT PASSWORD REQUEST','Change Password Request','Greeting %s,\n	Please use below one time password to login with your username and change password<br>\n	New Generated Password : %s<br><br><br>In Case , You are facing any issue please Contact School Admin for further communication.\n	<br>Thank You , <br><b>School Admin</b>','Email Send to admin','ADMIN','2014-03-09');

/*Table structure for table `misreport` */

DROP TABLE IF EXISTS `misreport`;

CREATE TABLE `misreport` (
  `id` int(11) NOT NULL auto_increment,
  `reporttype` varchar(40) default NULL,
  `name` varchar(100) default NULL,
  `description` varchar(300) default NULL,
  `status` smallint(6) default NULL,
  `totviews` smallint(6) default NULL,
  `totdownload` smallint(6) default NULL,
  `image` varchar(100) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `misreport` */

insert  into `misreport`(`id`,`reporttype`,`name`,`description`,`status`,`totviews`,`totdownload`,`image`,`createdby`,`createdon`,`modifiedby`,`modifiedon`) values (1,'85e8b7f9-01d6-467c-a76c-57985b361e6a','Yearly - Project Status Report','',1,0,0,'yearlyclassreport.jpg','SUPER ADMIN','2013-12-16 00:00:00','SUPER ADMIN','2013-12-16 22:13:06'),(2,'85e8b7f9-01d6-467c-a76c-57985b361e6a','Yearly - Timesheet Report','',1,0,0,'YearlyPaymentReport.jpg','SUPER ADMIN','2013-12-16 00:00:00','SUPER ADMIN','2013-12-16 22:13:06'),(21,'2a97c973-01a7-463b-aa2e-b3871e440a0c','Monthly - Project Status Report','',1,0,0,'MonthlyPaymentReport.jpg','SUPER ADMIN','2014-01-18 00:00:00','SUPER ADMIN','2014-01-18 13:25:09'),(22,'2a97c973-01a7-463b-aa2e-b3871e440a0c','monthly - Timesheet Report','',1,0,0,'MonthlyPaymentReport.jpg','SUPER ADMIN','2014-01-18 00:00:00','SUPER ADMIN','2014-01-18 14:09:36'),(23,'4548bec1-07a6-4ff7-ae45-e7b8c328b7eb','Weekly Timesheet Report','',1,0,0,'MonthlyPaymentReport.jpg','SUPER ADMIN','2014-01-18 00:00:00','SUPER ADMIN','2014-01-18 14:10:08'),(24,'17df2006-711d-44a8-9dff-9ae94923c90e','Daily Timesheet Report','',1,0,0,'MonthlyPaymentReport.jpg','SUPER ADMIN','2014-01-18 00:00:00','SUPER ADMIN','2014-01-18 14:11:13');

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(3) NOT NULL,
  `groupid` varchar(50) NOT NULL,
  `name` varchar(30) default NULL,
  `ts` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `image` varchar(40) default NULL,
  `tooltip` varchar(100) default NULL,
  `callback` varchar(100) NOT NULL,
  KEY `permission_ihgj_123` (`groupid`),
  CONSTRAINT `permission_ihgj_123` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `permissions` */

insert  into `permissions`(`id`,`groupid`,`name`,`ts`,`image`,`tooltip`,`callback`) values (1,'Widget_ADMIN','Settings','2013-06-08 00:53:57','setting_icon.png','','openSettings'),(4,'Widget_ADMIN','Manage Employee / Users','2013-06-08 00:53:58','leads-icon.png','','showUsers'),(5,'Widget_ADMIN','Audit Trails','2013-06-08 00:53:58','my-goals.png','','showAuditTrail'),(15,'Widget_ADMIN','Notice Board','2013-07-17 23:05:10','noticeboard.png','','showNotification'),(19,'Widget_ADMIN','Report Management','2013-12-16 21:49:43','report.png','','showReport'),(20,'Widget_ADMIN','Project Management','2014-09-10 12:34:25','report.png','','showProject'),(1,'Widget_QUICKLINKS','Timesheet Entry','2014-09-10 12:44:26','timetable.png','','showmytimesheet'),(2,'Widget_QUICKLINKS','Approval Center','2014-09-10 12:44:26','timetable.png','','approvalcenter');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `projectid` varchar(40) NOT NULL,
  `projectname` varchar(100) default NULL,
  `projectcode` varchar(40) default NULL,
  `description` varchar(1000) default NULL,
  `startdate` bigint(20) default NULL,
  `enddate` bigint(20) default NULL,
  `iscompleted` smallint(6) default '0',
  `clientname` varchar(100) default NULL,
  `projectpriority` varchar(40) default NULL,
  `projectstatus` varchar(40) default NULL,
  `projecttype` varchar(40) default NULL,
  `projectmanagerid` varchar(40) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `deleted` smallint(1) default '0',
  PRIMARY KEY  (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project` */

insert  into `project`(`projectid`,`projectname`,`projectcode`,`description`,`startdate`,`enddate`,`iscompleted`,`clientname`,`projectpriority`,`projectstatus`,`projecttype`,`projectmanagerid`,`createdby`,`createdon`,`modifiedby`,`modifiedon`,`deleted`) values ('1','Test','T','test project',1412101800000,1419964200000,0,NULL,NULL,NULL,NULL,NULL,NULL,'2014-10-11 23:55:46',NULL,'2014-10-11 23:55:46',0),('2','Time Managemen','TMS',NULL,1412101800000,1419964200000,0,NULL,NULL,NULL,NULL,NULL,NULL,'2014-10-11 23:55:46',NULL,'2014-10-17 09:19:26',0);

/*Table structure for table `projectactivity` */

DROP TABLE IF EXISTS `projectactivity`;

CREATE TABLE `projectactivity` (
  `id` varchar(40) NOT NULL,
  `projectid` varchar(40) NOT NULL,
  `activityid` varchar(40) NOT NULL,
  `description` varchar(1000) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `deleted` smallint(1) default '0',
  `deletedby` varchar(40) default NULL,
  `deletedon` datetime default NULL,
  PRIMARY KEY  (`id`,`projectid`,`activityid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `projectactivity` */

insert  into `projectactivity`(`id`,`projectid`,`activityid`,`description`,`createdby`,`createdon`,`modifiedby`,`modifiedon`,`deleted`,`deletedby`,`deletedon`) values ('cxvcx','1','1111',NULL,NULL,NULL,NULL,'2014-10-17 09:26:09',0,NULL,NULL),('cxvxc','1','2222',NULL,NULL,NULL,NULL,'2014-10-17 09:23:20',0,NULL,NULL),('erwe','2','1111',NULL,NULL,NULL,NULL,'2014-10-17 09:28:25',0,NULL,NULL),('iui','1','4444',NULL,NULL,NULL,NULL,'2014-10-17 09:28:34',0,NULL,NULL),('saas','1','3333',NULL,NULL,NULL,NULL,'2014-10-17 09:20:51',0,NULL,NULL);

/*Table structure for table `projectlocation` */

DROP TABLE IF EXISTS `projectlocation`;

CREATE TABLE `projectlocation` (
  `locationid` varchar(40) NOT NULL,
  `locationname` varchar(40) default NULL,
  `locationcode` varchar(20) default NULL,
  `description` varchar(1000) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `deleted` smallint(1) default '0',
  PRIMARY KEY  (`locationid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `projectlocation` */

/*Table structure for table `projectmember` */

DROP TABLE IF EXISTS `projectmember`;

CREATE TABLE `projectmember` (
  `id` varchar(40) NOT NULL,
  `projectid` varchar(40) NOT NULL,
  `userid` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL,
  `startdate` bigint(20) default NULL,
  `lastdate` bigint(20) default NULL,
  `createdby` varchar(40) default NULL,
  `createdon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `deleted` smallint(1) default '0',
  `deletedby` varchar(40) default NULL,
  `deletedon` datetime default NULL,
  PRIMARY KEY  (`id`,`projectid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `projectmember` */

insert  into `projectmember`(`id`,`projectid`,`userid`,`role`,`startdate`,`lastdate`,`createdby`,`createdon`,`modifiedby`,`modifiedon`,`deleted`,`deletedby`,`deletedon`) values ('','','','',NULL,0,NULL,NULL,NULL,'2014-10-17 09:37:53',0,NULL,NULL),('b','1','1111111111','',1413518823143,1413718823143,NULL,NULL,NULL,'2014-10-17 09:29:22',0,NULL,NULL),('saas','1','22222222222','',1413518823143,1413718823143,NULL,NULL,NULL,'2014-10-17 09:29:26',0,NULL,NULL),('sasa','1','3333333333','',1413518823143,1413718823143,NULL,NULL,NULL,'2014-10-17 09:29:28',0,NULL,NULL);

/*Table structure for table `properties` */

DROP TABLE IF EXISTS `properties`;

CREATE TABLE `properties` (
  `id` int(3) NOT NULL auto_increment,
  `value` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `properties` */

insert  into `properties`(`id`,`value`) values (1,'Designation'),(2,'Course'),(3,'PeriodNumber'),(4,'Religion'),(5,'Examination'),(6,'Session'),(7,'ExamAppearedStatus'),(9,'Location'),(10,'Vehicle Type'),(11,'Notification Recipient'),(12,'Notification Status'),(13,'Digital Dairy Priority'),(14,'Digital Dairy Request Type'),(15,'Student Progress Status'),(16,'Nationality'),(17,'AdmissionType'),(18,'Class'),(19,'Section'),(20,'Addmission Request Status'),(21,'Book Type'),(23,'Rating'),(24,'Teacher Type'),(25,'Teacher Job Type'),(27,'Department'),(28,'Education Level'),(29,'Qualification'),(30,'University'),(31,'Decipline'),(32,'Score Type'),(33,'School Affiliation'),(35,'MIS Report Type'),(36,'Hostel Type'),(37,'Room Type'),(38,'Floor'),(40,'Password Recovery Question'),(100,'Calendar'),(1001,'Project'),(1002,'Activity'),(1003,'Location');

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(2) NOT NULL auto_increment,
  `name` varchar(30) default NULL,
  `permvalue` int(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `roles` */

insert  into `roles`(`id`,`name`,`permvalue`) values (1,'Admin',1022),(2,'Manager',1022),(3,'Accountant',1022),(4,'Employee',1022);

/*Table structure for table `rolesgroupspermission` */

DROP TABLE IF EXISTS `rolesgroupspermission`;

CREATE TABLE `rolesgroupspermission` (
  `roleid` int(3) NOT NULL,
  `groupid` varchar(30) NOT NULL,
  `readonly` int(11) default NULL,
  `editable` int(11) default NULL,
  KEY `rolesgroupspermission_1` (`roleid`),
  KEY `rolesgroupspermission_2` (`groupid`),
  CONSTRAINT `rolesgroupspermission_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `rolesgroupspermission_2` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rolesgroupspermission` */

insert  into `rolesgroupspermission`(`roleid`,`groupid`,`readonly`,`editable`) values (1,'Widget_ADMIN',4194302,4194302),(1,'Widget_QUICKLINKS',64766,64766),(1,'Widget_PAYMENT',62,62),(1,'Widget_LEAVE',510,510),(1,'Widget_ALERT',2,2),(1,'Widget_PERANT',126,126),(1,'Widget_NOTICE',2,2),(1,'Widget_FORUM',2,2),(2,'Widget_ADMIN',2,2),(2,'Widget_QUICKLINKS',2,2),(2,'Widget_PAYMENT',2,2),(2,'Widget_LEAVE',2,2),(2,'Widget_ALERT',2,2),(2,'Widget_PERANT',2,2),(2,'Widget_NOTICE',2,2),(2,'Widget_FORUM',2,2),(3,'Widget_ADMIN',33090,33090),(3,'Widget_QUICKLINKS',25704,25704),(3,'Widget_PAYMENT',2,2),(3,'Widget_LEAVE',158,158),(3,'Widget_ALERT',2,2),(3,'Widget_PERANT',2,2),(3,'Widget_NOTICE',2,2),(3,'Widget_FORUM',2,2),(4,'Widget_ADMIN',32770,32770),(4,'Widget_QUICKLINKS',61678,61678),(4,'Widget_PAYMENT',2,2),(4,'Widget_LEAVE',2,2),(4,'Widget_ALERT',2,2),(4,'Widget_PERANT',66,66),(4,'Widget_NOTICE',2,2),(4,'Widget_FORUM',2,2);

/*Table structure for table `states` */

DROP TABLE IF EXISTS `states`;

CREATE TABLE `states` (
  `stateid` int(3) NOT NULL auto_increment,
  `NAME` varchar(40) default NULL,
  `countryid` int(3) default NULL,
  PRIMARY KEY  (`stateid`),
  KEY `state_ibfk_1` (`countryid`),
  CONSTRAINT `state_ibfk_1` FOREIGN KEY (`countryid`) REFERENCES `country` (`countryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `states` */

insert  into `states`(`stateid`,`NAME`,`countryid`) values (1,'Andhra Pradesh',1),(2,'Arunachal Pradesh',1),(3,'Assam',1),(4,'Bihar',1),(5,'Chhattisgarh',1),(6,'Goa',1),(7,'Gujarat',1),(8,'Haryana',1),(9,'Himachal Pradesh',1),(10,'Jammu and Kashmir',1),(11,'Jharkhand',1),(12,'Karnataka',1),(13,'Kerala',1),(14,'Madhya Pradesh',1),(15,'Maharashtra',1),(16,'Manipur',1),(17,'Meghalaya',1),(18,'Mizoram',1),(19,'Nagaland',1),(20,'Orissa',1),(21,'Punjab',1),(22,'Rajasthan',1),(23,'Sikkim',1),(24,'Tamil Nadu',1),(25,'Tripura',1),(26,'Uttar Pradesh',1),(27,'Uttarakhand',1),(28,'West Bengal',1);

/*Table structure for table `timesheet` */

DROP TABLE IF EXISTS `timesheet`;

CREATE TABLE `timesheet` (
  `id` varchar(40) NOT NULL,
  `locationid` varchar(40) NOT NULL,
  `fromdate` bigint(20) default NULL,
  `todate` bigint(20) default NULL,
  `status` smallint(6) default NULL,
  `userid` varchar(40) default NULL,
  `approvedby` varchar(40) default NULL,
  `approvedon` datetime default NULL,
  `createdon` datetime default NULL,
  `createdby` varchar(40) default NULL,
  `modifiedon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `modifiedby` varchar(40) default NULL,
  `comments` varchar(500) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `timesheet` */

insert  into `timesheet`(`id`,`locationid`,`fromdate`,`todate`,`status`,`userid`,`approvedby`,`approvedon`,`createdon`,`createdby`,`modifiedon`,`modifiedby`,`comments`) values ('aaaaaaaaaa','AUS',1413149193829,1413657000000,1,'1111111111',NULL,NULL,NULL,NULL,'2014-10-13 02:58:17',NULL,'');

/*Table structure for table `timesheetdetail` */

DROP TABLE IF EXISTS `timesheetdetail`;

CREATE TABLE `timesheetdetail` (
  `tsid` varchar(40) NOT NULL default '',
  `mon` decimal(10,0) default '0',
  `tues` decimal(10,0) default '0',
  `wed` decimal(10,0) default '0',
  `thurs` decimal(10,0) default '0',
  `fri` decimal(10,0) default '0',
  `sat` decimal(10,0) default '0',
  `sun` decimal(10,0) default '0',
  `description` varchar(500) default NULL,
  `projectId` varchar(40) default NULL,
  `activityId` varchar(40) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `timesheetdetail` */

insert  into `timesheetdetail`(`tsid`,`mon`,`tues`,`wed`,`thurs`,`fri`,`sat`,`sun`,`description`,`projectId`,`activityId`) values ('d671df26-ebdf-4c81-8a4b-59cc15a49926','0','4','0','0','0','0','0','gfg','1','1111'),('d671df26-ebdf-4c81-8a4b-59cc15a49926','0','0','0','5','0','0','0','retert','1','2222');

/*Table structure for table `timesheetlog` */

DROP TABLE IF EXISTS `timesheetlog`;

CREATE TABLE `timesheetlog` (
  `tsid` varchar(40) NOT NULL,
  `actionby` varchar(40) NOT NULL,
  `actionon` bigint(20) NOT NULL,
  `oldstatus` int(3) NOT NULL,
  `newstatus` int(3) NOT NULL,
  `comment` varchar(500) default '',
  `id` varchar(40) NOT NULL,
  `version` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `timesheetlog` */

insert  into `timesheetlog`(`tsid`,`actionby`,`actionon`,`oldstatus`,`newstatus`,`comment`,`id`,`version`) values ('aaaaaaaaaa','1111111111',1413990837284,0,1,'','d671df26-ebdf-4c81-8a4b-59cc15a49926',2);

/*Table structure for table `userlogin` */

DROP TABLE IF EXISTS `userlogin`;

CREATE TABLE `userlogin` (
  `userid` varchar(50) default NULL,
  `username` varchar(50) NOT NULL,
  `ts` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `PASSWORD` varchar(50) NOT NULL default 'dl@b1234',
  `sequerityques` varchar(50) default NULL,
  `ans` varchar(20) default NULL,
  `STATUS` bit(1) default NULL,
  PRIMARY KEY  (`username`),
  KEY `userslogin_ihgj_123` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `userlogin` */

insert  into `userlogin`(`userid`,`username`,`ts`,`PASSWORD`,`sequerityques`,`ans`,`STATUS`) values ('1111111111','demo','2014-09-27 12:44:14','demo','','',''),('9fbdc554-c9ca-4f90-957a-5ecea4fe3f56','tr.fs@com','2014-10-25 14:18:20','962d9cfcf50f',NULL,NULL,''),('f894aaed-6d66-4251-be11-ae44d2fed0c2','uiui','2014-10-12 05:54:13','1ee1954b7bf3',NULL,NULL,'');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userid` varchar(50) NOT NULL,
  `fname` varchar(50) default NULL,
  `mname` varchar(50) default NULL,
  `lname` varchar(50) default NULL,
  `dob` bigint(20) default NULL,
  `emailid` varchar(60) default NULL,
  `contactno` varchar(20) default NULL,
  `address` varchar(100) default NULL,
  `city` varchar(30) default NULL,
  `gender` varchar(2) default NULL,
  `profilepic` varchar(100) default NULL,
  `passwordsetting` smallint(6) default '0',
  `createdon` datetime default NULL,
  `createdby` varchar(40) default NULL,
  `modifiedon` datetime default NULL,
  `modifiedby` varchar(40) default NULL,
  `designation` varchar(60) default NULL,
  `roleid` int(4) default NULL COMMENT 'admin,employee',
  `deleted` smallint(6) default NULL,
  `deletedon` datetime default NULL,
  `deletedby` varchar(40) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `timesheet_db1`.`timesheetdetail` 
   add column `locationid` varchar(40) NULL after `activityId`
/*Data for the table `users` */

insert  into `users`(`userid`,`fname`,`mname`,`lname`,`dob`,`emailid`,`contactno`,`address`,`city`,`gender`,`profilepic`,`passwordsetting`,`createdon`,`createdby`,`modifiedon`,`modifiedby`,`designation`,`roleid`,`deleted`,`deletedon`,`deletedby`) values ('1111111111','kamlesh',NULL,'Garrison Mcknight',2014,'garrison@gmail.com','949494949','pune','pune','Ma','35d3be72-77e4-4128-ab35-02baa8ba2a4dImage023.jpg',1,'2014-01-07 00:00:00',NULL,'2014-01-07 00:00:00',NULL,'Super Admin',1,0,NULL,NULL),('96a847e5-0201-4bd4-8618-97cbf3879d11','iiii','ou','uiui',1404844200000,'uiui','uiui','uiui','uiui','M',NULL,0,'2014-10-12 05:40:33','1111111111',NULL,NULL,'uiui',1,0,NULL,NULL),('f894aaed-6d66-4251-be11-ae44d2fed0c2','iiii','ou','uiui',1404844200000,'uiui','uiui','uiui','uiui','M',NULL,0,'2014-10-12 05:54:12',NULL,NULL,NULL,'uiui',1,0,NULL,NULL),('9fbdc554-c9ca-4f90-957a-5ecea4fe3f56','Ritu','k','Sah',1170268200000,'tr.fs@com','6576578975','Niladri vihar ','Bhubaneswar','F',NULL,0,'2014-10-25 14:18:20','1111111111',NULL,NULL,'ftyty',4,0,NULL,NULL);

