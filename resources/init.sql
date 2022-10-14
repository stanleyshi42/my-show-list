create database if not exists myshowlist;
use myshowlist;

create table if not exists users(
	userID int not null auto_increment,
	username char(20) not null,
    password char(30) not null,
    primary key(userID),
    unique(username)
);

create table if not exists shows(
	showID int not null auto_increment,
	title char(50) not null,
    episodeCount smallint not null,
    seasonCount tinyint,
    primary key(showID),
    unique(title)
);

create table if not exists statuses(
	statusID int not null,
	status varchar(13) not null,
    primary key(statusID)
);

create table if not exists trackers(
	userID int,
    showID int,
    currentEpisode int,
    currentSeason int,
    statusID int,
    primary key(userID, showID),
    foreign key(userID) references users(userID),
    foreign key(showID) references shows(showID),
    foreign key(statusID) references statuses(statusID)
);

insert into statuses(statusID, status)
values(0, 'Watching');
insert into statuses(statusID, status)
values(1, 'Completed');
insert into statuses(statusID, status)
values(2, 'On Hold');
insert into statuses(statusID, status)
values(3, 'Dropped');
insert into statuses(statusID, status)
values(4, 'Plan to Watch');

insert into shows(title, episodeCount, seasonCount)
values('Breaking Bad', 62, 5);
insert into shows(title, episodeCount, seasonCount)
values('It\'s Always Sunny in Philadelphia', 162, 15);
insert into shows(title, episodeCount, seasonCount)
values('Naruto', 220, 5);
insert into shows(title, episodeCount, seasonCount)
values('The Office', 201, 9);
insert into shows(title, episodeCount, seasonCount)
values('Nathan for You', 32, 4);
insert into shows(title, episodeCount, seasonCount)
values('The Rehearsal', 6, 1);
insert into shows(title, episodeCount, seasonCount)
values('The Simpsons', 731, 34);
insert into shows(title, episodeCount, seasonCount)
values('The Boys', 24, 3);
insert into shows(title, episodeCount, seasonCount)
values('Seinfeld', 180, 9);
insert into shows(title, episodeCount, seasonCount)
values('Diners, Drive-Ins and Dives', 419, 35);
insert into shows(title, episodeCount, seasonCount)
values('Hell\'s Kitchen', 316, 21);
insert into shows(title, episodeCount, seasonCount)
values('One Piece', 1025, 20);

insert into users(username, password)
values('stan','1234');
insert into users(username, password)
values('john','password');
insert into users(username, password)
values('bob','1');
insert into users(username, password)
values('qwerty','1');
insert into users(username, password)
values('wasd','1');

insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 1, 7, 1, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 7, 645, 30, 2);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 10, 0, 0, 4);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 11, 316, 21, 1);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 3, 99, 0, 3);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 12, 1025, 0, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(1, 2, 0, 0, 4);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(2, 1, 15, 2, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(2, 2, 111, 0, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(2, 5, 5, 1, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(3, 12, 123, 1, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(4, 12, 274, 1, 0);
insert into trackers(userID, showID, currentEpisode, currentSeason, statusID)
values(5, 12, 900, 5, 0);

select * from statuses;
select * from users;
select * from shows;
select * from trackers;
