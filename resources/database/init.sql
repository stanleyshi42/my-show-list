create database if not exists myshowlist;
use myshowlist;

create table if not exists users(
	user_id int not null auto_increment,
	username char(15) not null,
    password char(30) not null,
    primary key(user_id),
    unique(username)
);

create table if not exists shows(
	show_id int not null auto_increment,
	title char(50) not null,
    episode_count smallint not null,
    season_count tinyint,
    primary key(show_id),
    unique(title)
);

create table if not exists statuses(
	status_id int not null,
	status varchar(13) not null,
    primary key(status_id)
);

create table if not exists trackers(
	user_id int,
    show_id int,
    current_episode int,
    current_season int,
    status int,
    primary key(user_id, show_id),
    foreign key(user_id) references users(user_id),
    foreign key(show_id) references shows(show_id)
);

insert into users(username, password)
values('stan','password');
insert into users(username, password)
values('john','1234');
select * from users;

insert into statuses(status_id, status)
values(0, 'Plan to Watch');
insert into statuses(status_id, status)
values(1, 'Watching');
insert into statuses(status_id, status)
values(2, 'Compelted');
insert into statuses(status_id, status)
values(3, 'On Hold');
insert into statuses(status_id, status)
values(4, 'Dropped');
select * from statuses;

insert into shows(title, episode_count, season_count)
values('Breaking Bad', 60, 6);
insert into shows(title, episode_count, season_count)
values('It\'s Always Sunny in Philadelphia', 160, 15);
insert into shows(title, episode_count, season_count)
values('Naruto', 300, 1);
insert into shows(title, episode_count, season_count)
values('The Office', 90, 9);
select * from shows;

insert into trackers(user_id, show_id, current_episode, current_season, status)
values(1, 1, 7, 1, 1);
insert into trackers(user_id, show_id, current_episode, current_season, status)
values(2, 1, 15, 2, 4);
insert into trackers(user_id, show_id, current_episode, current_season, status)
values(2, 2, 111, 1, 5);
select * from trackers;