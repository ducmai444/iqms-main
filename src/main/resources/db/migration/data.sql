INSERT into qms_site (id, name) values (1, 'Khu A'), (2, 'Khu B');

insert into qms_modality(id, site_id, name, code, modality_type, room_name)
values
(1, 1, 'CT2 - 2 dãy', '084816', 'CT', 'Phòng CT2 - 2 dãy'),
(2, 1, 'CT1 - 128 dãy', '0315YC', 'CT', 'Phòng CT1 - 128 dãy'),
(3, 1, 'MR1 - 1.5', '0315YC1', 'MR', 'Phòng MR1 - 1.5');

insert into qms_modality_type values ('CT'), ('MR'), ('DX'), ('US');

insert into qms_procedure(code, name, modality_type)
values
('CLVT040', 'Chụp CLVT cột sống cổ có tiêm thuốc cản quang (từ 64- 128 dãy)', 'CT'),
('CT12801', 'Chụp CLVT sọ não không tiêm thuốc cản quang (từ 1-32 dãy)', 'CT'),
('CT1280', 'Chụp CLVT sọ não không tiêm thuốc cản quang (từ 1-32 dãy)[Chụp trên máy 128 dãy]', 'CT'),
('CT12771', 'Chụp cắt lớp vi tính lồng ngực không tiêm thuốc cản quang (từ 1- 32 dãy) [lớp mỏng 1mm độ phân giải cao]', 'CT');

insert into qms_modality_procedure (modality_id, procedure_id)
values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(2, 3);

insert into qms_ticket (modality_id, patient_pid, patient_name, ticket_number)
values
(1, 'pid1', 'patient name 1', '001'),
(1, 'pid2', 'patient name 2', '001'),
(1, 'pid3', 'patient name 3', '001'),
(1, 'pid4', 'patient name 4', '001'),
(1, 'pid5', 'patient name 5', '001');

insert into qms_ticket (modality_id, patient_pid, patient_name, ticket_number)
values
(2, 'pid1', 'patient name 1', '001'),
(2, 'pid2', 'patient name 2', '001'),
(2, 'pid3', 'patient name 3', '001'),
(2, 'pid4', 'patient name 4', '001'),
(2, 'pid5', 'patient name 5', '001');


insert into qms_ticket_procedure (ticket_id, ris_accession_number)
values
(1, 'acc1'),
(1, 'acc2'),
(2, 'acc3');


insert into qms_modality_procedure (modality_id, procedure_id) (select 1, id from qms_procedure where modaity_type in ('DX', 'CR', 'DR'));
insert into qms_modality_procedure (modality_id, procedure_id) (select 1, id from qms_procedure where modaity_type in ('DX', 'CR', 'DR'));
insert into qms_modality_procedure (modality_id, procedure_id) (select 1, id from qms_procedure where modaity_type in ('DX', 'CR', 'DR'));
insert into qms_modality_procedure (modality_id, procedure_id) (select 1, id from qms_procedure where modaity_type in ('DX', 'CR', 'DR'));
insert into qms_modality_procedure (modality_id, procedure_id) (select 1, id from qms_procedure where modaity_type in ('DX', 'CR', 'DR'));

SHOW data_directory;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/x-quang1.wav')::bytea where id = 1;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/x-quang2.wav')::bytea where id = 17;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/x-quang3.wav')::bytea where id = 18;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/x-quang4.wav')::bytea where id = 19;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/CT1-128.wav')::bytea where id = 5;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/CT2-2.wav')::bytea where id = 15;

UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US304.wav')::bytea where id = 3;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US305.wav')::bytea where id = 62;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US306.wav')::bytea where id = 16;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US307.wav')::bytea where id = 11;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US308.wav')::bytea where id = 13;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US309.wav')::bytea where id = 14;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US310.wav')::bytea where id = 27;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US311.wav')::bytea where id = 59;
UPDATE qms_modality SET room_name_audio = pg_read_binary_file('sounds/US312.wav')::bytea where id = 2;