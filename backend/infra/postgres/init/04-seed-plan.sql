INSERT INTO plans (
    plan_id,
    name,
    type,
    monthly_price,
    created,
    active,
    description
) VALUES
      (
          'PL000001',
          'Plano Básico',
          'PRE_PAGO',
          49.90,
          NOW(),
          true,
          'Plano pré-pago básico'
      ),
      (
          'PL000002',
          'Plano Controle',
          'CONTROLE',
          89.90,
          NOW(),
          true,
          'Plano controle mensal'
      ),
      (
          'PL000003',
          'Plano Pós',
          'POS_PAGO',
          129.90,
          NOW(),
          true,
          'Plano pós-pago descontinuado'
      )
    ON CONFLICT (plan_id) DO NOTHING;
