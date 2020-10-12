"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql" -u root -p -e "create database protoerp DEFAULT CHARACTER SET utf8;"
"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql" -u root -p -e "GRANT ALL PRIVILEGES ON protoerp.* To 'protoerp'@'localhost' IDENTIFIED BY 'protoerp';"
pause