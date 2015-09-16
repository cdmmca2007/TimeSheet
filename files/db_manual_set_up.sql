CREATE DATABASE `timesheet_db`;

USE `timesheet_db`;


/*Application Metadata table name */
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userid` varchar(50) NOT NULL PRIMARY KEY,
  `fname` varchar(50) default NULL,
  `mname` varchar(50) default NULL,
  `lname` varchar(50) default NULL,
  `dob` bigint(20) default NULL,
  `emailid` varchar(60) NOT NULL,
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
  `roleid` int(4) NOT NULL COMMENT 'admin,employee',
  `deleted` smallint(6) default NULL,
  `deletedon` datetime default NULL,
  `deletedby` varchar(40) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `userlogin`;

CREATE TABLE `userlogin` (
  `userid` VARCHAR(50) DEFAULT NULL,
  `username` VARCHAR(50) NOT NULL PRIMARY KEY,
  `ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `password` VARCHAR(50) NOT NULL DEFAULT 'dl@b1234',
  `sequerityques` VARCHAR(50) DEFAULT NULL,
  `ans` VARCHAR(20) DEFAULT NULL,
  `status` BIT(1) DEFAULT NULL,
  `passwordchangedby` VARCHAR(50) DEFAULT NULL,
  `passwordchangedon` datetime default NULL,
  `diactivatedby`  VARCHAR(50) DEFAULT NULL,
  `diactivatedon` datetime default NULL,
  KEY `userlogin_fk_1` (`userid`),
  CONSTRAINT `userlogin_fk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `countryid` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  PRIMARY KEY  (`countryid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `states`;
CREATE TABLE `states` (
  `stateid` INT(3) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) DEFAULT NULL,
  `countryid` INT(3) DEFAULT NULL,
  PRIMARY KEY  (`stateid`),
  KEY `state_ibfk_1` (`countryid`),
  CONSTRAINT `state_ibfk_1` FOREIGN KEY (`countryid`) REFERENCES `country` (`countryid`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(2) NOT NULL auto_increment,
  `name` varchar(30) default NULL,
  `permvalue` int(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `accessgroups`;
CREATE TABLE `accessgroups` (
  `no` int(3) NOT NULL,
  `groupid` varchar(50) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY  (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `rolesgroupspermission`;
CREATE TABLE `rolesgroupspermission` (
  `roleid` int(3) NOT NULL,
  `groupid` varchar(30) NOT NULL,
  `readonly` int(11) default NULL,
  `editable` int(11) default NULL,
  KEY `rolesgroupspermission_fk_1` (`roleid`),
  KEY `rolesgroupspermission_fk_2` (`groupid`),
  CONSTRAINT `rolesgroupspermission_fk_1` FOREIGN KEY (`roleid`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `rolesgroupspermission_fk_2` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `id` int(3) NOT NULL,
  `groupid` varchar(50) NOT NULL,
  `name` varchar(30) default NULL,
  `ts` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `image` varchar(40) default NULL,
  `tooltip` varchar(100) default NULL,
  `callback` varchar(100) NOT NULL,
  KEY `permission_fk_1` (`groupid`),
  CONSTRAINT `permission_fk_1` FOREIGN KEY (`groupid`) REFERENCES `accessgroups` (`groupid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
  `id` int(3) NOT NULL auto_increment,
  `value` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `master`;
CREATE TABLE `master` (
  `id` varchar(36) NOT NULL,
  `value` varchar(50) NOT NULL,
  `propertyid` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `master_fk_1` (`propertyid`),
  CONSTRAINT `master_fk_1` FOREIGN KEY (`propertyid`) REFERENCES `properties` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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


DROP TABLE IF EXISTS `forgotpasswordrequest`;
CREATE TABLE `forgotpasswordrequest` (
  `pid` varchar(40) NOT NULL,
  `userid` varchar(40) default NULL,
  `username` varchar(40) default NULL,
  `oldpassword` varchar(40) default NULL,
  `newpassword` varchar(40) default NULL,
  `createdon` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`pid`),
  KEY `forgotpasswordrequest_fk_1` (`userid`),
  CONSTRAINT `forgotpasswordrequest_fk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `calendar_id` int(2) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL,
  `description` varchar(250) default NULL,
  `color` varchar(10) default NULL,
  `hidden` bit(1) default NULL,
  PRIMARY KEY  (`calendar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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
  KEY `events_fk_1` (`calendar_id`),
  CONSTRAINT `events_fk_1` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`calendar_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `actions`;
CREATE TABLE `actions` (
  `actionid` int(5) NOT NULL default '0',
  `actiontype` varchar(256) NOT NULL default '',
  PRIMARY KEY  (`actionid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `audittrail`;
CREATE TABLE `audittrail` (
  `actionid` int(5) NOT NULL,
  `params` varchar(100) default NULL,
  `ipaddr` varchar(36) NOT NULL default '',
  `userid` varchar(36) NOT NULL default '',
  `ts` bigint(20) default NULL,
  KEY `audittrail_fk_1` (`actionid`),
  CONSTRAINT `audittrail_fk_1` FOREIGN KEY (`actionid`) REFERENCES `actions` (`actionid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--------------------------------------------------------------------------------
/*Application related table*/

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
  `deletedby` varchar(40) default NULL,
  `deletedon` datetime default NULL,
  PRIMARY KEY  (`activityid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `deletedby` varchar(40) default NULL,
  `deletedon` datetime default NULL
  PRIMARY KEY  (`projectid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
   PRIMARY KEY  (`id`),
   KEY `projectactivity_fk_1` (`projectid`),
   CONSTRAINT `projectactivity_fk_1` FOREIGN KEY (`projectid`) REFERENCES `project` (`projectid`) ON DELETE CASCADE ON UPDATE CASCADE,
   KEY `projectactivity_fk_2` (`activityid`),
   CONSTRAINT `projectactivity_fk_2` FOREIGN KEY (`activity`) REFERENCES `activity` (`activityid`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



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
  `deletedby` varchar(40) default NULL,
  `deletedon` datetime default NULL,
  PRIMARY KEY  (`locationid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `projectmember`;
CREATE TABLE `projectmember` (
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
   PRIMARY KEY  (`id`),
   KEY `projectmember_fk_1` (`projectid`),
   CONSTRAINT `projectmember_fk_1` FOREIGN KEY (`projectid`) REFERENCES `project` (`projectid`) ON DELETE CASCADE ON UPDATE CASCADE,
   KEY `projectmember_fk_2` (`userid`),
   CONSTRAINT `projectmember_fk_2` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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
   PRIMARY KEY  (`id`),
   KEY `timesheet_fk_1` (`approvedby`),
   CONSTRAINT `timesheet_fk_1` FOREIGN KEY (`approvedby`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `activityId` varchar(40) default NULL,
   PRIMARY KEY  (`tsid`),
   KEY `timesheetdetail_fk_1` (`projectId`),
   CONSTRAINT `timesheetdetail_fk_1` FOREIGN KEY (`approvedby`) REFERENCES `users` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
   KEY `timesheetdetail_fk_2` (`activityId`),
   CONSTRAINT `timesheetdetail_fk_2` FOREIGN KEY (`activityId`) REFERENCES `projectactivity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*Table structure for table `timesheetlog` */

DROP TABLE IF EXISTS `timesheetlog`;
CREATE TABLE `timesheetlog` (
  `id` varchar(40) NOT NULL,
  `tsid` varchar(40) NOT NULL,
  `actionby` varchar(40) NOT NULL,
  `actionon` bigint(20) NOT NULL,
  `oldstatus` int(3) NOT NULL,
  `newstatus` int(3) NOT NULL,
  `comment` varchar(500) default '',
  `version` int(11) default NULL,
   PRIMARY KEY  (`id`),
   KEY `timesheetlog_fk_1` (`tsid`),
   CONSTRAINT `timesheetlog_fk_1` FOREIGN KEY (`tsid`) REFERENCES `timesheetdetail` (`tsid`) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table `designation` (
	`id` int ,
	`designation` varchar (300),
	`createdby` varchar (300),
	`createdon` datetime ,
	`modifiedby` varchar (300),
	`modifiedon` timestamp ,
        PRIMARY KEY  (`id`)
);


 
ALTER TABLE users ADD COLUMN superfund VARCHAR(200); 
ALTER TABLE users ADD COLUMN tfnno VARCHAR(30)  ;
ALTER TABLE users ADD COLUMN accountno VARCHAR(30)  ;
ALTER TABLE users ADD COLUMN bsbno VARCHAR(30)  ;
ALTER TABLE users ADD COLUMN homephone VARCHAR(30)  ;
ALTER TABLE users ADD COLUMN spousename VARCHAR(30)  ;

ALTER TABLE project ADD COLUMN projectvalue DOUBLE;


--------------------------------------------------------------------------------



CREATE INDEX users_idx_1 ON users(managerid); 
CREATE INDEX users_idx_2 ON users(roleid); 
CREATE INDEX userslogin_idx_1 ON userlogin(userid); 
CREATE INDEX timesheet_idx_1 ON timesheet(locationid); 
CREATE INDEX timesheet_idx_2 ON timesheet(userid); 
CREATE INDEX timesheet_idx_3 ON timesheet(fromdate,todate); 
CREATE INDEX timesheetlog_idx_1 ON timesheetlog(tsid); 
CREATE INDEX timesheetlog_idx_1 ON timesheetlog(tsid); 
CREATE INDEX timesheetdetail_idx_1 ON timesheetdetail(tsid); 
CREATE INDEX timesheetdetail_idx_2 ON timesheetdetail(projectid); 
CREATE INDEX roles_idx_1 ON roles(id); 
CREATE INDEX properties_idx_1 ON properties(id); 
CREATE INDEX projectmember_idx_1 ON projectmember(resourceid);
CREATE INDEX projectmember_idx_2 ON projectmember(projectid);
CREATE INDEX projectlocation_idx_1 ON projectlocation(locationid);
CREATE INDEX projectactivity_idx_1 ON projectactivity(projectid);
CREATE INDEX projectactivity_idx_2 ON projectactivity(activityid);
CREATE INDEX project_idx_1 ON project(projectid);
CREATE INDEX project_idx_2 ON project(projectmanagerid);
CREATE INDEX master_idx_1  ON MASTER(id,propertyid) ;
CREATE INDEX mail_template_idx_1 ON mail_template(template_id);
CREATE INDEX designation_idx_1   ON designation(id);
CREATE INDEX deptgroup_idx_1   ON deptgroup(id);
CREATE INDEX activity_idx_1   ON activity(activityid);