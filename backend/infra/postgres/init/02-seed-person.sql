INSERT INTO person (
    person_id,
    name,
    tax_identifier,
    person_status,
    created,
    birth_date
) VALUES
      (
          'PL000001',
          'João Silva',
          '11111111115',
          'ACTIVE',
          NOW(),
          '1990-05-10'
      ),
      (
          'PL000002',
          'Maria Souza',
          '22222222225',
          'INACTIVE',
          NOW(),
          '1988-11-22'
      ),
      (
          'PL000003',
          'Maria F. Souza',
          '22222222221',
          'ACTIVE',
          NOW(),
          '1984-10-02'
      ),
      (
          'PL000004',
          'Jean C B Santos',
          '11122222221',
          'ACTIVE',
          NOW(),
          '1977-05-18'
      ),
      (
          'PL000005',
          'Camilla D Santos',
          '11122222227',
          'ACTIVE',
          NOW(),
          '1999-04-08'
      )
    ON CONFLICT (person_id) DO NOTHING;
