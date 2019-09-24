create table if not exists accounts(
    id SERIAL unique primary key,
    email text unique not null,
    password text not null,
    permission boolean default false,
    CONSTRAINT email_not_empty CHECK (email <> ''),
    CONSTRAINT password_not_empty CHECK (password <> '')
);

 create table if not exists accountData(
    accounts_id int REFERENCES accounts(id) primary key,
    adress text,
    purchases int,
    name text,
    phone varchar(11),
    balance int,
    regular boolean default false
);
create table if not exists supplements (
    id serial unique primary key,
    name text unique not null,
    supplement_type text,
    img text unique not null,
    price int not null,
    premium boolean default false,
    amount int
);

create table if not exists inventory (
    accounts_id int REFERENCES accounts(id),
    supplements_id int REFERENCES supplements(id),
    amount int
);

create or replace function check_account_uniqueness()
returns trigger as
    'BEGIN
        IF (SELECT EXISTS(SELECT 1 FROM accounts WHERE email = NEW.email AND email != ''admin'') = true) THEN
            RAISE EXCEPTION ''Email already in use'';
        ELSE
            RETURN NEW;
        END IF;
    END;
'language plpgsql;

create or replace function check_supplement_uniqueness()
returns trigger as
    'BEGIN
        IF (SELECT EXISTS(SELECT 1 FROM supplements WHERE name = NEW.name AND img = NEW.img) = true) THEN
            RAISE EXCEPTION ''Protein already exists'';
        ELSE
            RETURN NEW;
        END IF;
    END;
'language plpgsql;

create or replace function set_basic_data()
returns trigger as
    'BEGIN
        perform NEW.id from accounts;
        perform NEW.email from accounts;
        IF (NEW.email = ''admin'') THEN
            insert into accountData (accounts_id, adress, purchases, name, phone, balance, regular) values (NEW.id, ''ADMIN'', 99999, ''ADMIN'', 99999, 99999, true);
        ELSE
            insert into accountData (accounts_id, adress, purchases, name, phone, balance, regular) values (NEW.id, ''empty'', 0, ''noname'', 0, 0, false);
        END IF;
        return NEW;
    END;
'language plpgsql;

drop trigger if exists check_account_uniqueness on accounts;
drop trigger if exists check_supplement_uniqueness on supplements;
drop trigger if exists set_basic_data on accountData;
drop trigger if exists set_basic_data on accounts;

create trigger check_account_uniqueness
    before insert on accounts
    for each row
    execute procedure check_account_uniqueness();

create trigger set_basic_data
    after insert on accounts
    for each row
    execute procedure set_basic_data();

insert into accounts (email, password, permission) values
('admin', 'admin', 'true') on conflict do nothing;

insert into supplements (name, supplement_type, img, price, premium) values
('Scitec 100% Whey Protein Professional', 'Whey', 'https://p1.akcdn.net/full/243620464.scitec-nutrition-100-whey-protein-professional-2350g.jpg', '50', 'false'),
('Optimum Nutrition Gold Standard 100% Whey', 'Whey', 'https://p1.akcdn.net/full/245630716.optimum-nutrition-gold-standard-100-whey-2270g.jpg', '100', 'true'),
('Scitec 100% Casein Complex', 'Casein', 'https://gymbeamhu.vshcdn.net/media/catalog/product/cache/926507dc7f93631a094422215b778fe0/1/0/100-casein-complex-2350-g-scitec-nutrition_1_1.jpg', '40', 'false'),
('Optimum Nutrition Gold Standard 100% Casein', 'Casein', 'https://gymbeamhu.vshcdn.net/media/catalog/product/cache/926507dc7f93631a094422215b778fe0/1/3/1339760708-28427200_1.png', '90', 'true'),
('Scitec Egg Pro', 'Egg', 'https://cms.scitecnutrition.com/media/4f1/4f1ba42993017e1e15760664c434e5dbd292e305.jpeg', '27', 'false'),
('NOW Foods Egg White Protein', 'Egg', 'https://www.nowfoods.com/sites/default/files/styles/product_page_image/public/sku-images/2040_mainimage.png?itok=P_UAEK8A', '70', 'true'),
('Biotech Rice Protein', 'Rice', 'https://p1.akcdn.net/full/335730479.biotechusa-rice-protein-500g.jpg', '12', 'false'),
('Naked Rice Premium Organic Brown Rice Protein', 'Rice', 'https://cdn.shopify.com/s/files/1/0645/6465/products/Organic_Brown_Rice_Protein_Powder.jpg?v=1550483974', '100', 'true'),
('Scitec Pro Mix', 'Mixed', 'https://cms.scitecnutrition.com/media/1de/1def5aa8a65ac79b89ba6fd7c5867b805fcf1093.jpeg', '60', 'false'),
('BSN Syntha 6', 'Mixed', 'https://www.profigym.hu/picture/shop/upload/bsnsyntha2270.jpg', '80', 'true'),
('Biotech Beef Protein', 'Beef', 'https://shop.builder.hu/images/product_images/12150_cbb4ac51c590.png', '60', 'false'),
('MuscleMeds Carnivor', 'Beef', 'https://s3.images-iherb.com/mme/mme00254/l/9.jpg', '150', 'true'),
('Scitec 100% Plant Protein', 'Pea', 'https://cms.scitecnutrition.com/media/ea8/ea8f9446659973e27b46448fa2757a938fb95118.jpeg', '20', 'false'),
('Naked Pea 100% Premium Pea Protein', 'Pea', 'https://cdn.shopify.com/s/files/1/0645/6465/products/Naked_Pea_Protein_Powder.jpg?v=1550484027', '50', 'true') on conflict do nothing;


create trigger check_supplement_uniqueness
    before insert on supplements
    for each row
    execute procedure check_supplement_uniqueness();
