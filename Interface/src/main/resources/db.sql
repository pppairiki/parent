--用户表 t_user
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
  	`account` varchar(25) DEFAULT NULL COMMENT '登录账号：邮箱or英文字符串',
  	`password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  	`name` 	varchar(25) DEFAULT NULL COMMENT '真实姓名',
  	`sex` 	int(11) DEFAULT NULL COMMENT '性别：1男2女',
  	`visable` int(11) DEFAULT NULL DEFAULT 1 COMMENT '是否有效 1有效 2无效 3空角色用户',
  	`phone` 	varchar(20) DEFAULT NULL COMMENT '手机号',
  	`idCard` 	varchar(20) DEFAULT NULL COMMENT '身份证号',
  	`email` 	varchar(11) DEFAULT NULL COMMENT '邮箱',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--用户组 t_user_group
DROP TABLE IF EXISTS `t_user_group`;
CREATE TABLE `t_user_group` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
  	`name` 	varchar(25) DEFAULT NULL COMMENT '组名',
  	`visable` bigint(32) DEFAULT NULL DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--用户组关系 t_user_group_relation
DROP TABLE IF EXISTS `t_user_group_relation`;
CREATE TABLE `t_user_group_relation` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` 	varchar(25) DEFAULT NULL COMMENT 'name',
	`userId` int(11) DEFAULT 0 DEFAULT 1 COMMENT '用户id',
	`userGroupId` int(11) DEFAULT 0 DEFAULT 1 COMMENT '用户组id',
  	`visable` int(11) DEFAULT NULL DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--角色表 t_role
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
  	`name` 	varchar(25) DEFAULT NULL COMMENT '角色名',
  	`visable` bigint(32) DEFAULT NULL DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--角色关系 t_user_group_relation
DROP TABLE IF EXISTS `t_user_role_relation`;
CREATE TABLE `t_user_role_relation` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` 	varchar(25) DEFAULT NULL COMMENT 'name',
	`userId` int(11) DEFAULT 0 COMMENT '用户id',
	`roleId` int(11) DEFAULT 0 COMMENT '角色id',
  	`visable` int(11) DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--权限定义表 t_priority
DROP TABLE IF EXISTS `t_priority`;
CREATE TABLE `t_priority` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	`priorityType` int(11) DEFAULT 0 COMMENT '权限类型 1-角色权限，2-组权限',
  	`name` 	varchar(25) DEFAULT NULL COMMENT '权限名',
  	`visable` int(11) DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--权限内容表 t_priority_content
DROP TABLE IF EXISTS `t_priority_content`;
CREATE TABLE `t_priority_content` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	`contentType` int(11) DEFAULT 0 COMMENT '内容类型 1-菜单，2-action',
	`name` 	varchar(25) DEFAULT NULL COMMENT 'name',
	`contentId` int(11) DEFAULT 0 COMMENT '内容id（菜单id或action id）',
  	`visable` int(11) DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--相册表t_gallery
DROP TABLE IF EXISTS `t_gallery`;
CREATE TABLE `t_gallery` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	  `name` 	varchar(25) DEFAULT NULL COMMENT 'name',
    `summary` 	varchar(60) DEFAULT NULL COMMENT 'summary',
	  `createrId` int(11) DEFAULT 0 COMMENT '创建人id',
    `createName` 	varchar(10) DEFAULT NULL COMMENT '创建人',
    `isPrivate` int(11) DEFAULT 0 COMMENT '是否隐私文件 1-是，0-否',
  	`visable` bigint(20) DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
insert into t_gallery(name,summary,createrId,createName,visable,remark,createTime)values('dubbo笔记','dubbo笔记',1,'riki','1','',now());
insert into t_gallery(name,summary,createrId,createName,visable,remark,createTime)values('Nginx笔记','Nginx笔记',1,'riki','1','',now());
--图片表t_image
DROP TABLE IF EXISTS `t_image`;
CREATE TABLE `t_image` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	  `infoType` int(11) DEFAULT 0 COMMENT '图片类型 1-笔记',
	  `infoId` int(11) DEFAULT 0 COMMENT '归属Id',
	  `createrId` int(11) DEFAULT 0 COMMENT '创建人',
    `createrName` 	varchar(10) DEFAULT NULL COMMENT '创建人',
  	`visable` bigint(20) DEFAULT 1 COMMENT '是否有效 1有效 除了1之外都无效每次删除是用时间戳标记',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--文件表t_file
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	  `name` 	varchar(25) DEFAULT NULL COMMENT 'name',
    `summary` 	varchar(60) DEFAULT NULL COMMENT 'summary',
	  `createrId` int(11) DEFAULT 0 COMMENT '创建人id',
    `createName` 	varchar(10) DEFAULT NULL COMMENT '创建人',
    `isPrivate` int(11) DEFAULT 0 COMMENT '是否隐私文件 1-是，0-否',
  	`visable` bigint(20) DEFAULT 1 COMMENT '是否有效 1有效 2无效',
  	`remark` 	varchar(100) DEFAULT NULL COMMENT '备注',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;
--文件内容表t_file_content
DROP TABLE IF EXISTS `t_file_content`;
CREATE TABLE `t_file_content` (
  	`id` int(11) NOT NULL AUTO_INCREMENT,
	  `fileId` int(11) DEFAULT 0 COMMENT 'file id',
	  `sortId` int(11) DEFAULT 0 COMMENT 'sortId',
	  `createrId` int(11) DEFAULT 0 COMMENT '创建人id',
    `createName` 	varchar(10) DEFAULT NULL COMMENT '创建人',
  	`createTime` datetime DEFAULT NULL COMMENT '创建时间',
  	`updateTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;