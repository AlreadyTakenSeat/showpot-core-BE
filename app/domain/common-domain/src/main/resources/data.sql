-- 장르 데이터
INSERT INTO public.genre (id, created_at, updated_at, is_deleted, name)
VALUES ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d1', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'rock'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d2', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'hiphop'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'band'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d4', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'jpop'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d5', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'pop'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d6', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'house'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d7', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'edm'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d8', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'musical'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876d9', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'rnb'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876da', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'opera'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876db', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'metal'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876dc', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'classic'),
       ('017f20d0-4f3c-8f4d-9e15-7ff0c3a876dd', '2024-08-04 00:00:00', '2024-08-04 00:00:00', false,
        'jazz');

-- 아티스트
insert into public.artist (id, created_at, updated_at, is_deleted, name, image, spotify_id)
values  ('01937cf1-1ddf-9bea-0437-6e1238cf5809', '2024-11-30 20:59:56.938', '2024-11-30 20:59:56.938', false, 'Post Malone', 'https://i.scdn.co/image/ab6761610000f178e17c0aa1714a03d62b5ce4e0', '246dkjvS1zLTtiykXe5h60'),
        ('01937cf1-1ddf-9bea-0437-6e1238cf580a', '2024-11-30 20:59:56.944', '2024-11-30 20:59:56.944', false, 'Coldplay', 'https://i.scdn.co/image/ab6761610000f1781ba8fc5f5c73e7e9313cc6eb', '4gzpq5DPGxSnKTe4SA8HAU'),
        ('01937cf1-1ddf-9bea-0437-6e1238cf580b', '2024-11-30 20:59:56.946', '2024-11-30 20:59:56.946', false, 'Olivia Rodrigo', 'https://i.scdn.co/image/ab6761610000f178e03a98785f3658f0b6461ec4', '1McMsnEElThX1knmY4oliG'),
        ('01937cf1-1ddf-9bea-0437-6e1238cf580c', '2024-11-30 20:59:56.949', '2024-11-30 20:59:56.949', false, 'Bruno Mars', 'https://i.scdn.co/image/ab6761610000f178c36dd9eb55fb0db4911f25dd', '0du5cEVh5yTK9QJze8zA0C'),
        ('01937cf1-1ddf-9bea-0437-6e1238cf580d', '2024-11-30 20:59:56.951', '2024-11-30 20:59:56.951', false, 'AJR', 'https://i.scdn.co/image/ab6761610000f178e65fa0329c232ac6f5040f80', '6s22t5Y3prQHyaHWUN1R1C');
insert into public.artist_genre (id, created_at, updated_at, is_deleted, artist_id, genre_id)
values  ('01937cf1-1e0b-4699-6547-8703a41de62c', '2024-11-30 20:59:56.940', '2024-11-30 20:59:56.940', false, '01937cf1-1ddf-9bea-0437-6e1238cf5809', '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3'),
        ('01937cf1-1e10-293a-13e6-e05201a98ea5', '2024-11-30 20:59:56.944', '2024-11-30 20:59:56.944', false, '01937cf1-1ddf-9bea-0437-6e1238cf580a', '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3'),
        ('01937cf1-1e12-8f40-b8fc-f4570b8f5286', '2024-11-30 20:59:56.946', '2024-11-30 20:59:56.946', false, '01937cf1-1ddf-9bea-0437-6e1238cf580b', '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3'),
        ('01937cf1-1e14-c232-2f90-387646abc31d', '2024-11-30 20:59:56.949', '2024-11-30 20:59:56.949', false, '01937cf1-1ddf-9bea-0437-6e1238cf580c', '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d7'),
        ('01937cf1-1e16-2661-e3c6-69d00f16dc88', '2024-11-30 20:59:56.951', '2024-11-30 20:59:56.951', false, '01937cf1-1ddf-9bea-0437-6e1238cf580d', '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3');

-- 공연
insert into public.show (id, created_at, updated_at, is_deleted, start_date, end_date, title, content, location, image, last_ticketing_at, view_count, seat_prices, ticketing_sites, version)
values  ('01937cf9-9c48-4ac9-1c57-8aba45fd4a96', '2024-11-30 21:09:13.547', '2024-11-30 21:09:13.547', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Post malone 공연1', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-09-10%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2011.33.52_1732968553281.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 30000, "스탠딩 S석": 50000}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24011642"}', 0),
        ('01937cfa-8fb0-9491-7ea7-9ce4e630ce14', '2024-11-30 21:10:15.857', '2024-11-30 21:10:15.857', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Post malone 공연2', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-10-20%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.42_1732968615663.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 30000, "스탠딩 S석": 50000}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/"}', 0),
        ('01937cfd-0fc4-61b0-1e61-2529b72090d1', '2024-11-30 21:12:59.717', '2024-11-30 21:12:59.717', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Coldplay 공연1', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-11-21%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2010.22.46_1732968779518.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 29999, "스탠딩 S석": 50000}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24006288"}', 0),
        ('01937cfe-5b84-3371-3a7e-b42bc7f01e1c', '2024-11-30 21:14:24.645', '2024-11-30 21:14:24.645', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Coldplay 공연2 ', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-11-21%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%2010.22.46_1732968864483.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 33333, "스탠딩 S석": 55555}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24011642"}', 0),
        ('01937cff-5d76-b7a9-287c-9f3bbabbe0c6', '2024-11-30 21:15:30.679', '2024-11-30 21:15:30.679', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Olivia Rodrigo 공연1', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-09-10%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2011.33.52_1732968930507.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 33333, "스탠딩 S석": 44444}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24011642"}', 0),
        ('01937d00-10ea-0792-4d21-e9588ac9344b', '2024-11-30 21:16:16.619', '2024-11-30 21:16:16.619', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Olivia Rodrigo 공연2', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-10-20%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.42_1732968976457.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 33333, "스탠딩 S석": 44444}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24007623"}', 0),
        ('01937d01-0358-516e-a16f-fe2e30017cac', '2024-11-30 21:17:18.681', '2024-11-30 21:17:18.681', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Bruno Mars 공연1', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-10-20%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.42_1732969038561.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 33333, "스탠딩 S석": 55555}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/"}', 0),
        ('01937d01-e4a6-7d9b-dbf1-128cfb018783', '2024-11-30 21:18:16.360', '2024-11-30 21:18:16.360', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'Bruno Mars 공연2', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-10-20%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.42_1732969096164.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 11111, "스탠딩 S석": 22222}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24011642"}', 0),
        ('01937d02-d452-b277-3ea3-fb71d3887122', '2024-11-30 21:19:17.716', '2024-11-30 21:19:17.716', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'AJR 공연1', '.', '서울 잠실', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-09-10%20%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB%2011.33.52_1732969156590.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 11111, "스탠딩 S석": 22222}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/"}', 0),
        ('01937d05-17e3-3985-b7cf-0241e238c2d0', '2024-11-30 21:21:46.085', '2024-11-30 21:21:46.085', false, CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days', 'AJR 공연2', '.', '서울 망포', 'https://showpot.s3.ap-northeast-2.amazonaws.com/show/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-10-20%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.55.42_1732969305874.png', NOW() + INTERVAL '5 days', 0, '{"스탠딩 P석": 11111, "스탠딩 S석": 22222}', '{"YES24": "http://ticket.yes24.com/", "인터파크": "https://tickets.interpark.com/goods/24011642"}', 0);

insert into public.show_artist (id, created_at, updated_at, is_deleted, artist_id, show_id)
values  ('01937cf9-9c56-0477-c0e4-aaa2fdb4f0e6', '2024-11-30 21:09:13.559', '2024-11-30 21:09:13.559', false, '01937cf1-1ddf-9bea-0437-6e1238cf5809', '01937cf9-9c48-4ac9-1c57-8aba45fd4a96'),
        ('01937cfa-8fb2-39d2-a90b-efe470bae483', '2024-11-30 21:10:15.859', '2024-11-30 21:10:15.859', false, '01937cf1-1ddf-9bea-0437-6e1238cf5809', '01937cfa-8fb0-9491-7ea7-9ce4e630ce14'),
        ('01937cfd-0fc7-75ed-94ed-86a3996fcb08', '2024-11-30 21:12:59.720', '2024-11-30 21:12:59.720', false, '01937cf1-1ddf-9bea-0437-6e1238cf580a', '01937cfd-0fc4-61b0-1e61-2529b72090d1'),
        ('01937cfe-5b87-1677-f4a2-6743a8f20874', '2024-11-30 21:14:24.648', '2024-11-30 21:14:24.648', false, '01937cf1-1ddf-9bea-0437-6e1238cf580a', '01937cfe-5b84-3371-3a7e-b42bc7f01e1c'),
        ('01937cff-5d79-1030-a391-1d4b7124aebb', '2024-11-30 21:15:30.682', '2024-11-30 21:15:30.682', false, '01937cf1-1ddf-9bea-0437-6e1238cf580b', '01937cff-5d76-b7a9-287c-9f3bbabbe0c6'),
        ('01937d00-10ee-4742-e3d4-9b4bf26c5e50', '2024-11-30 21:16:16.622', '2024-11-30 21:16:16.622', false, '01937cf1-1ddf-9bea-0437-6e1238cf580b', '01937d00-10ea-0792-4d21-e9588ac9344b'),
        ('01937d01-0359-00e8-7cd6-a45b108d73d5', '2024-11-30 21:17:18.682', '2024-11-30 21:17:18.682', false, '01937cf1-1ddf-9bea-0437-6e1238cf580c', '01937d01-0358-516e-a16f-fe2e30017cac'),
        ('01937d01-e4a9-f167-7a2f-d722959ef7dd', '2024-11-30 21:18:16.362', '2024-11-30 21:18:16.362', false, '01937cf1-1ddf-9bea-0437-6e1238cf580c', '01937d01-e4a6-7d9b-dbf1-128cfb018783'),
        ('01937d02-d456-d7f4-693f-7cc9ec0eb868', '2024-11-30 21:19:17.719', '2024-11-30 21:19:17.719', false, '01937cf1-1ddf-9bea-0437-6e1238cf580d', '01937d02-d452-b277-3ea3-fb71d3887122'),
        ('01937d05-17e8-ad05-d846-c04d1bd0f793', '2024-11-30 21:21:46.089', '2024-11-30 21:21:46.089', false, '01937cf1-1ddf-9bea-0437-6e1238cf580d', '01937d05-17e3-3985-b7cf-0241e238c2d0');

insert into public.show_genre (id, created_at, updated_at, is_deleted, genre_id, show_id)
values  ('01937cf9-9c57-3424-b25f-053cbc5c30b8', '2024-11-30 21:09:13.560', '2024-11-30 21:09:13.560', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937cf9-9c48-4ac9-1c57-8aba45fd4a96'),
        ('01937cfa-8fb2-39d2-a90b-efe470bae484', '2024-11-30 21:10:15.859', '2024-11-30 21:10:15.859', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937cfa-8fb0-9491-7ea7-9ce4e630ce14'),
        ('01937cfd-0fc7-75ed-94ed-86a3996fcb09', '2024-11-30 21:12:59.720', '2024-11-30 21:12:59.720', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937cfd-0fc4-61b0-1e61-2529b72090d1'),
        ('01937cfe-5b88-be9e-8be0-6e8b79dfa85a', '2024-11-30 21:14:24.648', '2024-11-30 21:14:24.648', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937cfe-5b84-3371-3a7e-b42bc7f01e1c'),
        ('01937cff-5d7a-3899-f7b4-dc42a4836475', '2024-11-30 21:15:30.683', '2024-11-30 21:15:30.683', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937cff-5d76-b7a9-287c-9f3bbabbe0c6'),
        ('01937d00-10ee-4742-e3d4-9b4bf26c5e51', '2024-11-30 21:16:16.623', '2024-11-30 21:16:16.623', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937d00-10ea-0792-4d21-e9588ac9344b'),
        ('01937d01-035a-d41c-1a5c-11a4dec35591', '2024-11-30 21:17:18.682', '2024-11-30 21:17:18.682', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d7', '01937d01-0358-516e-a16f-fe2e30017cac'),
        ('01937d01-e4aa-82f5-04be-5b3dc9004c02', '2024-11-30 21:18:16.362', '2024-11-30 21:18:16.362', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d7', '01937d01-e4a6-7d9b-dbf1-128cfb018783'),
        ('01937d02-d457-ecbf-c3cb-264400e57221', '2024-11-30 21:19:17.719', '2024-11-30 21:19:17.719', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937d02-d452-b277-3ea3-fb71d3887122'),
        ('01937d05-17e8-ad05-d846-c04d1bd0f794', '2024-11-30 21:21:46.089', '2024-11-30 21:21:46.089', false, '017f20d0-4f3c-8f4d-9e15-7ff0c3a876d3', '01937d05-17e3-3985-b7cf-0241e238c2d0');

insert into public.show_search (id, created_at, updated_at, is_deleted, show_id, name)
values  ('01937cf9-9c53-9697-e672-1bb866fb7876', '2024-11-30 21:09:13.556', '2024-11-30 21:09:13.556', false, '01937cf9-9c48-4ac9-1c57-8aba45fd4a96', 'postmalone공연1'),
        ('01937cfa-8fb2-39d2-a90b-efe470bae482', '2024-11-30 21:10:15.858', '2024-11-30 21:10:15.858', false, '01937cfa-8fb0-9491-7ea7-9ce4e630ce14', 'postmalone공연2'),
        ('01937cfd-0fc7-75ed-94ed-86a3996fcb07', '2024-11-30 21:12:59.719', '2024-11-30 21:12:59.719', false, '01937cfd-0fc4-61b0-1e61-2529b72090d1', 'coldplay공연1'),
        ('01937cfe-5b87-1677-f4a2-6743a8f20873', '2024-11-30 21:14:24.647', '2024-11-30 21:14:24.647', false, '01937cfe-5b84-3371-3a7e-b42bc7f01e1c', 'coldplay공연2'),
        ('01937cff-5d78-6eea-c760-0a4a97403347', '2024-11-30 21:15:30.682', '2024-11-30 21:15:30.682', false, '01937cff-5d76-b7a9-287c-9f3bbabbe0c6', 'oliviarodrigo공연1'),
        ('01937d00-10ed-8f21-d993-08e03491bd00', '2024-11-30 21:16:16.622', '2024-11-30 21:16:16.622', false, '01937d00-10ea-0792-4d21-e9588ac9344b', 'oliviarodrigo공연2'),
        ('01937d01-0359-00e8-7cd6-a45b108d73d4', '2024-11-30 21:17:18.682', '2024-11-30 21:17:18.682', false, '01937d01-0358-516e-a16f-fe2e30017cac', 'brunomars공연1'),
        ('01937d01-e4a9-f167-7a2f-d722959ef7dc', '2024-11-30 21:18:16.362', '2024-11-30 21:18:16.362', false, '01937d01-e4a6-7d9b-dbf1-128cfb018783', 'brunomars공연2'),
        ('01937d02-d456-d7f4-693f-7cc9ec0eb867', '2024-11-30 21:19:17.719', '2024-11-30 21:19:17.719', false, '01937d02-d452-b277-3ea3-fb71d3887122', 'ajr공연1'),
        ('01937d05-17e7-983e-2f9e-7978ba4897d7', '2024-11-30 21:21:46.088', '2024-11-30 21:21:46.088', false, '01937d05-17e3-3985-b7cf-0241e238c2d0', 'ajr공연2');

insert into public.show_ticketing_time (id, created_at, is_deleted, updated_at, ticketing_at, show_id, type)
values  ('01937cf9-9c59-6ae1-197e-9fe9efc76c73', '2024-11-30 21:09:13.562', false, '2024-11-30 21:09:13.562', NOW() + INTERVAL '5 days', '01937cf9-9c48-4ac9-1c57-8aba45fd4a96', 'NORMAL'),
        ('01937cfa-8fb2-39d2-a90b-efe470bae485', '2024-11-30 21:10:15.859', false, '2024-11-30 21:10:15.859', NOW() + INTERVAL '5 days', '01937cfa-8fb0-9491-7ea7-9ce4e630ce14', 'NORMAL'),
        ('01937cfd-0fc7-75ed-94ed-86a3996fcb0a', '2024-11-30 21:12:59.720', false, '2024-11-30 21:12:59.720', NOW() + INTERVAL '5 days', '01937cfd-0fc4-61b0-1e61-2529b72090d1', 'NORMAL'),
        ('01937cfe-5b88-be9e-8be0-6e8b79dfa85b', '2024-11-30 21:14:24.649', false, '2024-11-30 21:14:24.649', NOW() + INTERVAL '5 days', '01937cfe-5b84-3371-3a7e-b42bc7f01e1c', 'NORMAL'),
        ('01937cff-5d7a-3899-f7b4-dc42a4836476', '2024-11-30 21:15:30.683', false, '2024-11-30 21:15:30.683', NOW() + INTERVAL '5 days', '01937cff-5d76-b7a9-287c-9f3bbabbe0c6', 'NORMAL'),
        ('01937d00-10ee-4742-e3d4-9b4bf26c5e52', '2024-11-30 21:16:16.623', false, '2024-11-30 21:16:16.623', NOW() + INTERVAL '5 days', '01937d00-10ea-0792-4d21-e9588ac9344b', 'NORMAL'),
        ('01937d01-035a-d41c-1a5c-11a4dec35592', '2024-11-30 21:17:18.682', false, '2024-11-30 21:17:18.682', NOW() + INTERVAL '5 days', '01937d01-0358-516e-a16f-fe2e30017cac', 'NORMAL'),
        ('01937d01-e4aa-82f5-04be-5b3dc9004c03', '2024-11-30 21:18:16.362', false, '2024-11-30 21:18:16.362', NOW() + INTERVAL '5 days', '01937d01-e4a6-7d9b-dbf1-128cfb018783', 'NORMAL'),
        ('01937d02-d457-ecbf-c3cb-264400e57222', '2024-11-30 21:19:17.720', false, '2024-11-30 21:19:17.720', NOW() + INTERVAL '5 days', '01937d02-d452-b277-3ea3-fb71d3887122', 'NORMAL'),
        ('01937d05-17e8-ad05-d846-c04d1bd0f795', '2024-11-30 21:21:46.089', false, '2024-11-30 21:21:46.089', NOW() + INTERVAL '5 days', '01937d05-17e3-3985-b7cf-0241e238c2d0', 'NORMAL');