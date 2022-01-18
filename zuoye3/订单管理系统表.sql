/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.27 : Database - order management system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`order management system` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `order management system`;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `goods_id` int NOT NULL COMMENT '商品编号',
  `goods_name` varchar(10) NOT NULL COMMENT '商品名称',
  `goods_price` int NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `goods` */

insert  into `goods`(`goods_id`,`goods_name`,`goods_price`) values 
(1,'巧克力',20),
(2,'薯片',5),
(3,'饼干',3),
(4,'可乐',3),
(5,'甜甜圈',4),
(6,'牛奶',156);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int NOT NULL COMMENT '订单编号',
  `goods_id` int DEFAULT NULL COMMENT '商品编号',
  `order_time` date DEFAULT NULL COMMENT '下单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `order` */

insert  into `order`(`order_id`,`goods_id`,`order_time`) values 
(1,3,'2022-01-18'),
(2,5,'2022-01-18'),
(3,6,'2022-01-18'),
(4,1,'2022-01-18'),
(5,2,'2022-01-18'),
(6,4,'2022-01-18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
