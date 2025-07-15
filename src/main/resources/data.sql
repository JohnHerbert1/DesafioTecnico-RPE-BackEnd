-- ===================================================================
-- 10 CLIENTES (idempotente: não duplica se já existir)
-- ===================================================================
INSERT INTO tb_clients (id, name, cpf, data_nascimento, status_bloqueio, limite_credito)
VALUES
  ('44444444-444-4444-4444-444444444444', 'Daniela Lima',     '45678901234', '1992-03-10', 'ATIVO',     1500.00),
  ('55555555-5555-5555-5555-555555555555', 'Eduardo Souza',     '56789012345', '1988-07-22', 'ATIVO',     2500.00),
  ('66666666-6666-6666-6666-666666666666', 'Fernanda Rocha',    '67890123456', '1995-11-05', 'ATIVO',     1800.00),
  ('77777777-7777-7777-7777-777777777777', 'Gustavo Almeida',   '78901234567', '1979-02-17', 'ATIVO',     3000.00),
  ('88888888-8888-8888-8888-888888888888', 'Heloísa Fernandes', '89012345678', '1983-09-30', 'ATIVO',     1200.00),
  ('99999999-9999-9999-9999-999999999999', 'Igor Carvalho',     '90123456789', '1990-12-12', 'ATIVO',     2200.00),
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Juliana Pinto',     '01234567890', '1987-06-18', 'ATIVO',     2000.00),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Kleber Menezes',    '12345098765', '1993-04-25', 'ATIVO',     1700.00),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Lucas Barbosa',     '23456109876', '1982-08-14', 'BLOQUEIO',     0.00),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Mariana Castro',    '34567210987', '1975-01-29', 'BLOQUEIO',     0.00)
ON CONFLICT (id) DO NOTHING;

-- ===================================================================
-- 10 FATURAS (idempotente: não duplica se já existir)
-- ===================================================================
INSERT INTO tb_faturas (id, cliente_id, data_vencimento, data_pagamento, valor, status_fatura)
VALUES
  ('f4-uuid-4444-4444-4444-444444444444', '44444444-444-4444-4444-444444444444', '2025-07-05', NULL,    750.00, 'B'),
  ('f5-uuid-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', '2025-06-20', '2025-06-19', 500.00, 'P'),
  ('f6-uuid-6666-6666-6666-666666666666', '66666666-6666-6666-6666-666666666666', '2025-07-10', NULL,    300.00, 'B'),
  ('f7-uuid-7777-7777-7777-777777777777', '77777777-7777-7777-7777-777777777777', '2025-06-30', '2025-06-28',1200.00, 'P'),
  ('f8-uuid-8888-8888-8888-888888888888', '88888888-8888-8888-8888-888888888888', '2025-07-01', NULL,    400.00, 'B'),
  ('f9-uuid-9999-9999-9999-999999999999', '99999999-9999-9999-9999-999999999999', '2025-06-15', '2025-06-16', 950.00, 'P'),
  ('f10-uuid-aaaa-aaaa-aaaa-aaaaaaaaaaaa','aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa','2025-07-12', NULL, 650.00, 'B'),
  ('f11-uuid-bbbb-bbbb-bbbb-bbbbbbbbbbbb','bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb','2025-06-25','2025-06-26',800.00, 'P'),
  ('f12-uuid-cccc-cccc-cccc-cccccccccccc','cccccccc-cccc-cccc-cccc-cccccccccccc','2025-06-10', NULL,1100.00,'A'),
  ('f13-uuid-dddd-dddd-dddd-dddddddddddd','dddddddd-dddd-dddd-dddd-dddddddddddd','2025-06-05', NULL, 700.00,'A')
ON CONFLICT (id) DO NOTHING;
