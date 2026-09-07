CREATE TABLE currency (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          currency_code VARCHAR(10) NOT NULL UNIQUE,
                          currency_name VARCHAR(50) NOT NULL
);

INSERT INTO currency (currency_code, currency_name)
VALUES ('USD', '美元');

INSERT INTO currency (currency_code, currency_name)
VALUES ('GBP', '英鎊');

INSERT INTO currency (currency_code, currency_name)
VALUES ('EUR', '歐元');

INSERT INTO currency (currency_code, currency_name)
VALUES ('JPY', '日圓');