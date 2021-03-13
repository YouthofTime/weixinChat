# Host: 127.0.0.1  (Version: 5.7.20)
# Date: 2021-02-23 19:01:25
# Generator: MySQL-Front 5.3  (Build 4.269)

/*!40101 SET NAMES gb2312 */;

#
# Structure for table "apply"
#

DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `applyId` int(11) NOT NULL AUTO_INCREMENT,
  `textConfirm` text,
  `sendUid` varchar(20) DEFAULT NULL,
  `receiveId` varchar(20) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `gsId` varchar(20) DEFAULT NULL,
  `isApprove` tinyint(1) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `headImg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`applyId`),
  KEY `gsId` (`gsId`),
  KEY `sendUid` (`sendUid`),
  KEY `receiveUid` (`receiveId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "apply"
#


#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(30) DEFAULT NULL,
  `password` varchar(16) DEFAULT NULL,
  `username` char(30) NOT NULL,
  `bind_email` char(30) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `dsqId` varchar(20) DEFAULT NULL,
  `headImg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dsqId` (`dsqId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (15,'15684961565','123456','知乎用户','',NULL,NULL,'15684961565','user/头像4.jpg'),(16,'12345678901','123456','艾榕俐全息艾灸（霞宝）','3493487139@qq.com','湖南 邵阳','女','xia3493487139','1614076579443.jpg');

#
# Structure for table "singlemsg"
#

DROP TABLE IF EXISTS `singlemsg`;
CREATE TABLE `singlemsg` (
  `singleId` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` text,
  `sendUid` int(11) DEFAULT NULL,
  `receiveUid` int(11) DEFAULT NULL,
  PRIMARY KEY (`singleId`),
  KEY `sendUid` (`sendUid`),
  KEY `receiveUid` (`receiveUid`),
  CONSTRAINT `singlemsg_ibfk_1` FOREIGN KEY (`sendUid`) REFERENCES `user` (`id`),
  CONSTRAINT `singlemsg_ibfk_2` FOREIGN KEY (`receiveUid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "singlemsg"
#


#
# Structure for table "groupsplit"
#

DROP TABLE IF EXISTS `groupsplit`;
CREATE TABLE `groupsplit` (
  `gsId` varchar(20) NOT NULL,
  `belongId` int(11) DEFAULT NULL,
  `gsname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`gsId`),
  KEY `belongId` (`belongId`),
  CONSTRAINT `groupsplit_ibfk_1` FOREIGN KEY (`belongId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "groupsplit"
#


#
# Structure for table "groupsplituser"
#

DROP TABLE IF EXISTS `groupsplituser`;
CREATE TABLE `groupsplituser` (
  `gsId` varchar(20) NOT NULL,
  `userid` int(11) NOT NULL,
  `remark` varchar(20) NOT NULL,
  `leaveTime` datetime DEFAULT NULL,
  PRIMARY KEY (`gsId`,`userid`),
  KEY `id` (`userid`),
  CONSTRAINT `groupsplituser_ibfk_1` FOREIGN KEY (`gsId`) REFERENCES `groupsplit` (`gsId`),
  CONSTRAINT `groupsplituser_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "groupsplituser"
#


#
# Structure for table "groupchat"
#

DROP TABLE IF EXISTS `groupchat`;
CREATE TABLE `groupchat` (
  `gcId` varchar(20) NOT NULL,
  `gcname` varchar(20) DEFAULT NULL,
  `buildtime` datetime DEFAULT NULL,
  `belongId` int(11) DEFAULT NULL,
  `headImg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`gcId`),
  KEY `belongId` (`belongId`),
  CONSTRAINT `belongId` FOREIGN KEY (`belongId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "groupchat"
#

INSERT INTO `groupchat` VALUES ('gc15_1','工具群','2021-02-23 18:32:38',15,'/group/头像3.jpg'),('gc16_1','工具群','2021-02-23 18:34:32',16,'/group/头像3.jpg');

#
# Structure for table "groupmsg"
#

DROP TABLE IF EXISTS `groupmsg`;
CREATE TABLE `groupmsg` (
  `gmId` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime DEFAULT NULL,
  `value` text,
  `sendUid` int(11) DEFAULT NULL,
  `receiveGcid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`gmId`),
  KEY `receiveGcid` (`receiveGcid`,`sendUid`),
  KEY `sendUid` (`sendUid`),
  CONSTRAINT `groupmsg_ibfk_1` FOREIGN KEY (`receiveGcid`) REFERENCES `groupchat` (`gcId`),
  CONSTRAINT `groupmsg_ibfk_2` FOREIGN KEY (`sendUid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "groupmsg"
#

INSERT INTO `groupmsg` VALUES (1,'2021-02-23 18:41:44','12',16,'gc16_1'),(2,'2021-02-23 18:43:19','1',15,'gc15_1'),(3,'2021-02-23 18:43:21','45',15,'gc15_1'),(4,'2021-02-23 18:43:23','554',15,'gc15_1');

#
# Structure for table "groupchatuser"
#

DROP TABLE IF EXISTS `groupchatuser`;
CREATE TABLE `groupchatuser` (
  `gcId` varchar(20) NOT NULL,
  `userid` int(11) NOT NULL,
  `enterTime` datetime DEFAULT NULL,
  `leaveTime` datetime DEFAULT NULL,
  PRIMARY KEY (`gcId`,`userid`),
  KEY `userid` (`userid`),
  CONSTRAINT `groupchatuser_ibfk_1` FOREIGN KEY (`gcId`) REFERENCES `groupchat` (`gcId`),
  CONSTRAINT `groupchatuser_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "groupchatuser"
#

INSERT INTO `groupchatuser` VALUES ('gc15_1',15,'2021-02-23 18:32:38','2021-02-23 18:43:30'),('gc16_1',16,'2021-02-23 18:34:32','2021-02-23 18:41:41');
