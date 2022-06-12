create table marks (
                id int unsigned auto_increment,
                mark_date date,
                student_id int not null,
                mark_description varchar_casesensitive(500) not null,
                mark int not null,


                foreign key (student_id) references students (id)
);