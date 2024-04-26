CREATE TABLE Empresa (
                         id SERIAL PRIMARY KEY,
                         cnpj VARCHAR(14) NOT NULL,
                         nome VARCHAR(50) NOT NULL,
                         CHECK (cnpj ~ '^[0-9]{14}$'),
    UNIQUE (id),
    UNIQUE (cnpj)
);

CREATE TABLE Checklist (
                           id SERIAL PRIMARY KEY,
                           tipo VARCHAR(13) CHECK (tipo IN ('CONTABILIDADE', 'FISCAL', 'FOLHA')),
                           mes_ano VARCHAR(7),
                           status VARCHAR(10) CHECK (status IN ('COMPLETO', 'INCOMPLETO')),
                           empresa_id INT NOT NULL,
                           CHECK (mes_ano ~ '^(0[1-9]|1[0-2])\/[0-9]{4}$'),
    UNIQUE (id)
);

ALTER TABLE Checklist ADD FOREIGN KEY (empresa_id) REFERENCES Empresa (id);
ALTER TABLE Checklist ADD CONSTRAINT unique_tipo_mes_ano_empresa UNIQUE (tipo, mes_ano, empresa_id);