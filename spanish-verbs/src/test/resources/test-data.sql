CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS verbs (
    infinitive character varying NOT NULL,
    mood character varying NOT NULL,
    tense character varying NOT NULL,
    verb_english character varying,
    form_1s character varying,
    form_2s character varying,
    form_3s character varying,
    form_1p character varying,
    form_2p character varying,
    form_3p character varying
);

CREATE TABLE IF NOT EXISTS saved (
    infinitive VARCHAR(255),
    mood VARCHAR(255),
    tense VARCHAR(255),
    userid INTEGER,
    PRIMARY KEY (infinitive, mood, tense),
    FOREIGN KEY (infinitive, mood, tense) REFERENCES verbs(infinitive, mood, tense) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (userid) REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO accounts (id, email, password) VALUES ('1', 'test@account.com', 'password123');

INSERT INTO verbs (infinitive, mood, tense, verb_english, form_1s, form_2s, form_3s, form_1p, form_2p, form_3p)
    VALUES ('abandonar', 'Indicativo', 'Presente', 'I abandon, am abandoning', 'abandono', 'abandonas', 'abandona', 'abandonamos', 'abandonáis', 'abandonan'),
    ('batir', 'Indicativo', 'Presente', 'I beat, am beating', 'bato', 'bates', 'bate', 'batimos', 'batís', 'baten'),
    ('cenar', 'Indicativo', 'Pluscuamperfecto', 'I had eaten supper', 'había cenado', 'habías cenado', 'había cenado', 'habíamos cenado', 'habíais cenado', 'habían cenado'),
    ('devorar', 'Indicativo', 'Presente', 'I devour, am devouring', 'devoro', 'devoras', 'devora', 'devoramos', 'devoráis', 'devoran');
