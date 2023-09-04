create table work_category (
id  bigserial not null,
 category_name varchar(255),

 primary key (id));
 
ALTER TABLE work_type ADD COLUMN work_category_id int8;
alter table work_type add constraint work_category_id_fk foreign key (work_category_id) references work_category;
