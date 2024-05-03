-- Verifica se as tabelas existem antes de excluí-las
DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'checklist') THEN
            DROP TABLE public.checklist;
        END IF;

        IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'empresa') THEN
            DROP TABLE public.empresa;
        END IF;
    END $$;
