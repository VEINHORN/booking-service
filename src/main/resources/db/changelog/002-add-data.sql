insert into public.user(id, username) values('58763a87-ca4f-459b-b4eb-74a77d65ab50', 'admin');

insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 3, 2, 'Flat 1', 'FLAT', 10, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 4, 2, 'Flat 2', 'FLAT', 15, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 5, 3, 'Flat 3', 'FLAT', 20, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 3, 3, 'Flat 4', 'FLAT', 25, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 4, 4, 'Flat 5', 'FLAT', 30, '58763a87-ca4f-459b-b4eb-74a77d65ab50');

insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 4, 4, 'Apartments 1', 'APARTMENTS', 40, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 4, 4, 'Apartments 2', 'APARTMENTS', 45, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 4, 4, 'Apartments 3', 'APARTMENTS', 50, '58763a87-ca4f-459b-b4eb-74a77d65ab50');

insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 5, NULL, 'Home 1', 'HOME', 60, '58763a87-ca4f-459b-b4eb-74a77d65ab50');
insert into unit(id, number_of_rooms, floor, description, accomodation_type, cost, user_id)
	values(gen_random_uuid(), 5, NULL, 'Home 2', 'HOME', 70, '58763a87-ca4f-459b-b4eb-74a77d65ab50');