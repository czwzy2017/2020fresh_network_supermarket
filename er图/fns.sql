/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/8 13:31:46                            */
/*==============================================================*/


drop table if exists admin;

drop table if exists commend;

drop table if exists cookbook;

drop table if exists coupon;

drop table if exists deliver_address;

drop table if exists discount_goods;

drop table if exists fresh_category;

drop table if exists full_discount;

drop table if exists goods;

drop table if exists goods_comment;

drop table if exists goods_orders;

drop table if exists goods_procurement;

drop table if exists orders_detail;

drop table if exists promotion;

drop table if exists user_coupon;

drop table if exists user_infor;

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   admin_id             char(20) not null,
   admin_name           char(20) not null,
   admin_pwd            char(20) not null,
   primary key (admin_id)
);

/*==============================================================*/
/* Table: commend                                               */
/*==============================================================*/
create table commend
(
   goods_id             int not null,
   cookbook_id          int not null,
   commend_description  varchar(100),
   primary key (goods_id, cookbook_id)
);

/*==============================================================*/
/* Table: cookbook                                              */
/*==============================================================*/
create table cookbook
(
   cookbook_id          int not null auto_increment,
   cookbook_name        varchar(20) not null,
   cookbook_ingredient  varchar(100),
   cookbook_step        varchar(100),
   cookbook_image       longblob,
   primary key (cookbook_id)
);

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   coupon_id            int not null auto_increment,
   coupon_detail        varchar(100),
   coupon_minimum_price double not null,
   coupon_credit_price  double not null,
   coupon_begin_date    timestamp not null,
   coupon_end_date      timestamp not null,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: deliver_address                                       */
/*==============================================================*/
create table deliver_address
(
   delivery_id          int not null,
   user_id              int,
   delivery_province    varchar(20) not null,
   delivery_city        varchar(20) not null,
   delivery_district    varchar(20) not null,
   delivery_address     varchar(20) not null,
   delivery_contact     varchar(20) not null,
   delivery_tel         varchar(20) not null,
   primary key (delivery_id)
);

/*==============================================================*/
/* Table: discount_goods                                        */
/*==============================================================*/
create table discount_goods
(
   discount_id          int not null,
   goods_id             int not null,
   discount_begin_date  timestamp not null,
   discount_end_date    timestamp not null,
   primary key (discount_id, goods_id)
);

/*==============================================================*/
/* Table: fresh_category                                        */
/*==============================================================*/
create table fresh_category
(
   category_id          int not null auto_increment,
   category_name        varchar(20) not null,
   category_description varchar(100),
   primary key (category_id)
);

/*==============================================================*/
/* Table: full_discount                                         */
/*==============================================================*/
create table full_discount
(
   discount_id          int not null auto_increment,
   discount_detail      varchar(100),
   discount_goods_count int not null,
   discount             double not null,
   discount_begin_date  timestamp not null,
   discount_end_date    timestamp not null,
   primary key (discount_id)
);

/*==============================================================*/
/* Table: goods                                                 */
/*==============================================================*/
create table goods
(
   goods_id             int not null auto_increment,
   category_id          int,
   goods_name           varchar(20) not null,
   goods_price          double not null,
   goods_vip_price      double,
   goods_count          int not null,
   goods_size           varchar(20),
   goods_details        varchar(100),
   primary key (goods_id)
);

/*==============================================================*/
/* Table: goods_comment                                         */
/*==============================================================*/
create table goods_comment
(
   goods_id             int not null,
   user_id              int not null,
   comment_detail       varchar(100),
   comment_date         timestamp not null,
   comment_level        int not null,
   comment_image        longblob,
   primary key (goods_id, user_id)
);

/*==============================================================*/
/* Table: goods_orders                                          */
/*==============================================================*/
create table goods_orders
(
   orders_id            int not null auto_increment,
   coupon_id            int,
   user_id              int,
   delivery_id          int,
   orders_original_price double not null,
   orders_final_price   double not null,
   orders_time          timestamp not null,
   orders_status        char(4) not null,
   primary key (orders_id)
);

/*==============================================================*/
/* Table: goods_procurement                                     */
/*==============================================================*/
create table goods_procurement
(
   procurement_id       int not null auto_increment,
   admin_id             char(20),
   goods_id             int,
   procurement_count    int not null,
   procurement_status   char(4) not null,
   primary key (procurement_id)
);

/*==============================================================*/
/* Table: orders_detail                                         */
/*==============================================================*/
create table orders_detail
(
   orders_id            int not null,
   discount_id          int not null,
   goods_id             int not null,
   detail_count         int not null,
   goods_price          double not null,
   discount             double not null,
   primary key (orders_id, discount_id, goods_id)
);

/*==============================================================*/
/* Table: promotion                                             */
/*==============================================================*/
create table promotion
(
   promotion_id         int not null auto_increment,
   goods_id             int,
   promotion_price      double not null,
   promotion_count      int not null,
   promotion_begin_date timestamp not null,
   promotion_end_date   timestamp not null,
   primary key (promotion_id)
);

/*==============================================================*/
/* Table: user_coupon                                           */
/*==============================================================*/
create table user_coupon
(
   coupon_id            int not null,
   user_id              int not null,
   user_coupon_count    int not null,
   primary key (coupon_id, user_id)
);

/*==============================================================*/
/* Table: user_infor                                            */
/*==============================================================*/
create table user_infor
(
   user_id              int not null auto_increment,
   user_name            varchar(20) not null,
   user_sex             varchar(2) not null,
   user_pwd             varchar(20) not null,
   user_tel             varchar(20) not null,
   user_email           varchar(20),
   user_city            varchar(20) not null,
   user_creat_time      timestamp not null,
   user_vip             bool not null,
   user_vip_deadline    timestamp,
   primary key (user_id)
);

alter table commend add constraint FK_commend foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table commend add constraint FK_commend2 foreign key (cookbook_id)
      references cookbook (cookbook_id) on delete restrict on update restrict;

alter table deliver_address add constraint FK_user_delivery foreign key (user_id)
      references user_infor (user_id) on delete restrict on update restrict;

alter table discount_goods add constraint FK_discount_goods foreign key (discount_id)
      references full_discount (discount_id) on delete restrict on update restrict;

alter table discount_goods add constraint FK_discount_goods2 foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table goods add constraint FK_category_goods foreign key (category_id)
      references fresh_category (category_id) on delete restrict on update restrict;

alter table goods_comment add constraint FK_goods_comment foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table goods_comment add constraint FK_goods_comment2 foreign key (user_id)
      references user_infor (user_id) on delete restrict on update restrict;

alter table goods_orders add constraint FK_coupon_orders foreign key (coupon_id)
      references coupon (coupon_id) on delete restrict on update restrict;

alter table goods_orders add constraint FK_delivery_orders foreign key (delivery_id)
      references deliver_address (delivery_id) on delete restrict on update restrict;

alter table goods_orders add constraint FK_user_orders foreign key (user_id)
      references user_infor (user_id) on delete restrict on update restrict;

alter table goods_procurement add constraint FK_admin_procurement foreign key (admin_id)
      references admin (admin_id) on delete restrict on update restrict;

alter table goods_procurement add constraint FK_goods_procurement foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table orders_detail add constraint FK_orders_detail foreign key (orders_id)
      references goods_orders (orders_id) on delete restrict on update restrict;

alter table orders_detail add constraint FK_orders_detail2 foreign key (discount_id)
      references full_discount (discount_id) on delete restrict on update restrict;

alter table orders_detail add constraint FK_orders_detail3 foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table promotion add constraint FK_goods_promotion foreign key (goods_id)
      references goods (goods_id) on delete restrict on update restrict;

alter table user_coupon add constraint FK_user_coupon foreign key (coupon_id)
      references coupon (coupon_id) on delete restrict on update restrict;

alter table user_coupon add constraint FK_user_coupon2 foreign key (user_id)
      references user_infor (user_id) on delete restrict on update restrict;

insert into admin(admin_id,admin_name,admin_pwd) value ('0','陈哲炜','czwczwczw');