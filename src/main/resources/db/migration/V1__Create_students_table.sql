create table students (
                id int unsigned primary key auto_increment,
                name varchar(100) not null ,
                surname varchar(100) not null ,
                email varchar(50) not null ,
                major varchar(50) not null ,
                student_year int(1) not null ,
                student_group int(1) not null
                );
